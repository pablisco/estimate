package com.pablisco.android.estimate.graph;

import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;

import com.pablisco.android.sensor.ShakeDetector;
import com.pablisco.common.Debounce;

public class SensorModule {

    private static final int SENSOR_DELAY = 100;
    private Application application;

    SensorModule(Application application) {
        this.application = application;
    }

    public ShakeDetector shakeDetector() {
        SensorManager sensorManager = (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
        return new ShakeDetector(sensorManager, new Debounce(SENSOR_DELAY));
    }


}
