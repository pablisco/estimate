package com.pablisco.android.estimate.graph;

import android.app.Application;

public class Graph {

    private static Graph GRAPH;
    private SensorModule sensorModule;

    Graph(Application application) {
        sensorModule = new SensorModule(application);
    }

    public static Graph graph() {
        if (GRAPH == null) {
            throw new IllegalStateException("onCreate not called from Application");
        } else {
            return GRAPH;
        }
    }

    public static void onCreate(Application application) {
        GRAPH = new Graph(application);
    }

    public SensorModule sensorModule() {
        return sensorModule;
    }

}
