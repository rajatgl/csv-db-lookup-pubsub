package com.headstrait.waterportabilitydataproducer.constants;

import com.headstrait.waterportabiliitydataproducer.model.WaterPortability;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestObjects {

    public static final WaterPortability WATER_PORTABILITY_EVENT =
            new WaterPortability("0.0","0.0","0.0",
                    "0.0","0.0","0.0",
                    "0.0","0.0","0.0");

    public static final WaterPortability WATER_PORTABILITY_EVENT2 =
            new WaterPortability("1.0","4.0","3.1",
                    "4.5","5.0","2.0",
                    "1.0","2.0","3.0");

    public static final Map<String,String> DATA = Map.of(
            "ph","0.0",
            "Hardness","0.0",
            "Solids","0.0",
            "Chloramines","0.0",
            "Conductivity","0.0",
            "Organic_carbon","0.0",
            "Trihalomethanes","0.0",
            "Turbidity","0.0",
            "Potability","0.0");

    public static final List<WaterPortability> WATER_PORTABILITY_SINGLETON =
            Collections.singletonList(WATER_PORTABILITY_EVENT);

    public static final List<WaterPortability> WATER_PORTABILITY_EVENTS =
            List.of(WATER_PORTABILITY_EVENT,
            WATER_PORTABILITY_EVENT2);

}
