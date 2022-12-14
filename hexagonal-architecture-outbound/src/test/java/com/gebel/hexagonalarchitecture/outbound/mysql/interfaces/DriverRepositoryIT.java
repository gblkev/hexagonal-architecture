package com.gebel.hexagonalarchitecture.outbound.mysql.interfaces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.gebel.hexagonalarchitecture.outbound._test.AbstractIntegrationTest;
import com.gebel.hexagonalarchitecture.outbound.mysql.entity.DriverEntity;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class DriverRepositoryIT extends AbstractIntegrationTest {

	// Ex: c2bba799-02db-4b4b-8782-0df1517bbe1d
	private static final String UUID_REGEX = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
	
	@Autowired
	private DriverRepository driverRepository;

	@Test
	void givenSeveralDrivers_whenFindById_thenOneDriverRetrieved() {
		// Given
		getTestContainers().getMysqlTestContainer().executeSqlScript("mysql/driver/findById_severalDrivers.sql");
		
		String id = "id_1";

		// When
		Optional<DriverEntity> optionalDriver = driverRepository.findById(id);

		// Then
		DriverEntity foundDriver = optionalDriver.get();
		assertEquals("id_1", foundDriver.getId());
		assertEquals("Forrest", foundDriver.getFirstName());
		assertEquals("Gump", foundDriver.getLastName());
	}

	@Test
	void givenSeveralDrivers_whenFindAll_thenAllDriversRetrieved() {
		// Given
		getTestContainers().getMysqlTestContainer().executeSqlScript("mysql/driver/findAll_severalDrivers.sql");

		// When
		List<DriverEntity> drivers = driverRepository.findAll();

		// Then
		assertEquals(3, drivers.size());

		DriverEntity driver1 = drivers.get(0);
		assertEquals("id_1", driver1.getId());
		assertEquals("Forrest", driver1.getFirstName());
		assertEquals("Gump", driver1.getLastName());

		DriverEntity driver2 = drivers.get(1);
		assertEquals("id_2", driver2.getId());
		assertEquals("Tom", driver2.getFirstName());
		assertEquals("Hanks", driver2.getLastName());

		DriverEntity driver3 = drivers.get(2);
		assertEquals("id_3", driver3.getId());
		assertEquals("Ace", driver3.getFirstName());
		assertEquals("Ventura", driver3.getLastName());
	}
	
	@Test
	void givenNoDrivers_whenSave_thenDriverCreated() {
		// Given + empty table
		DriverEntity driverToCreate = DriverEntity.builder()
			.id(null)
			.firstName("Clint")
			.lastName("Eastwood")
			.build();

		// When
		DriverEntity createdDriver = driverRepository.save(driverToCreate);

		// Then
		assertEquals(1, driverRepository.count());
		assertIdFormat(createdDriver.getId());
		assertEquals("Clint", createdDriver.getFirstName());
		assertEquals("Eastwood", createdDriver.getLastName());
	}
	
	private void assertIdFormat(String id) {
		Pattern pattern = Pattern.compile(UUID_REGEX);
		assertTrue(pattern.matcher(id).matches());
	}
	
	@Test
	void givenSeveralDrivers_whenDeleteById_thenDriverDeleted() {
		// Given
		getTestContainers().getMysqlTestContainer().executeSqlScript("mysql/driver/deleteById_severalDrivers.sql");
		
		String id = "id_1";
		assertTrue(driverRepository.findById(id).isPresent());
		assertEquals(3, driverRepository.count());

		// When
		driverRepository.deleteById(id);

		// Then
		assertFalse(driverRepository.findById(id).isPresent());
		assertEquals(2, driverRepository.count());
	}

}