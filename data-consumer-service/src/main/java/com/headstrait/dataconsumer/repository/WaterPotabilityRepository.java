package com.headstrait.dataconsumer.repository;

import com.headstrait.dataconsumer.entity.WaterPortability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterPotabilityRepository extends CrudRepository<WaterPortability,Integer> {

    List<WaterPortability> findAll();

}
