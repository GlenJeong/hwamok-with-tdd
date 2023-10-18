package com.hwamok.core;

public class Preconditions {
    public static void require(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }
}
