package com.headstrait.lookup.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WaterPortabilityModel {
    @Id
    @GeneratedValue
    private Integer id;

    private Float ph;
    private Float hardness;
    private Float solids;
    private Float chloramines;
    private Float conductivity;
    private Float organic_carbon;
    private Float trihalomethanes;
    private Float turbidity;
    private Float potability;

}
