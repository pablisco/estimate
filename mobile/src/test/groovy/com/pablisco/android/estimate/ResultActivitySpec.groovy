//package com.pablisco.android.estimate
//
//import com.pablisco.android.estimate.graph.Graph
//import com.pablisco.android.estimate.graph.SensorModule
//import com.pablisco.android.sensor.ShakeDetector
//import spock.lang.Specification
//
//class ResultActivitySpec extends Specification {
//
//    @SuppressWarnings("GroovyAccessibility")
//    def "should start ShakeDetector on resume"() {
//        setup:
//        def resultActivity = new ResultActivity()
//        Graph.GRAPH = Stub(Graph) {
//            sensorModule() >> Stub(SensorModule) {
//                shakeDetector() >> actualShakeDetector
//            }
//        }
//        when: resultActivity.onResume()
//        then:
//        1 * actualShakeDetector.start()
//        where:
//        actualShakeDetector = Mock(ShakeDetector)
//
//    }
//}
