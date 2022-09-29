package com.Autopark.repairAuto;

import com.Autopark.entity.Vehicles;

import java.util.Map;

public interface Fixer {
    Map<String, Integer> detectBreaking(Vehicles vehicle);

    void repair(Vehicles vehicle);

    default boolean detectAndRepair(Vehicles vehicle) {
        if (isBroken(vehicle)) {
            repair(vehicle);
            System.out.println("was repair: " + vehicle);
            return true;
        }

        return false;
    }

    boolean isBroken(Vehicles vehicle);
}
