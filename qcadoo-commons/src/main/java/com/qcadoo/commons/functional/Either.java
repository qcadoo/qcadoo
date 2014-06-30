package com.qcadoo.commons.functional;

import org.apache.commons.lang.ObjectUtils;

import com.google.common.base.Function;

/**
 * 
 * @param <L>
 * @param <R>
 */
public abstract class Either<L, R> {

    public static <L2, R2> Left<L2, R2> left(final L2 value) {
        return new Left(value);
    }

    public static <L2, R2> Right<L2, R2> right(final R2 value) {
        return new Right(value);
    }

    private Either() {
    }

    public abstract boolean isLeft();

    public boolean isRight() {
        return !isLeft();
    }

    public L getLeft() {
        throw new IllegalStateException("Calling getLeft() on Right!");
    }

    public R getRight() {
        throw new IllegalStateException("Calling getRight() on Left!");
    }

    public abstract <V> V fold(final Function<? super L, V> ifLeft, final Function<? super R, V> ifRight);

    private static final class Left<L, R> extends Either<L, R> {

        private final L value;

        private Left(final L value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public L getLeft() {
            return value;
        }

        @Override
        public <V> V fold(final Function<? super L, V> ifLeft, final Function<? super R, V> ignored) {
            return ifLeft.apply(value);
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
            Left oth = (Left) obj;
            return ObjectUtils.equals(value, oth.value);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.hashCode(value);
        }

        @Override
        public String toString() {
            return String.format("Left(%s)", ObjectUtils.toString(value));
        }
    }

    private static final class Right<L, R> extends Either<L, R> {

        private final R value;

        private Right(final R value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public R getRight() {
            return value;
        }

        @Override
        public <V> V fold(final Function<? super L, V> ignored, final Function<? super R, V> ifRight) {
            return ifRight.apply(value);
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
            Right oth = (Right) obj;
            return ObjectUtils.equals(value, oth.value);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.hashCode(value);
        }

        @Override
        public String toString() {
            return String.format("Right(%s)", ObjectUtils.toString(value));
        }
    }

}
