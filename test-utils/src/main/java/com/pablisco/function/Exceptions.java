package com.pablisco.function;

import java.util.Optional;

/**
 * Handy tools to deal gracefully with exceptions.
 */
public interface Exceptions {

    static void runtimeThrow(RunnableWithException action) {
        try {
            action.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <T> T runtimeThrow(SupplierWithException<T> action) {
        try {
            return action.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <T> Optional<T> noThrow(SupplierWithException<T> callable) {
        try {
            return Optional.ofNullable(callable.get());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
