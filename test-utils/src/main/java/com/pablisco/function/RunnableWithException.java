package com.pablisco.function;

/**
 * Used to run functions that may throw an exception.
 */
@FunctionalInterface
public interface RunnableWithException {
    void run() throws Exception;
}
