package com.gebel.hexagonalarchitecture.hexagon.port.inbound;

import java.util.List;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Color;

public interface ColorServicePort {
	
	List<Color> getAllColors();
	
	Color createColor(String hexaCode) throws BusinessException;
	
	void deleteColor(String colorId);

}