package com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.gebel.hexagonalarchitecture.hexagon.port.inbound.BrandServicePort;
import com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.converter.V2ApiBrandConverter;
import com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.interfaces.BrandV2Endpoint;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.BrandDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class BrandV2EndpointImpl implements BrandV2Endpoint {

	private final BrandServicePort brandService;
	private final V2ApiBrandConverter brandConverter;
	
	@Override
	public List<BrandDto> getAllBrands() {
		LOGGER.info("Listing all brands");
		return brandConverter.toDto(brandService.getAllBrands());
	}

}