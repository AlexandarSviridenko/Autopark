package com.Autopark;

import com.Autopark.Engine.Startable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.Autopark.TechnicalSpecialist.*;

public class Vehicle {
    private VehicleType type;
    private String modelName;
    private String registrationNumber;
    private int weight;
    private int manufactureYear;
    private int mileage;
    private Color color;
    private int volumeTank;
    private Startable engine;
    private int id;
    private List<Rent> rents = new ArrayList<>();

    public Vehicle(int id,
                   VehicleType type,
                   Startable engine,
                   String modelName,
                   String registrationNumber,
                   int weight,
                   int manufactureYear,
                   int mileage,
                   Color color
    ) {
        try {
            this.id = id;
            if (!validateVehicleType(type)) {
                throw new NotVehicleException("Vehicle type: " + type);
            }
            this.type = type;

            if (!validateModelName(modelName)) {
                throw new NotVehicleException("Model name: " + modelName);
            }
            this.modelName = modelName;

            if (!validateRegistrationNumber(registrationNumber)) {
                throw new NotVehicleException("Registration number:" + registrationNumber);
            }
            this.registrationNumber = registrationNumber;

            if (!validateWeight(weight)) {
                throw new NotVehicleException("Weight: " + weight);
            }
            this.weight = weight;

            if (!validateManufactureYear(manufactureYear)) {
                throw new NotVehicleException("Manufacture year: " + manufactureYear);
            }
            this.manufactureYear = manufactureYear;

            if (!validateMileage(mileage)) {
                throw new NotVehicleException("Mileage: " + mileage);
            }
            this.mileage = mileage;

            if (!validateColor(color)) {
                throw new NotVehicleException("Color: " + color);
            }
            this.color = color;
            this.engine = engine;
        } catch (NotVehicleException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }

    public double getTotalIncome() {
        double sum = 0.0d;

        for (Rent rent : rents) {
            sum += rent.getRent();
        }
        return sum;
    }


    public double getTotalProfit() {
        return getTotalIncome() - getCalcTaxPerMonth();
    }

    public Vehicle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Startable getEngine() {
        return engine;
    }

    public void setEngine(Startable engine) {
        this.engine = engine;
    }

    public VehicleType getType() {
        return type;
    }

    public String getModelName() {
        return modelName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getCalcTaxPerMonth() {
        double GetCalcTaxPerMonth = getWeight() * 0.0013 + type.getTax() * engine.getTaxPerMonth() * 30 + 5;
        return new BigDecimal(GetCalcTaxPerMonth).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    @Override
    public String toString() {
        return type + "," + modelName + "," + registrationNumber + "," + weight + "," + manufactureYear + ","
                + mileage + "," + color + "," /*+ volumeTank + ","*/ + getCalcTaxPerMonth();

    }


    public int compareTo(Vehicle obj) {
        if (manufactureYear > obj.manufactureYear) {
            return 1;
        } else if (manufactureYear < obj.manufactureYear) {
            return -1;
        } else {
            if (mileage > obj.mileage) {
                return 1;
            } else if (mileage < obj.mileage) {
                return -1;
            }
        }
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return type.equals(vehicle.type) && modelName.equals(vehicle.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, modelName);
    }
}

