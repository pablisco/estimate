package com.pablisco.android.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.pablisco.common.Debounce
import spock.lang.Specification
import spock.lang.Unroll

import static android.hardware.Sensor.TYPE_ACCELEROMETER
import static android.hardware.SensorManager.SENSOR_DELAY_GAME

class ShakeDetectorSpec extends Specification {

    def debounce = Mock(Debounce) {
        run(_) >> { Runnable runnable -> runnable.run() }
    }
    def sensorManager = Mock(SensorManager)
    def shakeDetector = new ShakeDetector(sensorManager, debounce)

    def "should register to sensor manager onStart"() {
        setup:
        def accelerometer = Mock(Sensor)
        sensorManager.getDefaultSensor(TYPE_ACCELEROMETER) >> accelerometer
        when:
        shakeDetector.start()
        then:
        1 * sensorManager.registerListener(shakeDetector, accelerometer, SENSOR_DELAY_GAME)
    }

    def "should unregister from sensor manager onStop"() {
        when:
        shakeDetector.stop()
        then:
        1 * sensorManager.unregisterListener(shakeDetector);
    }

    def "should do nothing on accuracy changed"() {
        when:
        shakeDetector.onAccuracyChanged(Mock(Sensor), 0)
        then:
        0 * _
    }

    @Unroll
    def "should report shake #events"() {
        setup:
        def shakeListener = Mock(OnShakeListener)
        when:
        shakeDetector.onShake(shakeListener)
        events.each {
            def event = Mock(SensorEvent)
            event.values = it
            shakeDetector.onSensorChanged(event);
        }
        then:
        1 * shakeListener.onShake({ (it - speed).abs() < 10 })
        where:
        events                     | speed
        [[0, 0, 0], [800, 0, 0]]   | 800
        [[0, 0, 0], [0, 800, 0]]   | 800
        [[0, 0, 0], [0, 0, 800]]   | 800
        [[0, 0, 0], [0, 400, 400]] | 560

    }

}