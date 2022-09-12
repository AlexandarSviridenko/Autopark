package com.Autopark.service;

//import com.Autopark.Auto.VehicleType;
import com.Autopark.entity.Types;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.infrastructure.orm.EntityManager;

import java.util.List;

public class TypesService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() { }

    public Types get(Long id) {
        return entityManager.get(id, Types.class).get();
    }

    public List<Types> getAll() {
        return entityManager.getAll(Types.class);
    }

    public Long save(Types type) {
        return entityManager.save(type);
    }
}
