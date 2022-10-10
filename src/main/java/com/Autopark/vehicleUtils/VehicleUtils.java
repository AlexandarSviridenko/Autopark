package com.Autopark.vehicleUtils;

import com.Autopark.Auto.Rent;
import com.Autopark.entity.Rents;
import com.Autopark.entity.Types;
import com.Autopark.entity.Vehicles;

import java.util.List;

public class VehicleUtils {
    public static double getCalcTaxPerMonth(Vehicles vehicle, Types type) {
        return (double) Math.round(((vehicle.getWeight() * 0.0013) + (type.getCoefTaxes() * vehicle.getEngineTaxCoefficient() * 30) + 5) * 100) / 100;
    }

    public static double getTotalIncome(List<Rents> rents) {
        double sum = 0.0d;

        for (Rents rent: rents) {
            sum += rent.getCost();
        }
        return (double) Math.round(sum * 100) / 100;
    }

    public static double getMaxKilometers(Vehicles vehicle) {
        return (double) Math.round((vehicle.getFuelTankCapacity() * 100 /vehicle.getFuelConsumptionPer100()) * 100) / 100;
    }
}
