package com.javaprojektni.tasker.Interfaces;

import com.javaprojektni.tasker.model.Task;

public sealed interface Sealed permits Task {
    String ids();
}
