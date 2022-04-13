package com.headstrait.dataconsumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WaterPortabilityModel implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private Double ph;
    private Double hardness;
    private Double solids;
    private Double chloramines;
    private Double conductivity;
    private Double organic_carbon;
    private Double trihalomethanes;
    private Double turbidity;
    private Double potability;

    public WaterPortabilityModel(Double ph, Double hardness, Double solids,
                                 Double chloramines, Double conductivity, Double organic_carbon,
                                 Double trihalomethanes, Double turbidity, Double potability) {
        this.ph = ph;
        this.hardness = hardness;
        this.solids = solids;
        this.chloramines = chloramines;
        this.conductivity = conductivity;
        this.organic_carbon = organic_carbon;
        this.trihalomethanes = trihalomethanes;
        this.turbidity = turbidity;
        this.potability = potability;
    }
}
