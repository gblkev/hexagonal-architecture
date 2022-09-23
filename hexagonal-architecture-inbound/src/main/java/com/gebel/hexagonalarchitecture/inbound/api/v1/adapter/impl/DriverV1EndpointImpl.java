package com.gebel.hexagonalarchitecture.inbound.api.v1.adapter.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.hexagon.port.inbound.DriverServicePort;
import com.gebel.hexagonalarchitecture.inbound.api.v1.adapter.converter.V1ApiDriverConverter;
import com.gebel.hexagonalarchitecture.inbound.api.v1.adapter.interfaces.DriverV1Endpoint;
import com.gebel.hexagonalarchitecture.inbound.api.v1.dto.CreateDriverDto;
import com.gebel.hexagonalarchitecture.inbound.api.v1.dto.DriverDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class DriverV1EndpointImpl implements DriverV1Endpoint {

	private final DriverServicePort driverServicePort;
	private final V1ApiDriverConverter driverConverter;
	
	@Override
	public List<DriverDto> getAllDrivers() {
		LOGGER.info("Listing all drivers");
		return driverConverter.toDto(driverServicePort.getAllDrivers());
	}

	@Override
	public DriverDto createDriver(CreateDriverDto createDriverDto) throws BusinessException {
		LOGGER.info("Creating driver with data={}", createDriverDto);
		String firstName = (createDriverDto != null ? createDriverDto.getFirstName() : null);
		String lastName = (createDriverDto != null ? createDriverDto.getLastName() : null);
		Driver createdDriver = driverServicePort.createDriver(firstName, lastName);
		return driverConverter.toDto(createdDriver);
	}
	
	@Override
	public void deleteDriver(String driverId) {
		LOGGER.info("Deleting driver with id={}", driverId);
		driverServicePort.deleteDriver(driverId);
	}

}