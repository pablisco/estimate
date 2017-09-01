package com.pablisco.android.estimate.graph

import android.app.Application
import spock.lang.Specification

class GraphSpecs extends Specification {



    @SuppressWarnings("GroovyAccessibility")
    def "should provide exception if not initialized"() {
        given: Graph.@GRAPH = null;
        when: Graph.graph()
        then:
        def thrown = thrown(IllegalStateException)
        thrown.message == "onCreate not called from Application"
    }

    @SuppressWarnings("GroovyAccessibility")
    def "should provide Graph after onCreate from application"() {
        given: Graph.onCreate(application)
        when: def graph = Graph.graph()
        then: graph
        cleanup: Graph.@GRAPH = null;
        where: application = Mock(Application)
    }

    def "should provide sensor module"() {
        given: Graph.onCreate(Mock(Application))
        when: def sensorModule = Graph.graph().sensorModule();
        then: sensorModule instanceof SensorModule
        cleanup: Graph.@GRAPH = null;
    }
}
