package com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.impl;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.gebel.hexagonalarchitecture.hexagon.port.inbound.AdServicePort;
import com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.converter.V2ApiAdConverter;
import com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.interfaces.AdV2Endpoint;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.AdDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class AdV2EndpointImpl implements AdV2Endpoint {

	private final AdServicePort adService;
	private final V2ApiAdConverter adConverter;
	
	@Override
	public List<AdDto> getPersonalizedAds(String driverId) {
		LOGGER.info("Retrieving personalized ads for driverId={}", driverId);
		return adConverter.toDto(adService.getPersonalizedAds(driverId));
	}

	@Override
	public void unsubscribePersonalizedAds(String driverId) {
		LOGGER.info("Unsubscribing driverId={} from getting personalized ads", driverId);
		adService.unsubscribePersonalizedAds(driverId);
	}

}