package com.pablisco.android.estimate;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.pablisco.android.estimate.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        int spanCount = getResources().getInteger(R.integer.deck_column_count);
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        binding.cardsView.setLayoutManager(layoutManager);
        binding.cardsView.setAdapter(new CardsAdapter(this, R.array.standard));
    }

}
