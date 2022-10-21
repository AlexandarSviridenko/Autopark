package com.Autopark.entity;

import com.Autopark.infrastructure.orm.annotations.Column;
import com.Autopark.infrastructure.orm.annotations.ID;
import com.Autopark.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "orders")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @ID
    private Long id;
    @Column(name = "vehicleId")
    private Long vehicleId;
    @Column(name = "defect")
    private String defect;
    @Column(name = "breakingAmount")
    private Integer breakingAmount;
}
