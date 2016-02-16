package com.pablisco.android.estimate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pablisco.android.estimate.databinding.ResultActivityBinding;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    public static final String KEY_ESTIMATION = TAG + "#KEY_ESTIMATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResultActivityBinding binding;
        binding = DataBindingUtil.setContentView(this, R.layout.result_activity);
        CharSequence estimation = getIntent().getCharSequenceExtra(KEY_ESTIMATION);
        if (estimation != null) {
            binding.setEstimation(estimation);
        } else {
            finish();
        }
    }


}
