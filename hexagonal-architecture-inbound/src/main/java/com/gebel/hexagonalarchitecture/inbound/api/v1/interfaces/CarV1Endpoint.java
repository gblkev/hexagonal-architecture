package com.gebel.hexagonalarchitecture.inbound.api.v1.interfaces;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.inbound.api.v1.dto.CarDto;
import com.gebel.hexagonalarchitecture.inbound.api.v1.dto.CreateCarDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping(V1ApiBaseUri.API_V1_BASE_URI + "/cars")
@Tag(name = "Cars")
public interface CarV1Endpoint {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "List all the cars")
	List<CarDto> getAllCars();
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Create a new car")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "ApiBusinessErrorCodeDto.CAR_INVALID_COLOR"),
		@ApiResponse(responseCode = "ApiBusinessErrorCodeDto.CAR_INVALID_DRIVER")
	})
	CarDto createCar(@RequestBody @Schema(required = true, implementation = CreateCarDto.class) CreateCarDto createCarDto) throws BusinessException;
	
	@DeleteMapping("/{carId}")
	@Operation(summary = "Delete an existing car")
	void deleteCar(@PathVariable("carId") String carId);
	
}