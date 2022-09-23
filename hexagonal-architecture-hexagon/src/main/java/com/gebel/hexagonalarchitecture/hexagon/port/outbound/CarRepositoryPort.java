package com.gebel.hexagonalarchitecture.hexagon.port.outbound;

import java.util.List;

import com.gebel.hexagonalarchitecture.hexagon.domain.Car;

public interface CarRepositoryPort {
	
	List<Car> findAll();
	
	long count();
	
	Car save(Car car);
	
	void deleteById(String carId);

}