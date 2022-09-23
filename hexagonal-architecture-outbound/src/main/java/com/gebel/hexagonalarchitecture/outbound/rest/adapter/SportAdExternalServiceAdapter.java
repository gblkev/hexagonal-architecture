package com.gebel.hexagonalarchitecture.outbound.rest.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Ad;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.SportAdExternalServicePort;
import com.gebel.hexagonalarchitecture.outbound.rest.adapter.converter.DomainAdConverter;
import com.gebel.hexagonalarchitecture.outbound.rest.interfaces.SportAdRestWs;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SportAdExternalServiceAdapter implements SportAdExternalServicePort {
	
	private final SportAdRestWs sportAdRestWs;
	private final DomainAdConverter adConverter;

	@Override
	public List<Ad> getPersonalizedAds(String driverId) {
		return adConverter.sportAdsToDomain(sportAdRestWs.getPersonalizedAds(driverId));
	}

	@Override
	public void unsubscribePersonalizedAds(String driverId) {
		sportAdRestWs.unsubscribePersonalizedAds(driverId);
	}

}