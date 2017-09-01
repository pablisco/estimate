package com.pablisco.android.estimate;

import android.app.Application;

import com.pablisco.android.estimate.graph.Graph;

public class EstimateApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Graph.onCreate(this);
    }

}
