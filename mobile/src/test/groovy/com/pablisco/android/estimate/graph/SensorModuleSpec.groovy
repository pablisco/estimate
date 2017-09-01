package com.pablisco.android.estimate.graph

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import com.pablisco.android.sensor.ShakeDetector
import spock.lang.Specification

class SensorModuleSpec extends Specification {

    def application = Mock(Application)
    def sensorModule = new SensorModule(application)

    def "should provide ShakeDetector"() {
        when: def shakeDetector = sensorModule.shakeDetector()
        then: shakeDetector instanceof ShakeDetector
    }

    @SuppressWarnings("GroovyAccessibility")
    def "should use sensorManager"() {
        given: application.getSystemService(Context.SENSOR_SERVICE) >> sensorManager
        when: def shakeDetector = sensorModule.shakeDetector()
        then: shakeDetector.@sensorManager == sensorManager
        where: sensorManager = Mock(SensorManager)
    }

    @SuppressWarnings("GroovyAccessibility")
    def "should use a debounce with 100 milliseconds"() {
        when: def shakeDetector = sensorModule.shakeDetector()
        then: shakeDetector.@debounce.@delay == 100
    }

}
