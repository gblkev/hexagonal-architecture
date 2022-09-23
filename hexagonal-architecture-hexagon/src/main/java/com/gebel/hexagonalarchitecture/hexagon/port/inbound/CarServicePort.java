package com.gebel.hexagonalarchitecture.hexagon.port.inbound;

import java.util.List;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Car;

public interface CarServicePort {
	
	List<Car> getAllCars();
	
	Car createCar(String colorId, String driverId) throws BusinessException;
	
	void deleteCar(String carId);

}