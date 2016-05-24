package com.pablisco.android.estimate;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pablisco.android.estimate.view.util.ViewAnimationUtilsCompat;
import com.pablisco.android.sensor.ShakeDetector;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    static final String KEY_ESTIMATION = TAG + "#KEY_ESTIMATION";
    private ShakeDetector shakeDetector;
    private View revealMessage;
    private TextView estimationResult;
    private boolean revealed = false;

    public ResultActivity() {
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        revealMessage = findViewById(R.id.reveal_message);
        estimationResult = (TextView) findViewById(R.id.estimation_result);
        CharSequence estimation = getIntent().getCharSequenceExtra(KEY_ESTIMATION);
        estimationResult.setText(estimation);
    }

    @SuppressWarnings("UnusedParameters")
    public void reveal(View view) {
        if (!revealed) {
            revealAnimationFor(estimationResult).start();
            revealed = true;
        } else {
            finish();
        }
    }

    private Animator revealAnimationFor(View view) {
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);
        view.setVisibility(View.VISIBLE);

        return ViewAnimationUtilsCompat.createCircularReveal(view, cx, cy, 0, finalRadius);
    }

}
