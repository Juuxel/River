package org.jetbrains.kotlin.com.intellij.openapi.util.objectTree;

import java.util.Arrays;

// I think the code was crashing without this - Juuz
public final class ThrowableInterner {
    public static Throwable intern(Throwable throwable) {
        return throwable;
    }

    public static int computeHashCode(Throwable throwable) {
        return throwable.hashCode();
    }

    public static int computeTraceHashCode(Throwable throwable) {
        return Arrays.hashCode(throwable.getStackTrace());
    }
}
