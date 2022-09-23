package com.gebel.hexagonalarchitecture.hexagon.service.interfaces;

import java.util.Optional;

import com.gebel.hexagonalarchitecture.hexagon.domain.Color;
import com.gebel.hexagonalarchitecture.hexagon.port.inbound.ColorServicePort;

public interface ColorService extends ColorServicePort {
	
	Optional<Color> findById(String colorId);
	
	long countColors();
	
}