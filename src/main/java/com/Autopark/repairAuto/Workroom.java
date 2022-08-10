package com.Autopark.repairAuto;

import com.Autopark.Auto.Vehicle;
import com.Autopark.infrastructure.core.annotations.Autowired;

import java.util.List;
import java.util.stream.Stream;

public class Workroom {
    @Autowired
    private Fixer mechanic;

    public Workroom() {
    }

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(Fixer mechanic) {
        this.mechanic = mechanic;
    }

    public void checkAllVehicles(List<Vehicle> vehicles) {
        vehicles.forEach(f -> mechanic.detectBreaking(f));
        showBroken(vehicles.stream());
        System.out.println("Started repair:");
        vehicles.forEach(f1 -> mechanic.detectAndRepair(f1));
        showServiceable(vehicles.stream());
    }

    private void showBroken(Stream<Vehicle> stream) {
        System.out.println("Broken vehicles:");
        stream.filter(v -> mechanic.isBroken(v)).forEach(System.out::println);
    }

    private void showServiceable(Stream<Vehicle> stream) {
        System.out.println("Serviceable vehicles:");
        stream.filter(v -> !mechanic.isBroken(v)).forEach(System.out::println);
    }
}
