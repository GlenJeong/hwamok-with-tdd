package com.hwamok.core;
// https://github.com/google/guava/blob/master/guava/src/com/google/common/base/Preconditions.java
// public static void checkArgument(boolean expression) {
//    if (!expression) {
//      throw new IllegalArgumentException();
//    }
//  }

public class Preconditions {
    public static void require(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }
}
