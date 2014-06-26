package com.qcadoo.commons.functional;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Optional;

public final class Optionals {

    private Optionals() {
    }

    public static <F> Function<F, Optional<F>> lift() {
        return new Function<F, Optional<F>>() {

            @Override
            public Optional<F> apply(final F input) {
                return Optional.fromNullable(input);
            }
        };
    }

    public static <F, T> Function<F, Optional<T>> lift(final Function<F, T> f) {
        Function<T, Optional<T>> lift = lift();
        return Functions.compose(lift, f);
    }

    public static <F, T> Optional<T> flatMap(final Optional<F> optValue, final Function<F, Optional<T>> f) {
        return optValue.transform(f).or(Optional.<T> absent());
    }

}
