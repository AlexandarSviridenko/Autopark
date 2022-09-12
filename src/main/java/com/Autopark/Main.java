package com.Autopark;

import com.Autopark.Auto.VehicleCollection;
import com.Autopark.entity.Types;
import com.Autopark.entity.Vehicles;
import com.Autopark.infrastructure.configuratots.impl.AutowiredObjectConfigurator;
import com.Autopark.infrastructure.configuratots.ObjectConfigurator;
import com.Autopark.infrastructure.core.impl.ApplicationContext;
import com.Autopark.infrastructure.orm.impl.EntityManagerImp;
import com.Autopark.infrastructure.orm.service.PostgreDataBaseService;
//import com.Autopark.parser.ParserVehicleFromFile;
import com.Autopark.parser.ParserVehicleFromDB;
import com.Autopark.parser.ParserVehicleInterface;
//import com.Autopark.repairAuto.BadMechanicService;
import com.Autopark.repairAuto.Fixer;
import com.Autopark.repairAuto.MechanicService;
import com.Autopark.repairAuto.Workroom;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = initInterfaceToImplementation();
        ApplicationContext context = new ApplicationContext("src/main/java/com/Autopark", interfaceToImplementation);

//        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
//        Workroom workroom = context.getObject(Workroom.class);
//
//        printRents(vehicleCollection);
//        workroom.checkAllVehicles(vehicleCollection.getVehicles());
//
//        AutoCheck autoCheck = context.getObject(AutoCheck.class);
//        autoCheck.vehiclesFromDBToWorkroom(context);



        Types types1 = new Types(1L,"Bus",1.2);
        Types types2 = new Types(2L,"Car",1.0);
        Types types3 = new Types(3L,"Rink",1.5);
        Types types4 = new Types(4L,"Tractor",1.2);

        Vehicles vehicles1 = new Vehicles(1L,1L,"Volkswagen Crafter","5427 AX-7",2022,2015,376000,"Blue","Gasoline");
        Vehicles vehicles2 = new Vehicles(2L,1L,"Volkswagen Crafter","6427 AA-7",2500,2014,227010,"White","Gasoline");
        Vehicles vehicles3 = new Vehicles(3L,1L,"Electric Bus E321","6785 BA-7",12080,2019,20451,"Green","Electrical");
        Vehicles vehicles4 = new Vehicles(4L,2L,"Golf 5","8682 AX-7",1200,2006,230451,"Gray","Diesel");
        Vehicles vehicles5 = new Vehicles(5L,2L,"Tesla Model S 70D","8691 AX-3",2200,2019,10454,"White","Electrical");
        Vehicles vehicles6 = new Vehicles(6L,3L,"Hamm HD 12 VV","9327 BC-4",3000,2016,122,"Yellow","Diesel");
        Vehicles vehicles7 = new Vehicles(6L,4L,"МТЗ Беларус-1025.4","1145 AB-7",1200,2020,109,"Red","Diesel");




//        PostgreDataBaseService postgreDataBaseServiceType = new PostgreDataBaseService();
//        postgreDataBaseServiceType.save(types1);
//        postgreDataBaseServiceType.save(types2);
//        postgreDataBaseServiceType.save(types3);
//        postgreDataBaseServiceType.save(types4);

//        TypesService typesService = new TypesService();
//        typesService.save(types1);
//        typesService.save(types2);
//        typesService.save(types3);
//        typesService.save(types4);
//
//        VehicleService vehicleService = new VehicleService();
//        vehicleService.save(vehicles1);
//        vehicleService.save(vehicles2);
//        vehicleService.save(vehicles3);
//        vehicleService.save(vehicles4);
//        vehicleService.save(vehicles5);
//        vehicleService.save(vehicles6);
//        vehicleService.save(vehicles7);

        EntityManagerImp entityManagerImp = new EntityManagerImp();
        entityManagerImp.save(types1);
        entityManagerImp.save(types2);
        entityManagerImp.save(types3);
        entityManagerImp.save(types4);

        entityManagerImp.save(vehicles1);
        entityManagerImp.save(vehicles2);
        entityManagerImp.save(vehicles3);
        entityManagerImp.save(vehicles4);
        entityManagerImp.save(vehicles5);
        entityManagerImp.save(vehicles6);
        entityManagerImp.save(vehicles7);



//        PostgreDataBaseService postgreDataBaseService = new PostgreDataBaseService();
//        postgreDataBaseService.save(vehicles1);
//        postgreDataBaseService.save(vehicles2);
//        postgreDataBaseService.save(vehicles3);
//        postgreDataBaseService.save(vehicles4);
//        postgreDataBaseService.save(vehicles5);
//        postgreDataBaseService.save(vehicles6);
//        postgreDataBaseService.save(vehicles7);


        //ВОПРОСЫ С ДАТОЙ
//        Rents rents1 = new Rents(2L, new Date(2021, 10, 01), 68.0);
//        Rents rents2 = new Rents(1L, new Date(2021, 10, 01), 123.25);
//        Rents rents3 = new Rents(5L, new Date(2021, 10,03), 87.0);
//        Rents rents4 = new Rents(7L, new Date(2021, 10, 05),  42.0);
//        Rents rents5 = new Rents(5L, new Date(2021,10,07), 150.0);
//        Rents rents6 = new Rents(5L, new Date(2021,10,10), 47.0);
//        Rents rents7 = new Rents(5L, new Date(2021,10,15), 80.0);
//        Rents rents8 = new Rents(5L, new Date(2021, 10, 18), 150.0);
//        Rents rents9 = new Rents(5L, new Date(2021,10,19), 36.0);
//        Rents rents10 = new Rents(5L, new Date(2021,10,20), 60.50);
//        Rents rents11 = new Rents(5L, new Date(2021,10,22), 220.50);
//
//        RentsService rentsService = new RentsService();
//        rentsService.save(rents1);
//        rentsService.save(rents2);
//        rentsService.save(rents3);
//        rentsService.save(rents4);
//        rentsService.save(rents5);
//        rentsService.save(rents6);
//        rentsService.save(rents7);
//        rentsService.save(rents8);
//        rentsService.save(rents9);
//        rentsService.save(rents10);
//        rentsService.save(rents11);



        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Map<Class<?>, Class<?>> initInterfaceToImplementation() {
        Map<Class<?>, Class<?>> map = new HashMap<>();
        map.put(ObjectConfigurator.class, AutowiredObjectConfigurator.class);
        map.put(EntityManagerImp.class, PostgreDataBaseService.class);
        map.put(Fixer.class, MechanicService.class);
        map.put(ParserVehicleInterface.class, ParserVehicleFromDB.class);

        return map;
    }

    private static void printRents(VehicleCollection vehicleCollection) {
        System.out.println(vehicleCollection.getRents());
    }
}