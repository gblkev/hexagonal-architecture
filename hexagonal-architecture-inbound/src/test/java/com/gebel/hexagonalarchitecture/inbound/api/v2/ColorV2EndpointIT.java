package com.gebel.hexagonalarchitecture.inbound.api.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.gebel.hexagonalarchitecture.inbound._test.AbstractIntegrationTest;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.ColorDto;
import com.gebel.hexagonalarchitecture.inbound.api.v2.error.ApiBusinessErrorCodeDto;
import com.gebel.hexagonalarchitecture.inbound.api.v2.error.ApiBusinessErrorDto;
import com.gebel.hexagonalarchitecture.inbound.api.v2.error.ApiTechnicalErrorDto;

@SpringBootTest(
	webEnvironment = WebEnvironment.RANDOM_PORT,
	// To speed up the error occurrence when we simulate a connection issue with the database.
	properties = {
		"spring.datasource.hikari.connection-timeout=250",
		"spring.datasource.hikari.validation-timeout=250"
	}
)
class ColorV2EndpointIT extends AbstractIntegrationTest {
	
	private static final String API_URL_PATTERN = "http://localhost:%d/api/v2/colors";
	private static final String DELETE_BY_ID_API_URL_PATTERN = API_URL_PATTERN + "/{colorId}";

	private final TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Test
	void givenSeveralColors_whenGetFindAll_thenAllColorsRetrieved() {
		// Given
		getTestContainers().getMysqlTestContainer().executeSqlScript("api/v2/color/get_findAll_createSeveralColors.sql");
		String url = String.format(API_URL_PATTERN, getServerPort());
		
		// When
		ResponseEntity<ColorDto[]> response = restTemplate.getForEntity(url, ColorDto[].class);
		
		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		ColorDto[] colors = response.getBody();
		assertEquals(3, colors.length);

		ColorDto color1 = colors[0];
		assertEquals("id_1", color1.getId());
		assertEquals("#000000", color1.getHexaCode());

		ColorDto color2 = colors[1];
		assertEquals("id_2", color2.getId());
		assertEquals("#000001", color2.getHexaCode());

		ColorDto color3 = colors[2];
		assertEquals("id_3", color3.getId());
		assertEquals("#000002", color3.getHexaCode());
	}
	
	@Test
	@Transactional // To get a connection using the specific datasource configuration we defined on top of the class.
	void givenDatabaseUnavailable_whenGetFindAll_thenGenericError() {
		// Given
		String url = String.format(API_URL_PATTERN, getServerPort());
		
		// When
		ResponseEntity<ApiTechnicalErrorDto> response;
		try {
			getTestContainers().getMysqlTestContainer().pause();
			response = restTemplate.getForEntity(url, ApiTechnicalErrorDto.class);
		}
		finally {
			getTestContainers().getMysqlTestContainer().unpause();
		}
		
		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
		ApiTechnicalErrorDto apiTechnicalErrorDto = response.getBody();
		assertEquals("An unexpected error occured", apiTechnicalErrorDto.getMessage());
	}
	
	@Test
	void givenValidColor_whenPostCreate_thenColorCreated() {
		// Given
		String url = String.format(API_URL_PATTERN, getServerPort());
		
		String hexaCodeToCreate = "#ABCDEF";
		HttpEntity<String> request = new HttpEntity<>(hexaCodeToCreate, new HttpHeaders());
		
		// When
		ResponseEntity<ColorDto> response = restTemplate.postForEntity(url, request, ColorDto.class);
		
		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		ColorDto createdColor = response.getBody();
		assertNotNull(createdColor.getId());
		assertEquals("#ABCDEF", createdColor.getHexaCode());
	}
	
	@Test
	void givenInvalidColor_whenPostCreate_thenInvalidHexaCodeError() {
		// Given
		String url = String.format(API_URL_PATTERN, getServerPort());
		
		String hexaCodeToCreate = "#ZZZZZZ";
		HttpEntity<String> request = new HttpEntity<>(hexaCodeToCreate, new HttpHeaders());
		
		// When
		ResponseEntity<ApiBusinessErrorDto> response = restTemplate.postForEntity(url, request, ApiBusinessErrorDto.class);
		
		// Then
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		
		ApiBusinessErrorDto error = response.getBody();
		assertEquals(ApiBusinessErrorCodeDto.COLOR_INVALID_HEXA_CODE, error.getErrorCode());
		assertEquals(ApiBusinessErrorCodeDto.COLOR_INVALID_HEXA_CODE.getDescription(), error.getMessage());
	}
	
	@Test
	void givenColorAlreadyExists_whenPostCreate_thenColorAlreadyExistsError() {
		// Given
		getTestContainers().getMysqlTestContainer().executeSqlScript("api/v2/color/post_create_createColor.sql");
		String url = String.format(API_URL_PATTERN, getServerPort());
		
		String hexaCodeToCreate = "#000000";
		HttpEntity<String> request = new HttpEntity<>(hexaCodeToCreate, new HttpHeaders());
		
		// When
		ResponseEntity<ApiBusinessErrorDto> response = restTemplate.postForEntity(url, request, ApiBusinessErrorDto.class);
		
		// Then
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		
		ApiBusinessErrorDto error = response.getBody();
		assertEquals(ApiBusinessErrorCodeDto.COLOR_SAME_HEXA_CODE_ALREADY_EXISTS, error.getErrorCode());
		assertEquals(ApiBusinessErrorCodeDto.COLOR_SAME_HEXA_CODE_ALREADY_EXISTS.getDescription(), error.getMessage());
	}
	
	@Test
	@Transactional // To get a connection using the specific datasource configuration we defined on top of the class.
	void givenDatabaseUnavailable_whenPostCreate_thenGenericError() {
		// Given
		String url = String.format(API_URL_PATTERN, getServerPort());
		
		String hexaCodeToCreate = "#000000";
		HttpEntity<String> request = new HttpEntity<>(hexaCodeToCreate, new HttpHeaders());
		
		// When
		ResponseEntity<ApiTechnicalErrorDto> response;
		try {
			getTestContainers().getMysqlTestContainer().pause();
			response = restTemplate.postForEntity(url, request, ApiTechnicalErrorDto.class);
		}
		finally {
			getTestContainers().getMysqlTestContainer().unpause();
		}
		
		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
		ApiTechnicalErrorDto apiTechnicalErrorDto = response.getBody();
		assertEquals("An unexpected error occured", apiTechnicalErrorDto.getMessage());
	}
	
	@Test
	void givenValidColor_whenDeleteDeleteById_thenColorDeleted() {
		// Given
		getTestContainers().getMysqlTestContainer().executeSqlScript("api/v2/color/delete_deleteById_createSeveralColors.sql");
		String url = String.format(DELETE_BY_ID_API_URL_PATTERN, getServerPort());
		
		String colorIdToDelete = "id_1";
		
		// When
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class, colorIdToDelete);
		
		// Then
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		List<ColorDto> remainingColors = getAllColors();
		assertEquals(1, remainingColors.size());

		ColorDto remainingColor = remainingColors.get(0);
		assertEquals("id_2", remainingColor.getId());
		assertEquals("#000001", remainingColor.getHexaCode());
	}
	
	private List<ColorDto> getAllColors() {
		String findAllUrl = String.format(API_URL_PATTERN, getServerPort());
		ResponseEntity<ColorDto[]> findAllResponse = restTemplate.getForEntity(findAllUrl, ColorDto[].class);
		return Arrays.asList(findAllResponse.getBody());
	}
	
	@Test
	@Transactional // To get a connection using the specific datasource configuration we defined on top of the class.
	void givenDatabaseUnavailable_whenDeleteDeleteById_thenGenericError() {
		// Given
		String url = String.format(DELETE_BY_ID_API_URL_PATTERN, getServerPort());
		
		String colorIdToDelete = "anything";
		
		// When
		ResponseEntity<ApiTechnicalErrorDto> response;
		try {
			getTestContainers().getMysqlTestContainer().pause();
			response = restTemplate.exchange(url, HttpMethod.DELETE, null, ApiTechnicalErrorDto.class, colorIdToDelete);
		}
		finally {
			getTestContainers().getMysqlTestContainer().unpause();
		}
		
		// Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
		ApiTechnicalErrorDto apiTechnicalErrorDto = response.getBody();
		assertEquals("An unexpected error occured", apiTechnicalErrorDto.getMessage());
	}
	
}