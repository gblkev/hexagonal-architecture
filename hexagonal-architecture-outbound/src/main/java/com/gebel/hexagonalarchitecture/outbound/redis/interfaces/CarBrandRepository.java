package com.gebel.hexagonalarchitecture.outbound.redis.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.gebel.hexagonalarchitecture.outbound.redis.model.CarBrandModel;

public interface CarBrandRepository extends CrudRepository<CarBrandModel, String> {
	
}