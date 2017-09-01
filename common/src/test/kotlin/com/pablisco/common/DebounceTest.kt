package com.pablisco.common

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import java.lang.Thread.sleep

class DebounceTest {

    val runnable : Runnable = mock()
    val debounce = Debounce(20)

    @Test fun `should execute first command`() {
        debounce.run(runnable)
        verify(runnable).run()
    }

    @Test fun `should not run command before timeout`() {
        debounce.run(runnable)
        debounce.run(runnable)
        verify(runnable).run()
    }

    @Test fun `should run second command after timeout`() {
        debounce.run(runnable)
        sleep(20)
        debounce.run(runnable)
        verify(runnable, times(2)).run()
    }

}