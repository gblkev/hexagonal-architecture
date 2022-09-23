package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessErrorCode;
import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Car;
import com.gebel.hexagonalarchitecture.hexagon.domain.Color;
import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.CarRepositoryPort;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.CarService;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.ColorService;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.DriverService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

	private final CarRepositoryPort carRepositoryPort;
	private final ColorService colorService;
	private final DriverService driverService;
	
	@Override
	@Transactional(readOnly = true)
	public List<Car> getAllCars() {
		return carRepositoryPort.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countCars() {
		return carRepositoryPort.count();
	}

	@Override
	@Transactional
	public Car createCar(String colorId, String driverId) throws BusinessException {
		Optional<Color> optionalColor = colorService.findById(colorId);
		Optional<Driver> optionalDriver = driverService.findById(driverId);
		Car car = Car.builder()
			.color(optionalColor.orElse(null))
			.driver(optionalDriver.orElse(null))
			.build();
		checkCarCreationPrerequisites(car);
		return carRepositoryPort.save(car);
	}
	
	private void checkCarCreationPrerequisites(Car car) throws BusinessException {
		if (car.getColor() == null) {
			throw new BusinessException(BusinessErrorCode.CAR_INVALID_COLOR);
		}
		if (car.getDriver() == null) {
			throw new BusinessException(BusinessErrorCode.CAR_INVALID_DRIVER);
		}
	}
	
	@Override
	@Transactional
	public void deleteCar(String carId) {
		carRepositoryPort.deleteById(carId);
	}
	
}