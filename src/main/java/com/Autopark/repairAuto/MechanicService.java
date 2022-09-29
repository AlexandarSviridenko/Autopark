package com.Autopark.repairAuto;

import com.Autopark.entity.Orders;
import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.orm.service.PostgreDataBaseService;
import com.Autopark.infrastructure.core.annotations.Autowired;

import java.util.*;

public class MechanicService implements Fixer {
    public static final String[] details = {"Фильтр", "Втулка", "Вал", "Ось",
            "Свеча", "Масло", "ГРМ", "ШРУС"};
    long i = 1;
    private Orders orders;
    private Vehicles vehicle;

    @Autowired
    PostgreDataBaseService postgreDataBaseService;

    public MechanicService() {
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicle) {
        Map<String, Integer> result = new HashMap<>();
        createRandomBrokenDetails(vehicle, result);
        return result;
    }

    public Orders createRandomBrokenDetails(Vehicles vehicle, Map<String, Integer> result) {
        for (String s : details) {
            int randomNumber = (int) (Math.random() * 2);
            if (randomNumber != 0) {
                result.put(s, randomNumber);
                Orders orders = new Orders(i++, vehicle.getId(), s, randomNumber);
                postgreDataBaseService.save(orders);
            }
        }
        return orders;
    }

    @Override
    public void repair(Vehicles vehicle) {
        postgreDataBaseService.delete(vehicle.getId(), Orders.class);
    }

    @Override
    public boolean isBroken(Vehicles vehicle) {
        if (postgreDataBaseService.getColumn(vehicle.getId(), Orders.class) != null) {
            return true;
        } else {
            return false;
        }
    }
}
