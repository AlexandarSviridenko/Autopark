package com.Autopark.entity;

import com.Autopark.infrastructure.orm.annotations.Column;
import com.Autopark.infrastructure.orm.annotations.ID;
import com.Autopark.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Vehicles {
    @ID
    private Long id;
    @Column(name = "typesId", nullable = false)
    private Long typesId;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "registrationNumber", unique = true, nullable = false)
    private String registrationNumber;
    @Column(name = "weight", nullable = false)
    private Integer weight;
    @Column(name = "manufactureYear")
    private Integer manufactureYear;
    @Column(name = "mileage")
    private Integer mileage;
    @Column(name = "color")
    private String color;
    @Column(name = "engineType")
    private String engineType;
}