package com.headstrait.waterportabiliitydataproducer.model;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaterPortabilityEvent {

    private UUID id;
    @NonNull
    private WaterPortability waterPortability;
}
