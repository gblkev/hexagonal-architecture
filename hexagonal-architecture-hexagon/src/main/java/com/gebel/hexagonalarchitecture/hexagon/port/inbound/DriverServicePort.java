package com.gebel.hexagonalarchitecture.hexagon.port.inbound;

import java.util.List;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;

public interface DriverServicePort {
	
	List<Driver> getAllDrivers();
	
	Driver createDriver(String firstName, String lastName) throws BusinessException;
	
	void deleteDriver(String driverId);
	
}