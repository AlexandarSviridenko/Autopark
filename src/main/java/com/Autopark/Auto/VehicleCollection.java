package com.Autopark.Auto;

import com.Autopark.entity.Rents;
import com.Autopark.entity.Types;
import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.parser.ParserVehicleInterface;

import java.util.List;

public class VehicleCollection {
    private List<Types> vehicleTypes;
    private List<Vehicles> vehicles;
    private List<Rents> rents;

    @Autowired
    private ParserVehicleInterface parser;

    public VehicleCollection() {
    }

    public List<Types> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<Types> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public void setVehicles(List<Vehicles> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicles> getVehicles() {
        return vehicles;
    }

    public List<Rents> getRents() {
        return rents;
    }

    public void setRents(List<Rents> rents) {
        this.rents = rents;
    }

    @InitMethod
    public void init() {
        vehicleTypes = parser.loadTypes();
        vehicles = parser.loadVehicles();
        rents = parser.loadRents();
    }
}
