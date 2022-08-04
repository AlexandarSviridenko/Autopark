package com.Autopark.infrastructure.core.impl;

import com.Autopark.infrastructure.core.Scanner;
import org.reflections.Reflections;

import java.util.Set;

public class ScannerImpl implements Scanner {

    private final Reflections reflections;

    public ScannerImpl(String packageName) {
        reflections = new Reflections(packageName);
    }

    @Override
    public <T> Set<Class<? extends T>> getSubtypesOf(Class<T> type) {
        return reflections.getSubTypesOf(type);
    }

    @Override
    public Reflections getReflections() {
        return reflections;
    }
}
