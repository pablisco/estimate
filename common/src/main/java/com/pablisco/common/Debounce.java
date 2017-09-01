package com.pablisco.common;

/**
 * Used to limit the number of actions on a subject.
 */
public class Debounce {

    private final long delay;
    private long lastExecution = 0;
    private long timeSinceLastInteraction;

    @SuppressWarnings("SameParameterValue")
    public Debounce(long delay) {
        this.delay = delay;
    }

    public void run(Runnable runnable) {
        long now = System.currentTimeMillis();
        long deltaTime = now - lastExecution;
        if (deltaTime > delay) {
            timeSinceLastInteraction = deltaTime;
            runnable.run();
            lastExecution = now;
        }
    }

    public long getTimeSinceLastInteraction() {
        return timeSinceLastInteraction;
    }
}
