package com.Autopark.utils;

import com.Autopark.Auto.Vehicle;
import com.Autopark.Auto.VehicleCollection;
import com.Autopark.Auto.VehicleType;
import com.Autopark.Exception.DefectedVehicleException;
import com.Autopark.StackAndQueue.MyQueue;
import com.Autopark.StackAndQueue.MyStack;
import com.Autopark.repairAuto.MechanicService;


public class VehicleUtils {
    public static void changeTaxCoefficient(VehicleType[] as) {
        for (VehicleType a : as) {
            if (a.getName().equals("Tractor")) {
                a.setTax(1.3);
            }
        }
    }

    public static double calculationMaxTax(VehicleType[] as) {
        double maxTax = 0;
        for (VehicleType a : as) {
            if (a.getTax() > maxTax)
                maxTax = a.getTax();
        }
        return maxTax;
    }

    public static double calculationAverage(VehicleType[] as) {
        double sumTax = 0;
        for (VehicleType a : as) {
            sumTax = sumTax + a.getTax();
        }
        double average = sumTax / 4;
        return average;
    }

    public static Vehicle findMinMileageVehicle(Vehicle[] vehicles) {
        int minMileage;
        Vehicle minMileageVehicle;
        minMileageVehicle = vehicles[0];
        minMileage = vehicles[0].getMileage();
        for (int i = 0; i < SIZE_VEHICLES; i++) {
            int mileage = vehicles[i].getMileage();
            if (mileage < minMileage) {
                minMileage = mileage;
                minMileageVehicle = vehicles[i];
            }
        }
        return minMileageVehicle;
    }

    public static Vehicle findMaxMileageVehicle(Vehicle[] vehicles) {
        int maxMileage;
        Vehicle maxMileageVehicle;
        maxMileageVehicle = vehicles[0];
        maxMileage = vehicles[0].getMileage();
        for (int i = 0; i < SIZE_VEHICLES; i++) {
            int mileage = vehicles[i].getMileage();
            if (mileage > maxMileage) {
                maxMileage = mileage;
                maxMileageVehicle = vehicles[i];
            }
        }
        return maxMileageVehicle;
    }

    public static <T> void displayArr(T[] elements) {
        for (T element :
                elements) {
            System.out.println(element.toString());
        }
        System.out.println();
    }

    public static void autoMaxKilometrs(Vehicle[] vehicle) {
        double maxKilometers = 0.0;
        for (Vehicle as : vehicle) {
            if (as.getEngine().getMaxKilometers() - maxKilometers > 0) {
                maxKilometers = as.getEngine().getMaxKilometers();
            }
        }

        for (Vehicle as : vehicle) {
            if (maxKilometers == as.getEngine().getMaxKilometers()) {
                System.out.println(as);
            }
        }
    }

    public static final int SIZE_VEHICLES = 7;

    public static void addCarInQueue(VehicleCollection vehCollection, MyQueue<Vehicle> queue) {
        for (Vehicle vehicle : vehCollection.getVehicles()) {
            queue.enqueue(vehicle);
        }
    }

    public static void washVehiclesQueue(MyQueue<Vehicle> queue) {
        for (int i = 0; i < queue.getSize(); i++) {
            Vehicle vehicle = queue.dequeue();
            if (vehicle == null) {
                return;
            }
            System.out.println(vehicle + " -- is washed up");
        }
    }

    public static void goToTheGarage(VehicleCollection vehCollection, MyStack<Vehicle> stack) {

        for (int i = 0; i < stack.getSize(); i++) {
            Vehicle vehicle = stack.pop();
            if (vehicle == null) {
                return;
            }
            System.out.println(vehicle + " -- has driven out");
        }
    }

    public static void addCarInGarage(VehicleCollection vehCollection, MyStack<Vehicle> stack) {
        for (Vehicle vehicle : vehCollection.getVehicles()) {
            stack.push(vehicle);
            System.out.println(stack.peek() + " -- has driven in");
        }

        System.out.println("Garage is full\n");
    }

    public static void goInMechanicService(VehicleCollection vehCollection) {
        MechanicService mechanicService = new MechanicService();
        for (Vehicle vehicle : vehCollection.getVehicles()) {
            System.out.println(mechanicService.detectBreaking(vehicle));
        }
    }

    public static void goInMechanicService(Vehicle vehicle) {
        MechanicService mechanicService = new MechanicService();
        System.out.println(mechanicService.detectBreaking(vehicle));
    }

    public static void CarTroubleshootingAndRepair(VehicleCollection vehCollection) {
        MechanicService mechanicService = new MechanicService();
        for (Vehicle vehicle : vehCollection.getVehicles()) {
            if (mechanicService.detectAndRepair(vehicle)) {
                System.out.println(vehicle.getModelName() + " was repair ");
            } else {
                System.out.println(vehicle.getModelName() + " don't have breaking ");
            }
        }
    }

    public static void CarTroubleshootingAndRepair(Vehicle vehicle) {
        MechanicService mechanicService = new MechanicService();
            if (mechanicService.detectAndRepair(vehicle)) {
                System.out.println(vehicle.getModelName() + " was repair ");
            } else {
                System.out.println(vehicle.getModelName() + " don't have breaking ");
            }
    }

    public static void printServiceableCars(VehicleCollection vehCollection) {
        MechanicService mechanicService = new MechanicService();
        for (Vehicle vehicle : vehCollection.getVehicles()) {
            if (!(mechanicService.isBroken(vehicle)))
                System.out.println(vehicle);
        }
    }

    public static void printServiceableCars(Vehicle vehicle) {
        MechanicService mechanicService = new MechanicService();
            if (!(mechanicService.isBroken(vehicle)))
                System.out.println(vehicle);
    }

    public static void printMaxCarBroken(VehicleCollection vehCollection) {
        int maxSum = 0;
        maxSum = findMaxSumBroken(vehCollection);
        System.out.println(findMaxCarBroken(vehCollection, maxSum));
    }

    public static int findMaxSumBroken(VehicleCollection vehCollection) {
        MechanicService mechanicService = new MechanicService();
        int maxSum = 0;
        for (Vehicle vehicle : vehCollection.getVehicles()) {
            if (maxSum < vehicle.getSumOfBrokenParts()) {
                maxSum = vehicle.getSumOfBrokenParts();
            }
        }
        return maxSum;
    }

    public static Object findMaxCarBroken(VehicleCollection vehCollection, int maxSum) {
        MechanicService mechanicService = new MechanicService();
        for (Vehicle vehicle : vehCollection.getVehicles()) {
            if (maxSum == vehicle.getSumOfBrokenParts()) {
                return vehicle;
            }
        }
        return null;
    }

    public static void giveInRent(VehicleCollection vehCollection) {
        MechanicService mechanicService = new MechanicService();
        for (Vehicle vehicle : vehCollection.getVehicles()) {
            if (!(mechanicService.isBroken(vehicle))) {
                System.out.println("You can give in rent this car: " + vehicle.getModelName());
            } else {
                try {
                    throw new DefectedVehicleException("Sorry, this car:" + vehicle.getModelName() + " is broken");
                } catch (DefectedVehicleException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
