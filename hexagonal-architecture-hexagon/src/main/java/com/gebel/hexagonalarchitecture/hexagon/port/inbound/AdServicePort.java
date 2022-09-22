package com.gebel.hexagonalarchitecture.hexagon.port.inbound;

import java.util.List;

import com.gebel.hexagonalarchitecture.hexagon.domain.Ad;

public interface AdServicePort {

	List<Ad> getPersonalizedAds(String driverId);
	
	void unsubscribePersonalizedAds(String driverId);
	
}