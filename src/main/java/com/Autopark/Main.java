package com.Autopark;

import com.Autopark.Engine.DieselEngine;
import com.Autopark.Engine.ElectricalEngine;
import com.Autopark.Engine.GasolineEngine;
import com.Autopark.infrastructure.core.impl.ApplicationContext;
import com.Autopark.utils.VehicleUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.Autopark.utils.VehicleUtils.*;

public class Main {

    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementationMechanicService();
        ApplicationContext context = new ApplicationContext("src/main/java/com/Autopark", interfaceToImplementation);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        Workroom workroom = context.getObject(Workroom.class);
        workroom.checkAllVehicles(vehicleCollection.getVehicles());
    }

    private static Map<Class<?>, Class<?>> initInterfaceToImplementationBadMechanicService() {
        Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(Fixer.class, BadMechanicService.class);
        return map;
    }

    private static Map<Class<?>, Class<?>> initInterfaceToImplementationMechanicService() {
        Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(Fixer.class, MechanicService.class);
        return map;
    }
}