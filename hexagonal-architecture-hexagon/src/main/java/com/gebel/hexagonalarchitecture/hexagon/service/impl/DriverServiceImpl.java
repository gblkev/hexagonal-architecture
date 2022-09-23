package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.DriverRepositoryPort;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.DriverService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

	private final DriverRepositoryPort driverRepositoryPort;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Driver> findById(String id) {
		return driverRepositoryPort.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Driver> getAllDrivers() {
		return driverRepositoryPort.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countDrivers() {
		return driverRepositoryPort.count();
	}

	@Override
	@Transactional
	public Driver createDriver(String firstName, String lastName) throws BusinessException {
		Driver driver = Driver.builder()
			.firstName(firstName)
			.lastName(lastName)
			.build();
		checkDriverCreationPrerequisites(driver);
		return createDriverInDb(driver);
	}
	
	private void checkDriverCreationPrerequisites(Driver driver) throws BusinessException {
		driver.cleanAndValidate();
	}
	
	private Driver createDriverInDb(Driver driver) {
		return driverRepositoryPort.save(driver);
	}

	@Override
	@Transactional
	public void deleteDriver(String driverId) {
		driverRepositoryPort.deleteById(driverId);
	}

}