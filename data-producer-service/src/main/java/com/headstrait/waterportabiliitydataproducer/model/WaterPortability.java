package com.headstrait.waterportabiliitydataproducer.model;

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

    public WaterPortability(String ph, String hardness, String solids,
                     String chloramines, String conductivity, String organic_carbon,
                     String trihalomenthanes, String turbidity, String potability){
        try{
            this.ph = Float.parseFloat(ph);
        }catch (Exception e){
            this.ph = null;
        }
        try{
            this.hardness = Float.parseFloat(hardness);
        }catch (Exception e){
            this.hardness = null;
        }
        try{
            this.solids = Float.parseFloat(solids);
        }catch (Exception e){
            this.solids = null;
        }
        try{
            this.chloramines = Float.parseFloat(chloramines);
        }catch (Exception e){
            this.chloramines = null;
        }
        try{
            this.conductivity = Float.parseFloat(conductivity);
        }catch (Exception e){
            this.conductivity = null;
        }
        try{
            this.organic_carbon = Float.parseFloat(organic_carbon);
        }catch (Exception e){
            this.organic_carbon = null;
        }
        try{
            this.trihalomethanes = Float.parseFloat(trihalomenthanes);
        }catch (Exception e){
            this.trihalomethanes = null;
        }
        try{
            this.turbidity = Float.parseFloat(turbidity);
        }catch (Exception e){
            this.turbidity = null;
        }
        try{
            this.potability = Float.parseFloat(potability);
        }catch (Exception e){
            this.potability = null;
        }

    }
}
