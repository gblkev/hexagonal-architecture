package com.gebel.hexagonalarchitecture.hexagon.port.outbound;

import java.util.List;

import com.gebel.hexagonalarchitecture.hexagon.domain.Ad;

public interface FormulaOneAdExternalServicePort {
	
	List<Ad> getPersonalizedAds(String driverId);
	
	void unsubscribePersonalizedAds(String driverId);

}