package com.Autopark.service;

import com.Autopark.Auto.Vehicle;
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

    public Vehicle get(Long id) {
        return entityManager.get(id, Vehicle.class).get();
    }

    public List<Vehicle> getAll() {
        return entityManager.getAll(Vehicle.class);
    }

    public Long save(Vehicle vehicle) {
        return entityManager.save(vehicle);
    }
}
