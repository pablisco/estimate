package com.pablisco.common;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DebounceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Runnable runnable;

    @Test
    public void shouldExecuteFirstCommand() throws Exception {
        Debounce debounce = new Debounce(50);

        debounce.run(runnable);

        verify(runnable).run();
    }

    @Test
    public void shouldNotRunCommandIfWithinTime() throws Exception {
        Debounce debounce = new Debounce(50);

        debounce.run(runnable);
        debounce.run(runnable);

        verify(runnable).run();
    }

    @Test
    public void shouldRunSecondCommandIfTimePassed() throws Exception {
        Debounce debounce = new Debounce(50);

        debounce.run(runnable);
        Thread.sleep(50);
        debounce.run(runnable);

        verify(runnable, times(2)).run();
    }

    @Test
    public void shouldAllowRunnableAccessToInterval() throws Exception {
        Debounce debounce = new Debounce(50);
        debounce.run(runnable);
        Thread.sleep(60);
        debounce.run(() -> {
            long time = debounce.getTimeSinceLastInteraction();
            assertThat(time).isGreaterThan(50);
        });

    }
}