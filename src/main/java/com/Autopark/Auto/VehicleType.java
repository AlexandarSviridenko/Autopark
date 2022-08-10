package com.Autopark.Auto;

public class VehicleType {

   private String typeName;
   private double taxCoefficient;
   private int id;

    public VehicleType(String typeName, double taxCoefficient, int id) {
        this.typeName = typeName;
        this.taxCoefficient = taxCoefficient;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleType() {
    }

    public String getName() {
        return typeName;
    }

    public double getTax() {
        return taxCoefficient;
    }

    public void setName(String typeName) {
        this.typeName = typeName;
    }

    public void setTax(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }

    @Override
    public String toString() {
        return typeName + "," + taxCoefficient;
    }

    public String getString() {
        return this.typeName + "," + this.taxCoefficient;
    }


}