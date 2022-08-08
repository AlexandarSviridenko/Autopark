package com.Autopark.infrastructure.config.impl;

import com.Autopark.infrastructure.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set<Class<? extends T>> implementation = scanner.getSubtypesOf(target);

        if (implementation.size() != 1) {
            if (!isImplementationPresent(target)) {
                throw new RuntimeException("target interface has 0 or more than 1 implementation");
            }

            return getImplementationFromMap(target);
        }

        return implementation.iterator().next();
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    private <T> Class<? extends T> getImplementationFromMap(Class<T> target) {
        return (Class<? extends T>) interfaceToImplementation.get(target);
    }

    private <T> boolean isImplementationPresent(Class<T> target) {
        return interfaceToImplementation.get(target) != null;
    }
}
