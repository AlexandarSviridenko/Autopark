package com.Autopark.infrastructure.orm.impl;

import com.Autopark.infrastructure.core.Context;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.orm.ConnectionFactory;
import com.Autopark.infrastructure.orm.EntityManager;
import com.Autopark.infrastructure.orm.service.PostgreDataBaseService;


import java.util.List;
import java.util.Optional;

public class EntityManagerImp implements EntityManager {
    @Autowired
    private ConnectionFactory connection;

    @Autowired
    private PostgreDataBaseService dataBaseService;

    @Autowired
    private Context context;

    public EntityManagerImp() {
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        return dataBaseService.get(id, clazz);
    }

    @Override
    public Long save(Object object) {
        return dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dataBaseService.getAll(clazz);
    }
}
