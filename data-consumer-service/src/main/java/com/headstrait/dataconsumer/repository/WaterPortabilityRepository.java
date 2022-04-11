package com.headstrait.dataconsumer.repository;

import com.headstrait.dataconsumer.entity.WaterPortabilityModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterPortabilityRepository extends CrudRepository<WaterPortabilityModel,Integer> {
}
