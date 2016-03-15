package com.pablisco.android.estimate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pablisco.android.estimate.databinding.ResultActivityBinding;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    public static final String KEY_ESTIMATION = TAG + "#KEY_ESTIMATION";
    private ResultActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.result_activity);
        binding.setValueVisible(false);
        CharSequence estimation = getIntent().getCharSequenceExtra(KEY_ESTIMATION);
        if (estimation != null) {
            binding.setEstimation(estimation);
        } else {
            finish();
        }
    }

    @SuppressWarnings("UnusedParameters")
    public void reveal(View view) {
        if (!binding.getValueVisible()) {
            binding.setValueVisible(true);
        } else {
            finish();
        }
    }
}
