package com.pablisco.android.estimate.graph;

import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;

import com.pablisco.android.sensor.ShakeDetector;
import com.pablisco.common.Debounce;

public class SensorModule {

    private static final int SENSOR_DELAY = 100;
    private ApplicationSource applicationSource;

    SensorModule(ApplicationSource applicationSource) {
        this.applicationSource = applicationSource;
    }

    public ShakeDetector shakeDetector() {
        SensorManager sensorManager = (SensorManager) application().getSystemService(Context.SENSOR_SERVICE);
        return new ShakeDetector(sensorManager, new Debounce(SENSOR_DELAY));
    }

    private Application application() {
        return applicationSource.findApplication();
    }

}
