package com.gebel.hexagonalarchitecture.hexagon.service.interfaces;

import java.util.Optional;

import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.hexagon.port.inbound.DriverServicePort;

public interface DriverService extends DriverServicePort {
	
	Optional<Driver> findById(String driverId);
	
	long countDrivers();

}