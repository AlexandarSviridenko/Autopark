package com.Autopark.service;

import com.Autopark.entity.Orders;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.infrastructure.orm.EntityManager;

import java.util.List;

public class OrdersService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Orders get(Long id) {
        return entityManager.get(id, Orders.class).get();
    }

    public List<Orders> getAll() {
        return entityManager.getAll(Orders.class);
    }

    public Long save(Orders order) {
        return entityManager.save(order);
    }
}
