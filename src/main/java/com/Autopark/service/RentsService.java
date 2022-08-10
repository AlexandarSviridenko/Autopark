package com.Autopark.service;

import com.Autopark.Auto.Rent;
import com.Autopark.entity.Rents;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.infrastructure.orm.EntityManager;

import java.util.List;

public class RentsService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Rents get(Long id) {
        return entityManager.get(id, Rents.class).get();
    }

    public List<Rents> getAll() {
        return entityManager.getAll(Rents.class);
    }

    public Long save(Rents rent) {
        return entityManager.save(rent);
    }
}
