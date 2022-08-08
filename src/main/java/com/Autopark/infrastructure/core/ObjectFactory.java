package com.Autopark.infrastructure.core;

public interface ObjectFactory {
    <T> T createObject(Class<T> implementation);
}
