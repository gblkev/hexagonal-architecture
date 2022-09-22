package com.gebel.hexagonalarchitecture.inbound.api.v2.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Car;
import com.gebel.hexagonalarchitecture.hexagon.port.inbound.CarServicePort;
import com.gebel.hexagonalarchitecture.inbound.api.v2.converter.V2ApiCarConverter;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.CarDto;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.CreateCarDto;
import com.gebel.hexagonalarchitecture.inbound.api.v2.interfaces.CarV2Endpoint;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class CarV2EndpointImpl implements CarV2Endpoint {

	private final CarServicePort carService;
	private final V2ApiCarConverter carConverter;
	
	@Override
	public List<CarDto> getAllCars() {
		LOGGER.info("Listing all cars");
		return carConverter.toDto(carService.getAllCars());
	}

	@Override
	public CarDto createCar(CreateCarDto createCarDto) throws BusinessException {
		LOGGER.info("Creating car with data={}", createCarDto);
		String colorId = (createCarDto != null ? createCarDto.getColorId() : null);
		String driverId = (createCarDto != null ? createCarDto.getDriverId() : null);
		Car createdCar = carService.createCar(colorId, driverId);
		return carConverter.toDto(createdCar);
	}

	@Override
	public void deleteCar(String carId) {
		LOGGER.info("Deleting car with id={}", carId);
		carService.deleteCar(carId);
	}

}