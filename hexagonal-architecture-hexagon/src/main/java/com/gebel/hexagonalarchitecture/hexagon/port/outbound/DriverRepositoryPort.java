package com.gebel.hexagonalarchitecture.hexagon.port.outbound;

import java.util.List;
import java.util.Optional;

import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;

public interface DriverRepositoryPort {
	
	Optional<Driver> findById(String driverId);
	
	List<Driver> findAll();
	
	long count();
	
	Driver save(Driver driver);
	
	void deleteById(String driverId);

}