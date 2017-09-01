package com.pablisco.common

import spock.lang.Specification

public class DebounceTest extends Specification {

    def runnable = Mock(Runnable)
    def debounce = new Debounce(20)

    def "should execute first command"() {
        when: debounce.run(runnable)
        then: 1 * runnable.run()
    }

    def "should not run command before timeout"() {
        when: (1..2).each { debounce.run(runnable) }
        then: 1 * runnable.run()
    }

    def "should run second command after timeout"() {
        when: (1..2).each { debounce.run(runnable); sleep 20 }
        then: 2 * runnable.run()
    }

    def "should allow access to last execution"() {
        when: debounce.run(runnable); sleep 30
        then: debounce.timeSinceLastInteraction > 20
    }

}