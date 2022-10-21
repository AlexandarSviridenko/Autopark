package com.Autopark.entity;

import com.Autopark.infrastructure.orm.annotations.Column;
import com.Autopark.infrastructure.orm.annotations.ID;
import com.Autopark.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "types")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Types {
    @ID
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "coefTaxes")
    private Double coefTaxes;
}