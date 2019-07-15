package net.noncore.fdx.helper;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Cast {
    static Cast of(Object value) {
        return new CastPipeline(value);
    }
    <T, R> Cast when(Class<T> clazz, R value);
    <T, R> Cast when(Class<T> clazz, Function<T, R> function);
    <T> Cast whenCall(Class<T> clazz, Consumer<T> consumer);
    <R> Cast other(R value);
    <R> Cast other(Function<Object, R> function);
    Cast otherCall(Consumer<Object> consumer);
    <R> R end();
}

class CastPipeline implements Cast {
    private Object value;
    private Object result;
    private boolean matched;

    CastPipeline(Object value) {
        this.value = value;
    }

    @Override
    public <T, R> Cast when(Class<T> clazz, R result) {
        if (!matched) {
            matched = clazz.isInstance(value);
            if (matched) {
                this.result = result;
            }
        }
        return this;
    }

    @Override
    public <T, R> Cast when(Class<T> clazz, Function<T, R> function) {
        if (!matched) {
            matched = clazz.isInstance(value);
            if (matched) {
                result = function.apply(clazz.cast(value));
            }
        }
        return this;
    }

    @Override
    public <T> Cast whenCall(Class<T> clazz, Consumer<T> consumer) {
        if (!matched) {
            matched = clazz.isInstance(value);
            if (matched) {
                consumer.accept(clazz.cast(value));
            }
        }
        return this;
    }

    @Override
    public <R> Cast other(R result) {
        if (!matched) {
            matched = true;
            this.result = result;
        }
        return this;
    }

    @Override
    public <R> Cast other(Function<Object, R> function) {
        if (!matched) {
            matched = true;
            this.result = function.apply(value);
        }
        return this;
    }

    @Override
    public Cast otherCall(Consumer<Object> consumer) {
        if (!matched) {
            matched = true;
            consumer.accept(value);
        }
        return this;
    }

    @Override
    public <R> R end() {
        return (R) result;
    }
}
