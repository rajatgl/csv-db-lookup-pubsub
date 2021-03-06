package com.headstrait.lookuptest.constants;

import com.headstrait.lookup.entity.WaterPortabilityModel;

import java.util.List;

public class TestObjects {
    public static final WaterPortabilityModel WATER_PORTABILITY_MODEL =
            new WaterPortabilityModel(1, 1.0f, 1.0f, 1.0f,
                    1.0f, 1.0f, 1.0f,
                    1.0f, 1.0f, 1.0f);
    public static final List<WaterPortabilityModel> WATER_PORTABILITY_MODELS =
            List.of(WATER_PORTABILITY_MODEL);
}
