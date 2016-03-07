package com.pablisco.function;

/**
 * Similar to {@link java.util.function.Supplier} but it may throw an exception
 */
@FunctionalInterface
public interface SupplierWithException<T> {
    T get() throws Exception;
}
