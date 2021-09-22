package com.headstrait.dataconsumer.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headstrait.dataconsumer.entity.WaterPortabilityModel;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class CustomDeserializer implements Deserializer<WaterPortabilityModel> {

    ObjectMapper objectMapper;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public WaterPortabilityModel deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data,WaterPortabilityModel.class);
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

//    @Override
//    public WaterPortabilityModel deserialize(JsonParser parser,
//                                             DeserializationContext context) throws IOException {
//        ObjectCodec oc = parser.getCodec();
//        JsonNode node = oc.readTree(parser);
//
//        final Double ph = node.get("ph").asDouble();
//        final Double hardness = node.get("hardness").asDouble();
//        final Double solids = node.get("solids").asDouble();
//        final Double chloramines = node.get("chloramines").asDouble();
//        final Double conductivity = node.get("conductivity").asDouble();
//        final Double organic_carbon = node.get("organic_carbon").asDouble();
//        final Double trihalomethanes = node.get("trihalomethanes").asDouble();
//        final Double turbidity = node.get("turbidity").asDouble();
//        final Double potability = node.get("potability").asDouble();
//
//        return new WaterPortabilityModel(ph,hardness,solids,chloramines,conductivity,organic_carbon,trihalomethanes,turbidity,potability);
//    }


}
