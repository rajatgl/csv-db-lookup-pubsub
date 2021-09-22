package com.headstrait.dataconsumer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WaterPortability implements Serializable {
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

    public WaterPortability(Integer id, String ph, String hardness, String solids,
                            String chloramines, String conductivity, String organic_carbon,
                            String trihalomethanes, String turbidity, String potability){

        this.id = id;
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
            this.trihalomethanes = Float.parseFloat(trihalomethanes);
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
