package com.hwamok.util;

import org.springframework.util.Assert;

import java.util.Objects;

public class Triple<F, S, T> {

    private final F first;

    private final S second;

    private final T third;

    private Triple(F first, S second, T third) {

        Assert.notNull(first, "first is not null");
        Assert.notNull(second, "second is not null");
        Assert.notNull(third, "third is not null");

        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <F, S, T> Triple<F, S, T> of(F first, S second, T third){
        return new Triple<>(first, second, third);
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public T getThird() {
        return third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(first, triple.first) && Objects.equals(second, triple.second) && Objects.equals(third, triple.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}
