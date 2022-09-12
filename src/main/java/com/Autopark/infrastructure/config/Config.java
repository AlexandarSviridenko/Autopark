package com.Autopark.infrastructure.config;

import com.Autopark.infrastructure.core.Scanner;

public interface Config {
    <T> Class<? extends T> getImplementation(Class<T> target);

    Scanner getScanner();
}
