package com.gebel.hexagonalarchitecture.outbound.rest.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Ad;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.FormulaOneAdExternalServicePort;
import com.gebel.hexagonalarchitecture.outbound.rest.adapter.converter.DomainAdConverter;
import com.gebel.hexagonalarchitecture.outbound.rest.interfaces.FormulaOneAdRestWs;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FormulaOneAdExternalServiceAdapter implements FormulaOneAdExternalServicePort {
	
	private final FormulaOneAdRestWs formulaOneAdRestWs;
	private final DomainAdConverter adConverter;

	@Override
	public List<Ad> getPersonalizedAds(String driverId) {
		return adConverter.formulaOneAdsToDomain(formulaOneAdRestWs.getPersonalizedAds(driverId));
	}

	@Override
	public void unsubscribePersonalizedAds(String driverId) {
		formulaOneAdRestWs.unsubscribePersonalizedAds(driverId);
	}

}