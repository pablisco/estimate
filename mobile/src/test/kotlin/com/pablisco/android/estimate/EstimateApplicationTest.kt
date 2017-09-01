package com.pablisco.android.estimate

import com.pablisco.android.estimate.graph.Graph
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class EstimateApplicationTest {

    @Test fun shouldInitializeGraphOnCreate() {
        val application = EstimateApplication()
        application.onCreate()
        assertThat(Graph.graph()).isNotNull()
    }

}
