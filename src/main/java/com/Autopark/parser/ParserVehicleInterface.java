package com.Autopark.parser;

import com.Autopark.Auto.Rent;
import com.Autopark.Auto.Vehicle;
import com.Autopark.Auto.VehicleType;
import com.Autopark.entity.Rents;

import java.util.List;

public interface ParserVehicleInterface {
    List<VehicleType> loadTypes();

    List<Vehicle> loadVehicles();

    List<Rents> loadRents();
}
