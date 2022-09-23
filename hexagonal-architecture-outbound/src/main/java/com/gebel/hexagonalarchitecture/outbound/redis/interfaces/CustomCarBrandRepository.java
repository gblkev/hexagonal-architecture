package com.gebel.hexagonalarchitecture.outbound.redis.interfaces;

import com.gebel.hexagonalarchitecture.outbound.redis.model.CarBrandModel;

public interface CustomCarBrandRepository {
	
	Iterable<CarBrandModel> findAllWithoutSpringBug();
	
	long countWithoutSpringBug();
	
}