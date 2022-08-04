package com.Autopark;

import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;

import java.util.List;

public class VehicleCollection {
    private List<VehicleType> vehicleTypes;
    private List<Vehicle> vehicles;

    @Autowired
    private ParserVehicleFromFile parser;

    public VehicleCollection() {
    }

    public List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @InitMethod
    public void init() {
        vehicleTypes = parser.loadTypes();
        vehicles = parser.loadVehicles();
    }
}
