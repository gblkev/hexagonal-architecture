package com.gebel.hexagonalarchitecture.inbound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gebel.hexagonalarchitecture.inbound._test.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GlogalErrorHandlingIT extends AbstractIntegrationTest {
	
	@Test
	void givenInvalidUrl_whenCallingUrl_thenGenericNotFoundErrorMessage() throws Exception {
		// Given
		String invalidUrl = "http://localhost:%d/doesnotexist";
		String managementPortUrl = String.format(invalidUrl, getServerPort());
		
		// When
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(managementPortUrl, String.class);
		
		// Then
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
		ObjectMapper mapper = new ObjectMapper();
		String expectedJson = "{\"message\":\"Page not found\"}";
		assertEquals(mapper.readTree(expectedJson), mapper.readTree(response.getBody()));
	}

}