package com.Autopark.service;

import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.infrastructure.orm.EntityManager;

import java.util.List;

public class VehicleService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Vehicles get(Long id) {
        return entityManager.get(id, Vehicles.class).get();
    }

    public List<Vehicles> getAll() {
        return entityManager.getAll(Vehicles.class);
    }

    public Long save(Vehicles vehicle) {
        return entityManager.save(vehicle);
    }
}
