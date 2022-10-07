package com.Autopark.infrastructure.configuratots;

import com.Autopark.infrastructure.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T object, Class<T> implementation, Context context);
}
