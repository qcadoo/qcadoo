package com.qcadoo.commons.functional;

public interface BiFunction<F1, F2, T> {

    T apply(final F1 arg1, final F2 arg2);
}
