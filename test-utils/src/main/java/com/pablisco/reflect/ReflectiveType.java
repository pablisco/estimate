package com.pablisco.reflect;

import com.pablisco.function.Exceptions;
import com.pablisco.function.SupplierWithException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static com.pablisco.function.Exceptions.noThrow;
import static com.pablisco.function.Exceptions.runtimeThrow;

/**
 * Used to access hidden parts of a class and not recommended for production, mostly for testing.
 */
public class ReflectiveType<T> {

    private static final Field MODIFIER_FIELD;

    static {
        MODIFIER_FIELD = runtimeThrow(() -> Field.class.getDeclaredField("modifiers"));
        MODIFIER_FIELD.setAccessible(true);
    }

    private Class<?> type;
    private T subject;

    private ReflectiveType(Class<?> type, T subject) {
        this.type = type;
        this.subject = subject;
    }

    private ReflectiveType(T subject) {
        this(subject.getClass(), subject);
    }

    public static <T> ReflectiveType<T> reflect(T subject) {
        return new ReflectiveType<>(subject);
    }

    public static <T> ReflectiveType<T> reflect(Class<T> subjectType) {
        return new ReflectiveType<>(subjectType, null);
    }

    private static Stream<Class<?>> inheritanceTree(Class<?> type) {
        return Stream.iterate(type, (UnaryOperator<Class<?>>) Class::getSuperclass)
                .filter(t -> Object.class != t);
    }

    private static void ensureNonFinal(Field field) {
        Stream.of(field.getModifiers()).filter(Modifier::isFinal)
                .map(m -> m & ~Modifier.FINAL)
                .findFirst()
                .ifPresent(m -> runtimeThrow(() -> MODIFIER_FIELD.set(field, m)));
    }

    private static void ensureAccessible(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public ReflectiveField field(String name) {
        Optional<Field> field = inheritanceTree(type)
                .map(t -> findField(t, name))
                .filter(Optional::isPresent)
                .findFirst().get();
        return new ReflectiveField(field.orElseThrow(fieldNotFoundException(name)));
    }

    private Supplier<RuntimeException> fieldNotFoundException(String name) {
        return () -> new RuntimeException("Field " + name + " not found in " + this.type.getSimpleName());
    }

    private Optional<Field> findField(final Class<?> type, String name) {
        return Stream.<SupplierWithException<Field>>of(
                () -> type.getDeclaredField(name),
                () -> type.getField(name))
                .map(Exceptions::noThrow)
                .filter(Optional::isPresent)
                .findFirst().orElse(Optional.empty());
    }

    public class ReflectiveField {

        private final Field field;

        public ReflectiveField(Field field) {
            this.field = field;
            ensureAccessible(field);
            ensureNonFinal(field);
        }

        public void set(Object object) {
            runtimeThrow(() -> field.set(subject, object));
        }

        public <S> S getAs(Class<S> type) {
            return noThrow(() -> type.cast(field.get(subject))).get();
        }

    }

}
