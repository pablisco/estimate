package com.pablisco.function;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;

public class ExceptionsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowRunTimeException() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(instanceOf(CheckedException.class));
        Exceptions.runtimeThrow(this::doFail);
    }

    @Test
    public void shouldNotThrowExceptionIfNotThrown() throws Exception {
        Exceptions.runtimeThrow(this::doNotFail);
    }

    @Test
    public void shouldThrowRunTimeException_withSupplier() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(instanceOf(CheckedException.class));
        Exceptions.runtimeThrow(this::getFail);
    }

    @Test
    public void shouldNotThrowExceptionIfNotThrown_withSupplier() throws Exception {
        String result = Exceptions.runtimeThrow(this::getNotFail);
        Assertions.assertThat(result).isEqualTo("test");
    }

    @Test
    public void shouldProvideEmptyOptional() throws Exception {
        Optional<String> s = Exceptions.noThrow(this::getFail);
        Assertions.assertThat(s.isPresent()).isFalse();
    }

    @Test
    public void shouldProvideOptional() throws Exception {
        Optional<String> result = Exceptions.noThrow(this::getNotFail);
        Assertions.assertThat(result.isPresent()).isTrue();
    }

    private void doFail() throws Exception {
        throw new CheckedException();
    }

    @SuppressWarnings("EmptyMethod")
    private void doNotFail() throws Exception {
    }

    private String getFail() throws Exception {
        throw new CheckedException();
    }

    @SuppressWarnings("SameReturnValue")
    private String getNotFail() throws Exception {
        return "test";
    }

    private static class CheckedException extends Exception {
    }

}