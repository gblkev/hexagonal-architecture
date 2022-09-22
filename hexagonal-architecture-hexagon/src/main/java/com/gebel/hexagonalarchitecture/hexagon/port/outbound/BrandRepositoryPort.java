package com.gebel.hexagonalarchitecture.hexagon.port.outbound;

import java.util.List;

import com.gebel.hexagonalarchitecture.hexagon.domain.Brand;

public interface BrandRepositoryPort {

	List<Brand> findAll();
	
	long count();
	
}