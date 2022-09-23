package com.gebel.hexagonalarchitecture.hexagon.port.outbound;

import java.util.List;
import java.util.Optional;

import com.gebel.hexagonalarchitecture.hexagon.domain.Color;

public interface ColorRepositoryPort {

	Optional<Color> findById(String colorId);
	
	Optional<Color> findOneByHexaCodeIgnoreCase(String hexaCode);
	
	List<Color> findAll();
	
	long count();
	
	Color save(Color color);
	
	void deleteById(String colorId);

}