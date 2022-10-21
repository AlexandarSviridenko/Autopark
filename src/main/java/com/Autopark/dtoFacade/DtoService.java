package com.Autopark.dtoFacade;

import com.Autopark.dto.OrderDto;
import com.Autopark.dto.RentDto;
import com.Autopark.dto.VehicleDto;
import com.Autopark.dto.VehicleTypeDto;
import com.Autopark.entity.Types;
import com.Autopark.infrastructure.core.annotations.Autowired;
import com.Autopark.service.OrdersService;
import com.Autopark.service.RentsService;
import com.Autopark.service.TypesService;
import com.Autopark.service.VehicleService;
import com.Autopark.vehicleUtils.VehicleUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DtoService {
    @Autowired
    VehicleService vehicleService;
    @Autowired
    TypesService typeService;
    @Autowired
    RentsService rentService;
    @Autowired
    OrdersService orderService;

    public DtoService() { }

    public List<VehicleDto> getVehicles() {
        return vehicleService.getAll().stream().map(vehicle -> {
            Types type = typeService.get(vehicle.getTypesId());

            return VehicleDto.builder()
                    .id(vehicle.getId())
                    .typeName(type.getName())
                    .taxCoefficient(type.getCoefTaxes())
                    .color(vehicle.getColor())
                    .engineName(vehicle.getEngineType())
                    .engineTaxCoefficient(vehicle.getEngineTaxCoefficient())
                    .tax(VehicleUtils.getCalcTaxPerMonth(vehicle, type))
                    .manufactureYear(vehicle.getManufactureYear())
                    .mileage(vehicle.getMileage())
                    .modelName(vehicle.getModel())
                    .registrationNumber(vehicle.getRegistrationNumber())
                    .tankVolume(vehicle.getFuelTankCapacity())
                    .weight(vehicle.getWeight())
                    .per100km(vehicle.getFuelConsumptionPer100())
                    .maxKm(VehicleUtils.getMaxKilometers(vehicle))
                    .income(VehicleUtils.getTotalIncome(rentService.getAll()
                            .stream()
                            .filter(rent -> rent.getCarId().equals(vehicle.getId()))
                            .collect(Collectors.toList())))
                    .build();
        }).collect(Collectors.toList());
    }

    public List<VehicleTypeDto> getTypes() {
        return typeService.getAll().stream().map(type ->
                VehicleTypeDto.builder()
                        .typeId(type.getId())
                        .name(type.getName())
                        .taxCoefficient(type.getCoefTaxes())
                        .build()).collect(Collectors.toList());
    }

    public List<RentDto> getRents(long id) {
        return rentService.getAll().stream().filter(rent -> rent.getCarId().equals(id)).map(rent ->
                RentDto.builder()
                        .carId(id)
                        .date(rent.getDate())
                        .cost(rent.getCost())
                        .build()).collect(Collectors.toList());
    }

    public List<OrderDto> getOrders() {
        return orderService.getAll().stream().map(order ->
                OrderDto.builder()
                        .vehicleId(order.getVehicleId())
                        .defect(order.getDefect())
                        .breakingAmount(order.getBreakingAmount())
                        .build()).collect(Collectors.toList());
    }
}
