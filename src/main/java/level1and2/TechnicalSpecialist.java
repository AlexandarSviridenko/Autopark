package level1and2;

import Engine.DieselEngine;
import Engine.ElectricalEngine;
import Engine.GasolineEngine;

public class TechnicalSpecialist {
    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    static public boolean validateManufactureYear(int manufactureYear) {
        if (manufactureYear >= LOWER_LIMIT_MANUFACTURE_YEAR) {
            return true;
        } else {
            return false;
        }
    }

    static public boolean validateMileage(int mileage) {
        if (mileage >= 0) {
            return true;
        } else {
            return false;
        }
    }

    static public boolean validateWeight(int weight) {
        if (weight >= 0) {
            return true;
        } else {
            return false;
        }
    }

    static public boolean validateColor(Color color) {
        if (color != null) {
            return true;
        } else {
            return false;
        }
    }

    static public boolean validateVehicleType(VehicleType type) {
        String name = type.getName();
        if (name != null && !(name.equals(""))) {
            return type.getTax() > 0.0;
        }

        return false;
    }

    static public boolean validateRegistrationNumber(String registrationNumber) {

        if (registrationNumber == null || registrationNumber.length() != 9) {
            return false;
        }

        char[] chars = registrationNumber.toCharArray();

        if (!isNumber(chars[0], chars[1], chars[2], chars[3], chars[8])) {
            return false;
        }

        if (chars[4] != ' ') {
            return false;
        }

        if (!isCapLetter(chars[5], chars[6])) {
            return false;
        }

        return chars[7] == '-';
    }

    private static boolean isNumber(char... chars) {
        for (char ch : chars) {
            if (ch < '0' || ch > '9') {
                return false;
            }
        }

        return true;
    }

    private static boolean isCapLetter(char... chars) {
        for (char ch : chars) {
            if (ch < 'A' || ch > 'Z') {
                return false;
            }
        }

        return true;
    }

    static public boolean validateModelName(String name) {
        return name != null && !name.equals("");
    }
    public static boolean validateGasolineEngine(GasolineEngine engine) {
        if (engine == null) {
            return false;
        }
        return engine.getEngineCapacity() > 0 && engine.getFuelTankCapacity() > 0 && engine.getFuelConsuptionPer100() > 0;
    }

    public static boolean validateDieselEngine(DieselEngine engine) {
        if (engine == null) {
            return false;
        }

        return engine.getEngineCapacity() > 0 && engine.getFuelTankCapacity() > 0 && engine.getFuelConsuptionPer100() > 0;
    }

    public static boolean validateElectricalEngine(ElectricalEngine engine) {
        if (engine == null) {
            return false;
        }

        return engine.getBatterySize() > 0 && engine.getElectricityConsumption() > 0;
    }
}

