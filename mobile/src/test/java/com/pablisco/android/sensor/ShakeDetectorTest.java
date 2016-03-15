package com.pablisco.android.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;

import com.pablisco.common.Debounce;
import com.pablisco.reflect.ReflectiveType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collection;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShakeDetectorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private SensorManager sensorManager;
    @Mock
    private Sensor accelerometer;

    @Mock
    private OnShakeListener onShakeListener;

    @Mock
    private Debounce debounce;

    @Before
    public void setUp() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Runnable runnable = (Runnable) invocation.getArguments()[0];
                runnable.run();
                return null;
            }
        }).when(debounce).run(any(Runnable.class));
    }

    @Test
    public void shouldRegisterToSensorManager_onStart() throws Exception {
        ShakeDetector shakeDetector = givenShakeDetector();

        shakeDetector.start();

        verify(sensorManager).registerListener(shakeDetector, accelerometer, SENSOR_DELAY_GAME);
    }

    @Test
    public void shouldUnregisterFromSensorManager_onStop() throws Exception {
        ShakeDetector shakeDetector = givenShakeDetector();

        shakeDetector.stop();

        verify(sensorManager).unregisterListener(shakeDetector);
    }

    @Test
    public void shouldAllowListeningToShakeEvents() throws Exception {
        ShakeDetector shakeDetector = givenShakeDetector();

        shakeDetector.onShake(onShakeListener);

    }

    @RunWith(Parameterized.class)
    public static class OnSensorChanged {

        @Rule
        public MockitoRule mockitoRule = MockitoJUnit.rule();

        @Parameter(0)
        public String name;

        @Parameter(1)
        public float[][] events;

        @Parameter(2)
        public double speed;

        @Mock
        private SensorManager sensorManager;

        @Mock
        private OnShakeListener onShakeListener;

        @Mock
        private SensorEvent sensorEvent;

        @Mock
        private Debounce debounce;

        @Before
        public void setUp() throws Exception {
            doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    Runnable runnable = (Runnable) invocation.getArguments()[0];
                    runnable.run();
                    return null;
                }
            }).when(debounce).run(any(Runnable.class));
        }

        @Parameters(name = "{0}")
        public static Collection parameters() {
            return Arrays.asList(new Object[][]{
                    {"x movement",  new float[][] {{0, 0, 0}, {800, 0, 0}}, 800},
                    {"y movement",  new float[][] {{0, 0, 0}, {0, 800, 0}}, 800},
                    {"z movement",      new float[][] {{0, 0, 0}, {0, 0, 800}}, 800},
                    {"y-z movement",    new float[][] {{0, 0, 0}, {0, 400, 400}}, 560},
            });
        }

        @Test
        public void shouldReportShakeWhenInTime() {
            ShakeDetector shakeDetector = givenDetectorWithListener();

            triggerSensorEvents(shakeDetector, events);

            verify(onShakeListener).onShake(AdditionalMatchers.eq(speed, 10));
        }

        @NonNull
        private ShakeDetector givenDetectorWithListener() {
            ShakeDetector shakeDetector = new ShakeDetector(sensorManager, debounce);
            shakeDetector.onShake(onShakeListener);
            return shakeDetector;
        }

        private void triggerSensorEvents(ShakeDetector shakeDetector, float[][] events) {
            for (float[] event : events) {
                ReflectiveType.reflect(sensorEvent).field("values").set(event);
                shakeDetector.onSensorChanged(sensorEvent);
            }
        }

    }

    @NonNull
    private ShakeDetector givenShakeDetector() {
        when(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)).thenReturn(accelerometer);
        return new ShakeDetector(sensorManager, debounce);
    }
}