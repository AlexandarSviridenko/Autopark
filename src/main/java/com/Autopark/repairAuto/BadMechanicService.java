package com.Autopark.repairAuto;

import com.Autopark.Auto.Vehicle;
import com.Autopark.entity.Vehicles;

import java.util.HashMap;
import java.util.Map;

public class BadMechanicService implements Fixer {

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicle) {
        return new HashMap<>();
    }

    @Override
    public void repair(Vehicles vehicle) {

    }

    @Override
    public boolean isBroken(Vehicles vehicle) {
        return false;
    }
}
