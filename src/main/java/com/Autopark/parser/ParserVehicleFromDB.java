package com.Autopark.parser;

import com.Autopark.entity.Rents;
import com.Autopark.entity.Types;
import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.service.RentsService;
import com.Autopark.service.TypesService;
import com.Autopark.service.VehicleService;

import java.util.List;

public class ParserVehicleFromDB implements ParserVehicleInterface {
    @Autowired
    private TypesService typesService;
    @Autowired
    private VehicleService vehiclesService;
    @Autowired
    private RentsService rentsService;

    public ParserVehicleFromDB() {
    }

    @Override
    public List<Types> loadTypes() {
        return typesService.getAll();
    }

    @Override
    public List<Vehicles> loadVehicles() {
        return vehiclesService.getAll();
    }

    @Override
    public List<Rents> loadRents() {
        return rentsService.getAll();
    }
}
