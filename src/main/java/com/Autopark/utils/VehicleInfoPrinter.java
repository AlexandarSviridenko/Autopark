//package com.Autopark.utils;
//
//import com.Autopark.Auto.Vehicle;
//import com.Autopark.Auto.VehicleType;
//
//import static com.Autopark.utils.VehicleUtils.*;
//
//public class VehicleInfoPrinter {
//    public static final int SIZE_VEHICLES = 7;
//
//    public static void printlnEqualVehicleType(Vehicle[] vehicle) {
//        System.out.println("\nThe following vehicles are equal:");
//        displayEqual(vehicle);
//    }
//
//    public static void printlnGetStringVehicleType(VehicleType[] as) {
//        for (VehicleType a : as
//        ) {
//            System.out.println(a.getString());
//        }
//    }
//
//    public static void printlnMaxAndMinMileage(Vehicle[] vehicle) {
//        System.out.println("\nMax mileage car: ");
//        System.out.println(findMaxMileageVehicle(vehicle));
//
//        System.out.println("\nMin mileage car: ");
//        System.out.println(findMinMileageVehicle(vehicle));
//    }
//
//    public static void printlnMaxTax(VehicleType[] vehicleTypes) {
//        System.out.println("maxTax = " + VehicleUtils.calculationMaxTax(vehicleTypes) + "\n" + "average = " + calculationAverage(vehicleTypes));
//    }
//
//    public static void displayEqual(Vehicle[] vehicles) {
//        int counter = 0;
//        for (int i = 0; i < SIZE_VEHICLES; i++) {
//            for (int j = i + 1; j < SIZE_VEHICLES; j++) {
//                if (vehicles[i].equals(vehicles[j])) {
//                    counter++;
//                    System.out.println(vehicles[i] + " and " + vehicles[j]);
//                }
//            }
//        }
//        if (counter == 0) {
//            System.out.println("Neither of vehicles are equal.");
//        }
//    }
//    public static void printAutoMaxKilometrs(Vehicle[] vehicles){
//        autoMaxKilometrs(vehicles);
//    }
//}