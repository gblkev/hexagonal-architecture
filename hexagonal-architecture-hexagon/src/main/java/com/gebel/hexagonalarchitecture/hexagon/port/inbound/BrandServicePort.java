package com.gebel.hexagonalarchitecture.hexagon.port.inbound;

import java.util.List;

import com.gebel.hexagonalarchitecture.hexagon.domain.Brand;

public interface BrandServicePort {
	
	List<Brand> getAllBrands();
	
	long countBrands();

}