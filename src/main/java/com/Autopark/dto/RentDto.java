package com.Autopark.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;


@Getter
@Builder
public class RentDto {
    private long carId;
    private Date date;
    private double cost;
}