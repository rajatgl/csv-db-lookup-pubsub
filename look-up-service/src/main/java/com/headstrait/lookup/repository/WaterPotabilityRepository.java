package com.headstrait.lookup.repository;

import com.headstrait.lookup.entity.WaterPortabilityModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterPotabilityRepository extends CrudRepository<WaterPortabilityModel,Integer> {
    List<WaterPortabilityModel> findAll();
    WaterPortabilityModel findById(int id);
}
