package com.qcadoo.commons.functional;

public final class Fold {

    private Fold() {
    }

    public static <F, T> T fold(final Iterable<F> iterable, final T zero, final BiFunction<T, F, T> f) {
        T acc = zero;
        for (F el : iterable) {
            acc = f.apply(acc, el);
        }
        return acc;
    }

}
