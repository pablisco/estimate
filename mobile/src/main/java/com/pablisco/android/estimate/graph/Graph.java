package com.pablisco.android.estimate.graph;

import android.app.Application;

public class Graph implements ApplicationSource {

    private static final Graph GRAPH = new Graph();
    private SensorModule sensorModule;
    private ApplicationSource applicationSource;

    private Graph() {
        sensorModule = new SensorModule(this);
    }

    public static Graph graph() {
        return GRAPH;
    }

    public void registerApplication(ApplicationSource applicationSource) {
        this.applicationSource = applicationSource;
    }

    public SensorModule sensorModule() {
        return sensorModule;
    }

    @Override
    public Application findApplication() {
        if (applicationSource == null) {
            throw new IllegalStateException("Application source not registered");
        }
        return applicationSource.findApplication();
    }

}
