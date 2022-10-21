package com.Autopark.entity;

import com.Autopark.infrastructure.orm.annotations.Column;
import com.Autopark.infrastructure.orm.annotations.ID;
import com.Autopark.infrastructure.orm.annotations.Table;
import lombok.*;

import java.util.Date;

@Table(name = "rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Rents {
    @ID(name = "carId")
    private Long carId;
    @Column(name = "date")
    private Date date;
    @Column(name = "cost")
    private Double cost;
}
