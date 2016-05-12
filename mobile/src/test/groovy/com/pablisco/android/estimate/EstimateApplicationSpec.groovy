package com.pablisco.android.estimate

import com.pablisco.android.estimate.graph.Graph
import spock.lang.Specification

class EstimateApplicationSpec extends Specification {

    def "should initialize graph on create"() {
        when: application.onCreate()
        then: Graph.graph()
        where: application = new EstimateApplication()
    }

}
