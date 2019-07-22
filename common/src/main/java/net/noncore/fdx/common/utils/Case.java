package net.noncore.fdx.common.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Case<T> {
    static <T> Case<T> of(T value) {
        return new CasePipeline<>(value);
    }
    Case<T> whenNull();
    Case<T> when(T... targets);
    Case<T> when(Predicate<T> predicate);
    <R> Case<T> then(R result);
    <R> Case<T> then(Function<T, R> function);
    Case<T> call(Consumer<T> consumer);
    Case<T> other();
    <R> R end();
}

class CasePipeline<T> implements Case<T> {
    private T value;
    private Object result;
    private boolean matched;
    private boolean finished;

    CasePipeline(T value) {
        this.value = value;
    }

    @Override
    public Case<T> whenNull() {
        if (!finished) {
            matched = value == null;
        }
        return this;
    }

    @Override
    public Case<T> when(T... targets) {
        if (!finished) {
            matched = Stream.of(targets)
                    .anyMatch(v -> Objects.equals(v, value));
        }
        return this;
    }

    @Override
    public Case<T> when(Predicate<T> predicate) {
        if (!finished) {
            matched = predicate.test(value);
        }
        return this;
    }

    @Override
    public <R> Case<T> then(R result) {
        if (!finished && matched) {
            this.result = result;
            finished = true;
        }
        return this;
    }

    @Override
    public <R> Case<T> then(Function<T, R> function) {
        if (!finished && matched) {
            this.result = function.apply(value);
            finished = true;
        }
        return this;
    }

    @Override
    public Case<T> call(Consumer<T> consumer) {
        if (!finished && matched) {
            consumer.accept(value);
            finished = true;
        }
        return this;
    }

    @Override
    public Case<T> other() {
        if (!finished) {
            matched = true;
        }
        return this;
    }

    @Override
    public <R> R end() {
        return (R) result;
    }
}
