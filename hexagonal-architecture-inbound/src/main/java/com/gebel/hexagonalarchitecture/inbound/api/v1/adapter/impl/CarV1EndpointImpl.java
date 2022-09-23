package com.gebel.hexagonalarchitecture.inbound.api.v1.adapter.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Car;
import com.gebel.hexagonalarchitecture.hexagon.port.inbound.CarServicePort;
import com.gebel.hexagonalarchitecture.inbound.api.v1.adapter.converter.V1ApiCarConverter;
import com.gebel.hexagonalarchitecture.inbound.api.v1.adapter.interfaces.CarV1Endpoint;
import com.gebel.hexagonalarchitecture.inbound.api.v1.dto.CarDto;
import com.gebel.hexagonalarchitecture.inbound.api.v1.dto.CreateCarDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class CarV1EndpointImpl implements CarV1Endpoint {

	private final CarServicePort carServicePort;
	private final V1ApiCarConverter carConverter;
	
	@Override
	public List<CarDto> getAllCars() {
		LOGGER.info("Listing all cars");
		return carConverter.toDto(carServicePort.getAllCars());
	}

	@Override
	public CarDto createCar(CreateCarDto createCarDto) throws BusinessException {
		LOGGER.info("Creating car with data={}", createCarDto);
		String colorId = (createCarDto != null ? createCarDto.getColorId() : null);
		String driverId = (createCarDto != null ? createCarDto.getDriverId() : null);
		Car createdCar = carServicePort.createCar(colorId, driverId);
		return carConverter.toDto(createdCar);
	}

	@Override
	public void deleteCar(String carId) {
		LOGGER.info("Deleting car with id={}", carId);
		carServicePort.deleteCar(carId);
	}

}