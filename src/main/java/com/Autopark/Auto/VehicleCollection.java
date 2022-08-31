package com.Autopark.Auto;

import com.Autopark.Auto.Vehicle;
import com.Autopark.entity.Rents;
import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.infrastructure.core.annotations.InitMethod;
import com.Autopark.parser.ParserVehicleFromFile;
import com.Autopark.service.RentsService;

import java.util.List;

public class VehicleCollection {
    private List<VehicleType> vehicleTypes;
    private List<Vehicles> vehicles;
    private List<Rents> rents;

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
