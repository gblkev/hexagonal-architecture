package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessErrorCode;
import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.DriverRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {
	
	@Mock
	private DriverRepositoryPort driverRepositoryPort;
	
	private DriverServiceImpl driverService;
	
	@BeforeEach
	void setup() {
		driverService = new DriverServiceImpl(driverRepositoryPort);
	}
	
	@Test
	void givenExistingDriver_whenGetById_thenDriverRetrieved() {
		// Given
		String driverId = "test_id1";
		Driver repositoryDriver = Driver.builder()
			.id(driverId)
			.firstName("Forrest")
			.lastName("Gump")
			.build();
		when(driverRepositoryPort.findById(driverId))
			.thenReturn(Optional.of(repositoryDriver));
		
		// When
		Optional<Driver> optionalDriver = driverService.findById(driverId);
		
		// Then
		assertTrue(optionalDriver.isPresent());
		
		Driver driver = optionalDriver.get();
		assertEquals("test_id1", driver.getId());
		assertEquals("Forrest", driver.getFirstName());
		assertEquals("Gump", driver.getLastName());
	}
	
	@Test
	void givenNonExistingDriver_whenGetById_thenEmptyResult() {
		// Given
		String driverId = "test_id1";
		when(driverRepositoryPort.findById(driverId))
			.thenReturn(Optional.empty());
		
		// When
		Optional<Driver> optionalDriver = driverService.findById(driverId);
		
		// Then
		assertTrue(optionalDriver.isEmpty());
	}

	@Test
	void givenSeveralDrivers_whenGetAllDrivers_thenAllDriversRetrieved() {
		// Given
		Driver repositoryDriver1 = Driver.builder()
			.id("test_id1")
			.firstName("Forrest")
			.lastName("Gump")
			.build();
		Driver repositoryDriver2 = Driver.builder()
			.id("test_id2")
			.firstName("Tom")
			.lastName("Hanks")
			.build();
		List<Driver> repositoryDrivers = List.of(repositoryDriver1, repositoryDriver2);
		when(driverRepositoryPort.findAll())
			.thenReturn(repositoryDrivers);
		
		// When
		List<Driver> drivers = driverService.getAllDrivers();
		
		// Then
		assertEquals(2, drivers.size());
		
		Driver driver1 = drivers.get(0);
		assertEquals("test_id1", driver1.getId());
		assertEquals("Forrest", driver1.getFirstName());
		assertEquals("Gump", driver1.getLastName());
		
		Driver driver2 = drivers.get(1);
		assertEquals("test_id2", driver2.getId());
		assertEquals("Tom", driver2.getFirstName());
		assertEquals("Hanks", driver2.getLastName());
	}
	
	@Test
	void givenSeveralDrivers_whenCountDrivers_thenRightCount() {
		// Given
		when(driverRepositoryPort.count())
			.thenReturn(13L);
		
		// When
		long count = driverService.countDrivers();
		
		// Then
		assertEquals(13, count);
	}
	
	@Test
	void givenValidDriver_whenCreateDriver_thenDriverCreated() throws BusinessException {
		// Given
		Driver createdRepositoryDriver = Driver.builder()
			.id("test_id")
			.firstName("Forrest")
			.lastName("Gump")
			.build();
		ArgumentCaptor<Driver> repositoryDriverArgumentCaptor = ArgumentCaptor.forClass(Driver.class);
		when(driverRepositoryPort.save(repositoryDriverArgumentCaptor.capture()))
			.thenReturn(createdRepositoryDriver);
		
		String firstNameOfDriverToCreate = "Forrest";
		String lastNameOfDriverToCreate = "Gump";
		
		// When
		Driver createdDriver = driverService.createDriver(firstNameOfDriverToCreate, lastNameOfDriverToCreate);
		
		// Then
		assertNull(repositoryDriverArgumentCaptor.getValue().getId());
		assertEquals("Forrest", repositoryDriverArgumentCaptor.getValue().getFirstName());
		assertEquals("Gump", repositoryDriverArgumentCaptor.getValue().getLastName());
		
		assertEquals("test_id", createdDriver.getId());
		assertEquals("Forrest", createdDriver.getFirstName());
		assertEquals("Gump", createdDriver.getLastName());
	}
	
	@Test
	void givenInvalidFirstName_whenCreateDriver_thenThrowBusinessException() throws BusinessException {
		// Given
		String firstNameOfDriverToCreate = null;
		String lastNameOfDriverToCreate = "Gump";
		
		BusinessException businessException = assertThrows(BusinessException.class, () -> {
			// When
			driverService.createDriver(firstNameOfDriverToCreate, lastNameOfDriverToCreate);
		});

		// Then
		verifyNoInteractions(driverRepositoryPort);
		assertEquals(BusinessErrorCode.DRIVER_INVALID_FIRST_NAME, businessException.getBusinessError());
	}
	
	@Test
	void givenInvalidLastName_whenCreateDriver_thenThrowBusinessException() throws BusinessException {
		// Given
		String firstNameOfDriverToCreate = "Forrest";
		String lastNameOfDriverToCreate = null;
		
		BusinessException businessException = assertThrows(BusinessException.class, () -> {
			// When
			driverService.createDriver(firstNameOfDriverToCreate, lastNameOfDriverToCreate);
		});

		// Then
		verifyNoInteractions(driverRepositoryPort);
		assertEquals(BusinessErrorCode.DRIVER_INVALID_LAST_NAME, businessException.getBusinessError());
	}
	
	@Test
	void givenExistingDriver_whenDeleteDriver_thenDriverDeleted() {
		// Given
		String driverId = "driver_id";
		
		// When
		driverService.deleteDriver(driverId);
		
		// Then
		verify(driverRepositoryPort, times(1))
			.deleteById("driver_id");
	}
	
}