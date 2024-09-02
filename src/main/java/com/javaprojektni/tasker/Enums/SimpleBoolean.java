package com.javaprojektni.tasker.Enums;

public enum SimpleBoolean {
    TRUE(true),
    FALSE(false);

    private final boolean value;

    SimpleBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
