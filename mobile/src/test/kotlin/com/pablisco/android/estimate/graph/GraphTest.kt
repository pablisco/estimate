package com.pablisco.android.estimate.graph

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GraphTest {

    val application : Application = mock()
    val applicationSource : ApplicationSource = mock {
        whenever(it.findApplication()).thenReturn(application)
    }

    @Test fun shouldProvideExceptionIfNotInitialized() {
        Graph.graph().registerApplication(null)
        try {
            Graph.graph().sensorModule().shakeDetector()
        } catch(e: IllegalStateException) {
            Assertions.assertThat(e).hasMessage("Application source not registered")
        }
    }

    @Test fun shouldProvideGraphAfterOnCreateFromApplication() {
        Graph.graph().registerApplication(applicationSource)

        assertThat(Graph.graph().findApplication()).isEqualTo(application)
    }

    @Test fun shouldProvideSensorModule() {
        Graph.graph().registerApplication(applicationSource)
        val sensorModule = Graph.graph().sensorModule()

        assertThat(sensorModule).isNotNull()
    }

}
