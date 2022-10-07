package com.Autopark.Auto;

import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.core.Context;
import com.Autopark.infrastructure.orm.EntityManager;
import com.Autopark.infrastructure.threads.annotations.Schedule;
import com.Autopark.repairAuto.Workroom;

import java.util.List;

public class AutoCheck {

    public AutoCheck() {}

    @Schedule(delta = 10000,timeout = 10000)
    public void vehiclesFromDBToWorkroom(Context context) {
        EntityManager manager = context.getObject(EntityManager.class);
        List<Vehicles> vehicle = manager.getAll(Vehicles.class);
        context.getObject(Workroom.class).checkAllVehicles(vehicle);
    }
}
