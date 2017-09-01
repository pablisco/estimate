package com.pablisco.android.estimate;

import android.app.Application;

import static com.pablisco.android.estimate.graph.Graph.graph;

public class EstimateApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        graph().registerApplication(() -> this);
    }

}
