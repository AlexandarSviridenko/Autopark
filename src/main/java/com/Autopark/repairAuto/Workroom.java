package com.Autopark.repairAuto;

import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.core.annotations.Autowired;

import java.util.List;
import java.util.stream.Stream;

public class Workroom {
    @Autowired
    private Fixer mechanic;

    public void checkAllVehicles(List<Vehicles> vehicle) {
        vehicle.forEach(f -> mechanic.detectBreaking(f));
        showBroken(vehicle.stream());
        System.out.println("Started repair:");
        vehicle.forEach(f1 -> mechanic.detectAndRepair(f1));
        showServiceable(vehicle.stream());
    }

    private void showBroken(Stream<Vehicles> stream) {
        System.out.println("Broken vehicles:");
        stream.filter(v -> mechanic.isBroken(v)).forEach(System.out::println);
    }

    private void showServiceable(Stream<Vehicles> stream) {
        System.out.println("Serviceable vehicles:");
        stream.filter(v -> mechanic.isBroken(v)).forEach(System.out::println);
    }
}
