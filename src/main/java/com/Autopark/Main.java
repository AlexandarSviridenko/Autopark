package com.Autopark;

import com.Autopark.Auto.AutoCheck;
import com.Autopark.Auto.VehicleCollection;
import com.Autopark.infrastructure.configuratots.impl.AutowiredObjectConfigurator;
import com.Autopark.infrastructure.configuratots.ObjectConfigurator;
import com.Autopark.infrastructure.core.impl.ApplicationContext;
import com.Autopark.infrastructure.orm.EntityManager;
import com.Autopark.infrastructure.orm.service.PostgreDataBaseService;
import com.Autopark.parser.ParserVehicleFromDB;
import com.Autopark.parser.ParserVehicleInterface;
import com.Autopark.repairAuto.Fixer;
import com.Autopark.repairAuto.MechanicService;
import com.Autopark.repairAuto.Workroom;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("com.Autopark", interfaceToImplementation);

        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        Workroom workroom = context.getObject(Workroom.class);

        printRents(vehicleCollection);
        workroom.checkAllVehicles(vehicleCollection.getVehicles());

        AutoCheck autoCheck = context.getObject(AutoCheck.class);
        autoCheck.vehiclesFromDBToWorkroom(context);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Map<Class<?>, Class<?>> initInterfaceToImplementation() {
        Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(ObjectConfigurator.class, AutowiredObjectConfigurator.class);
        map.put(EntityManager.class, PostgreDataBaseService.class);
        map.put(Fixer.class, MechanicService.class);
        map.put(ParserVehicleInterface.class, ParserVehicleFromDB.class);

        return map;
    }

    private static void printRents(VehicleCollection vehicleCollection) {
        System.out.println(vehicleCollection.getRents());
    }
}