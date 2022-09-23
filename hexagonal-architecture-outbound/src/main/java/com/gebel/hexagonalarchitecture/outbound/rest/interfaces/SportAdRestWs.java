package com.gebel.hexagonalarchitecture.outbound.rest.interfaces;

import java.util.List;

import com.gebel.hexagonalarchitecture.outbound.rest.dto.SportAdDto;

public interface SportAdRestWs {

	List<SportAdDto> getPersonalizedAds(String driverId);
	
	void unsubscribePersonalizedAds(String driverId);
	
}