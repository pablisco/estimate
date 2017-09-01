package com.pablisco.android.estimate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        int spanCount = getResources().getInteger(R.integer.deck_column_count);
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        RecyclerView cardsView = (RecyclerView) findViewById(R.id.cardsView);
        assert cardsView != null;
        cardsView.setLayoutManager(layoutManager);
        cardsView.setAdapter(new CardsAdapter(getResources().getTextArray(R.array.standard)));
    }

}
