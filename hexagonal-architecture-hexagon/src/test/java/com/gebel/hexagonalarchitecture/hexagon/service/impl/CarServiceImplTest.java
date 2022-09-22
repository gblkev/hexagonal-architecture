package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.gebel.hexagonalarchitecture.hexagon.domain.Car;
import com.gebel.hexagonalarchitecture.hexagon.domain.Color;
import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.CarRepositoryPort;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.ColorService;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.DriverService;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
	
	@Mock
	private CarRepositoryPort carRepositoryPort;
	
	@Mock
	private ColorService colorService;
	
	@Mock
	private DriverService driverService;
	
	private CarServiceImpl carService;
	
	
	@BeforeEach
	void setup() {
		carService = new CarServiceImpl(carRepositoryPort, colorService, driverService);
	}
	
	@Test
	void givenSeveralCars_whenGetAllCars_thenAllCarsRetrieved() {
		// Given
		Color repositoryColor1 = new Color("test_color_id1", "#000000");
		Driver repositoryDriver1 = new Driver("test_driver_id1", "Forrest", "Gump");
		Car repositoryCar1 = new Car("test_car_id1", repositoryColor1, repositoryDriver1);
		
		Color repositoryColor2 = new Color("test_color_id2", "#000001");
		Driver repositoryDriver2 = new Driver("test_driver_id2", "Tom", "Hanks");
		Car repositoryCar2 = new Car("test_car_id2", repositoryColor2, repositoryDriver2);
		
		List<Car> repositoryCars = List.of(repositoryCar1, repositoryCar2);
		when(carRepositoryPort.findAll())
			.thenReturn(repositoryCars);

		// When
		List<Car> foundCars = carService.getAllCars();
		
		// Then
		assertEquals(2, foundCars.size());
		
		Car foundCar1 = foundCars.get(0);
		assertEquals("test_car_id1", foundCar1.getId());
		Color foundColor1 = foundCar1.getColor();
		assertEquals("test_color_id1", foundColor1.getId());
		assertEquals("#000000", foundColor1.getHexaCode());
		Driver foundDriver1 = foundCar1.getDriver();
		assertEquals("test_driver_id1", foundDriver1.getId());
		assertEquals("Forrest", foundDriver1.getFirstName());
		assertEquals("Gump", foundDriver1.getLastName());
		
		Car foundCar2 = foundCars.get(1);
		assertEquals("test_car_id2", foundCar2.getId());
		Color foundColor2 = foundCar2.getColor();
		assertEquals("test_color_id2", foundColor2.getId());
		assertEquals("#000001", foundColor2.getHexaCode());
		Driver foundDriver2 = foundCar2.getDriver();
		assertEquals("test_driver_id2", foundDriver2.getId());
		assertEquals("Tom", foundDriver2.getFirstName());
		assertEquals("Hanks", foundDriver2.getLastName());
	}
	
	@Test
	void givenSeveralCars_whenCountCars_thenRightCount() {
		// Given
		when(carRepositoryPort.count())
			.thenReturn(128L);
		
		// When
		long count = carService.countCars();
		
		// Then
		assertEquals(128, count);
	}
	
	@Test
	void givenValidCar_whenCreateCar_thenCarCreated() throws BusinessException {
		// Given
		Color createdRepositoryCarColor = new Color("test_color_id", "#000000");
		Driver createdRepositoryCarDriver = new Driver("test_driver_id", "Forrest", "Gump");
		Car createdRepositoryCar = new Car("test_car_id", createdRepositoryCarColor, createdRepositoryCarDriver);
		
		ArgumentCaptor<Car> repositoryCarArgumentCaptor = ArgumentCaptor.forClass(Car.class);
		when(carRepositoryPort.save(repositoryCarArgumentCaptor.capture()))
			.thenReturn(createdRepositoryCar);
		
		Color getColorByIdMock = new Color("test_color_id", "#000000");
		when(colorService.findById("test_color_id"))
			.thenReturn(Optional.of(getColorByIdMock));
		
		Driver getDriverByIdMock = new Driver("test_driver_id", "Forrest", "Gump");
		when(driverService.findById("test_driver_id"))
			.thenReturn(Optional.of(getDriverByIdMock));
		
		String colorIdOfCarToCreate = "test_color_id";
		String driverIdOfCarToCreate = "test_driver_id";
		
		// When
		Car createdCar = carService.createCar(colorIdOfCarToCreate, driverIdOfCarToCreate);
		
		// Then
		Car repositoryCarPassedToPort = repositoryCarArgumentCaptor.getValue();
		assertNull(repositoryCarPassedToPort.getId());
		Color repositoryColorPassedToPort = repositoryCarPassedToPort.getColor();
		assertEquals("test_color_id", repositoryColorPassedToPort.getId());
		assertEquals("#000000", repositoryColorPassedToPort.getHexaCode());
		Driver repositoryDriverPassedToPort = repositoryCarPassedToPort.getDriver();
		assertEquals("test_driver_id", repositoryDriverPassedToPort.getId());
		assertEquals("Forrest", repositoryDriverPassedToPort.getFirstName());
		assertEquals("Gump", repositoryDriverPassedToPort.getLastName());
		
		assertEquals("test_car_id", createdCar.getId());
		Color createdCarColor = createdCar.getColor();
		assertEquals("test_color_id", createdCarColor.getId());
		assertEquals("#000000", createdCarColor.getHexaCode());
		Driver createdCarDriver = createdCar.getDriver();
		assertEquals("test_driver_id", createdCarDriver.getId());
		assertEquals("Forrest", createdCarDriver.getFirstName());
		assertEquals("Gump", createdCarDriver.getLastName());
	}
	
	@Test
	void givenInvalidColor_whenCreateCar_thenThrowBusinessException() throws BusinessException {
		// Given
		String invalidColorId = "test_color_id";
		String validDriverId = "test_driver_id";
		
		when(colorService.findById(invalidColorId))
			.thenReturn(Optional.empty());
		
		Driver getDriverByIdMock = new Driver(validDriverId, "Forrest", "Gump");
		when(driverService.findById(validDriverId))
			.thenReturn(Optional.of(getDriverByIdMock));
		
		BusinessException businessException = assertThrows(BusinessException.class, () -> {
			// When
			carService.createCar(invalidColorId, validDriverId);
		});

		// Then
		verifyNoInteractions(carRepositoryPort);
		assertEquals(BusinessErrorCode.CAR_INVALID_COLOR, businessException.getBusinessError());
	}
	
	@Test
	void givenInvalidDriver_whenCreateCar_thenThrowBusinessException() throws BusinessException {
		// Given
		String validColorId = "test_color_id";
		String invalidDriverId = "test_driver_id";
		
		Color getColorByIdMock = new Color(validColorId, "#000000");
		when(colorService.findById(validColorId))
			.thenReturn(Optional.of(getColorByIdMock));
		
		when(driverService.findById(invalidDriverId))
			.thenReturn(Optional.empty());
		
		BusinessException businessException = assertThrows(BusinessException.class, () -> {
			// When
			carService.createCar(validColorId, invalidDriverId);
		});

		// Then
		verifyNoInteractions(carRepositoryPort);
		assertEquals(BusinessErrorCode.CAR_INVALID_DRIVER, businessException.getBusinessError());
	}
	
	@Test
	void givenExistingCar_whenDeleteCar_thenCarDeleted() {
		// Given
		String carId = "car_id";
		
		// When
		carService.deleteCar(carId);
		
		// Then
		verify(carRepositoryPort, times(1))
			.deleteById("car_id");
	}
	
}