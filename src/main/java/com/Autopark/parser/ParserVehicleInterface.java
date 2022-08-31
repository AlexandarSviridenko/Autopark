package com.Autopark.parser;

import com.Autopark.Auto.Rent;
import com.Autopark.Auto.Vehicle;
import com.Autopark.Auto.VehicleType;
import com.Autopark.entity.Rents;
import com.Autopark.entity.Vehicles;

import java.util.List;

public interface ParserVehicleInterface {
    List<VehicleType> loadTypes();

    List<Vehicles> loadVehicles();

    List<Rents> loadRents();
}
