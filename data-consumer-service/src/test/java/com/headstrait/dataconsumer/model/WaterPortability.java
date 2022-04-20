package com.headstrait.dataconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaterPortability implements Serializable {
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
