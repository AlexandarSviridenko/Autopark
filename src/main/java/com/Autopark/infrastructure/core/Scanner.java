package com.Autopark.infrastructure.core;

import org.reflections.Reflections;

import java.util.Set;

public interface Scanner {
    <T> Set<Class<? extends T>> getSubtypesOf(Class<T> type);

    Reflections getReflections();
}
