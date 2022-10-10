package com.Autopark.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

    @Getter
    @Builder
    @ToString
    public class VehicleDto {
        private long id;
        private String typeName;
        private double taxCoefficient;
        private String modelName;
        private String registrationNumber;
        private int weight;
        private int manufactureYear;
        private int mileage;
        private String color;
        private String engineName;
        private double tankVolume;
        private double engineTaxCoefficient;
        private double per100km;
        private double maxKm;
        private double tax;
        private double income;
    }

