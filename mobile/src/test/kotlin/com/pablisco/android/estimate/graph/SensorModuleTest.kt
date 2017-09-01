package com.pablisco.android.estimate.graph

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.pablisco.android.sensor.ShakeDetector
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SensorModuleTest {

    val application : Application = mock()
    val sensorModule = SensorModule(ApplicationSource { application })

    @Test fun shouldProvideShakeDetector() {
        val shakeDetector = sensorModule.shakeDetector()
        assertThat(shakeDetector).isInstanceOf(ShakeDetector::class.java)
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