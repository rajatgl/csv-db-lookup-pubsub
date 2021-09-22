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
}
