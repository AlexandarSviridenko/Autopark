package com.Autopark.infrastructure.core;

import com.Autopark.infrastructure.config.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}
