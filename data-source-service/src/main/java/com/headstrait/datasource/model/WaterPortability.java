package com.headstrait.datasource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterPortability {
    private Float ph;
    private Float hardness;
    private Float solids;
    private Float chloramin;
    private Float conductivity;
    private Float organic_carbon;
    private Float trihalomethanes;
    private Float turbidity;
    private Float Potability;
}
