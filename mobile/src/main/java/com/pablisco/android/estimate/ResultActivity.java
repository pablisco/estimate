package com.pablisco.android.estimate;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pablisco.android.estimate.view.util.ViewAnimationUtilsCompat;
import com.pablisco.android.sensor.ShakeDetector;

import static com.pablisco.android.estimate.graph.Graph.graph;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    static final String EXTRA_ESTIMATION = TAG + "#EXTRA_ESTIMATION";
    private static final String STATE_REVEALED = TAG + "#STATE_REVEALED";
    private ShakeDetector shakeDetector;
    private TextView estimationResult;
    private boolean revealed = false;

    public ResultActivity() {
        shakeDetector = graph().sensorModule().shakeDetector();
        shakeDetector.onShake(speed -> {
            if (!revealed && speed > 50) reveal(null);
        });
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    protected void onResume() {
        super.onResume();
        shakeDetector.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shakeDetector.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        estimationResult = (TextView) findViewById(R.id.estimation_result);
        CharSequence estimation = getIntent().getCharSequenceExtra(EXTRA_ESTIMATION);
        estimationResult.setText(estimation);
    }

    @SuppressWarnings("UnusedParameters")
    public void reveal(View view) {
        if (!revealed) {
            fullRevealAnimationFor(estimationResult).start();
            revealed = true;
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_REVEALED, revealed);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        revealed = savedInstanceState.getBoolean(STATE_REVEALED, false);
        if (revealed) {
            estimationResult.setVisibility(View.VISIBLE);
        }
    }

    private Animator fullRevealAnimationFor(View view) {
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);
        view.setVisibility(View.VISIBLE);

        return ViewAnimationUtilsCompat.createCircularReveal(view, cx, cy, 0, finalRadius);
    }

}
