package com.gebel.hexagonalarchitecture.hexagon.service.interfaces;

import com.gebel.hexagonalarchitecture.hexagon.port.inbound.CarServicePort;

public interface CarService extends CarServicePort {
	
	long countCars();
	
}