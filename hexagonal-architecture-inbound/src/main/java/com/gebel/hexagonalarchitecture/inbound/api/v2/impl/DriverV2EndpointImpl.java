package com.gebel.hexagonalarchitecture.inbound.api.v2.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.hexagon.port.inbound.DriverServicePort;
import com.gebel.hexagonalarchitecture.inbound.api.v2.converter.V2ApiDriverConverter;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.CreateDriverDto;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.DriverDto;
import com.gebel.hexagonalarchitecture.inbound.api.v2.interfaces.DriverV2Endpoint;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class DriverV2EndpointImpl implements DriverV2Endpoint {

	private final DriverServicePort driverService;
	private final V2ApiDriverConverter driverConverter;
	
	@Override
	public List<DriverDto> getAllDrivers() {
		LOGGER.info("Listing all drivers");
		return driverConverter.toDto(driverService.getAllDrivers());
	}

	@Override
	public DriverDto createDriver(CreateDriverDto createDriverDto) throws BusinessException {
		LOGGER.info("Creating driver with data={}", createDriverDto);
		String firstName = (createDriverDto != null ? createDriverDto.getFirstName() : null);
		String lastName = (createDriverDto != null ? createDriverDto.getLastName() : null);
		Driver createdDriver = driverService.createDriver(firstName, lastName);
		return driverConverter.toDto(createdDriver);
	}
	
	@Override
	public void deleteDriver(String driverId) {
		LOGGER.info("Deleting driver with id={}", driverId);
		driverService.deleteDriver(driverId);
	}

}