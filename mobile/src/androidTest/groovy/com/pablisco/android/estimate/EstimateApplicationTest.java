package com.pablisco.android.estimate;

import android.test.ApplicationTestCase;

import com.pablisco.android.estimate.graph.Graph;
import com.pablisco.reflect.ReflectiveType;

import static org.assertj.core.api.Assertions.assertThat;

public class EstimateApplicationTest extends ApplicationTestCase<EstimateApplication> {

    public EstimateApplicationTest() {
        super(EstimateApplication.class);
    }

    public void testInitializeGraph() throws Exception {
        createApplication();

        Graph actualGraph = ReflectiveType.reflect(Graph.graph()).field("GRAPH").getAs(Graph.class);

        assertThat(actualGraph).isNotNull();

    }
}