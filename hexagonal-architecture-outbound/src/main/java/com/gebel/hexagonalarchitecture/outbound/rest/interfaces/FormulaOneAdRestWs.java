package com.gebel.hexagonalarchitecture.outbound.rest.interfaces;

import java.util.List;

import com.gebel.hexagonalarchitecture.outbound.rest.dto.FormulaOneAdDto;

public interface FormulaOneAdRestWs {

	List<FormulaOneAdDto> getPersonalizedAds(String driverId);
	
	void unsubscribePersonalizedAds(String driverId);
	
}