package com.pablisco.android.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.pablisco.common.Debounce;

public class ShakeDetector implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Debounce debounce;

    private float[] lastValues = new float[3];
    private OnShakeListener onShakeListener;

    public ShakeDetector(SensorManager sensorManager, Debounce debounce) {
        this.sensorManager = sensorManager;
        this.debounce = debounce;
    }

    public void start() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        debounce.run(() -> {
            float dx = event.values[0] - lastValues[0];
            float dy = event.values[1] - lastValues[1];
            float dz = event.values[2] - lastValues[2];

            double speed = Math.sqrt(dx * dx + dy * dy + dz * dz);

            lastValues[0] = event.values[0];
            lastValues[1] = event.values[1];
            lastValues[2] = event.values[2];

            if (null != onShakeListener) {
                onShakeListener.onShake((float) Math.abs(speed));
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    public void onShake(OnShakeListener onShakeListener) {
        this.onShakeListener = onShakeListener;
    }
}
