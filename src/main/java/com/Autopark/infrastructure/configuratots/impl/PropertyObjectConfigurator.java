package com.Autopark.infrastructure.configuratots.impl;

import com.Autopark.infrastructure.configuratots.ObjectConfigurator;
import com.Autopark.infrastructure.core.Context;
import com.Autopark.infrastructure.core.annotations.Property;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyObjectConfigurator implements ObjectConfigurator {

    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader()
                .getResource("application.properties");
        if (path == null) {
            throw new FileNotFoundException(String.format("File '%s' not found", "application.properties"));
        }

        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream()))
                .lines();
        properties = lines.map(line -> line.split("="))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        String key;
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {

                key = getPropertyValue(field);

                if (key.isEmpty()) {
                    key = field.getName();
                }

                field.setAccessible(true);
                field.set(object, properties.get(key));
            }
        }
    }

    private String getPropertyValue(Field field) {
        return field.getAnnotation(Property.class).value();
    }
}
