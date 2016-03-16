package com.pablisco.android.sensor

import android.hardware.SensorEvent
import android.hardware.SensorManager
import com.pablisco.common.Debounce
import spock.lang.Specification

class ShakeDetectorSpec extends Specification {

    def "should report shake"(float[][] events, double speed) {
        setup:
        def shakeListener = Mock(OnShakeListener)
        def debounce = Mock(Debounce)
        debounce.run(_) >> { Runnable runnable -> runnable.run() }
        def sensorManager = Mock(SensorManager)
        def shakeDetector = new ShakeDetector(sensorManager, debounce)
        when:
        shakeDetector.onShake(shakeListener)
        events.each {
            def event = Mock(SensorEvent)
            event.values = it
            shakeDetector.onSensorChanged(event);
        }
        then:
        1 * shakeListener.onShake(speed)
        where:
        events                     | speed
        [[0, 0, 0], [800, 0, 0]]   | 800
        [[0, 0, 0], [0, 800, 0]]   | 800
        [[0, 0, 0], [0, 0, 800]]   | 800
    }

}