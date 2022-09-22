package com.gebel.hexagonalarchitecture.inbound.api.v1.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.port.inbound.ColorServicePort;
import com.gebel.hexagonalarchitecture.inbound.api.v1.converter.V1ApiColorConverter;
import com.gebel.hexagonalarchitecture.inbound.api.v1.dto.ColorDto;
import com.gebel.hexagonalarchitecture.inbound.api.v1.interfaces.ColorV1Endpoint;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class ColorV1EndpointImpl implements ColorV1Endpoint {

	private final ColorServicePort colorServicePort;
	private final V1ApiColorConverter colorConverter;
	
	@Override
	public List<ColorDto> getAllColors() {
		LOGGER.info("Listing all available colors");
		return colorConverter.toDto(colorServicePort.getAllColors());
	}

	@Override
	public ColorDto createColor(String hexaCode) throws BusinessException {
		LOGGER.info("Creating color with hexaCode={}", hexaCode);
		return colorConverter.toDto(colorServicePort.createColor(hexaCode));
	}

	@Override
	public void deleteColor(String colorId) {
		LOGGER.info("Deleting color with id={}", colorId);
		colorServicePort.deleteColor(colorId);
	}

}