package com.qcadoo.commons.functional;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public final class FluentOptional<T> {

    private final Optional<T> optional;

    public static <V> FluentOptional<V> fromNullable(final V valueOrNull) {
        return new FluentOptional<V>(Optional.fromNullable(valueOrNull));
    }

    public static <V> FluentOptional<V> wrap(final Optional<V> optional) {
        return new FluentOptional(optional);
    }

    private FluentOptional(final Optional<T> optional) {
        Preconditions.checkArgument(optional != null, "Cannot build FluentOptional wrapper from null reference!");
        this.optional = optional;
    }

    public <U> FluentOptional<U> transform(final Function<T, U> f) {
        return map(f);
    }

    public <U> FluentOptional<U> map(final Function<T, U> f) {
        return new FluentOptional<U>(optional.transform(f));
    }

    public <U> FluentOptional<U> flatMap(final Function<T, Optional<U>> f) {
        return new FluentOptional<U>(Optionals.flatMap(optional, f));
    }

    public T or(final T defaultValue) {
        return optional.or(defaultValue);
    }

    public Optional<T> toOpt() {
        return optional;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        FluentOptional rhs = (FluentOptional) obj;
        return optional.equals(rhs.optional);
    }

    @Override
    public int hashCode() {
        return optional.hashCode();
    }
}
