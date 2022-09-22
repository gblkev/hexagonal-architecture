package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gebel.hexagonalarchitecture.hexagon.domain.Ad;
import com.gebel.hexagonalarchitecture.hexagon.domain.AdCategory;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.FormulaOneAdExternalServicePort;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.SportAdExternalServicePort;

@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {
	
	@Mock	
	private FormulaOneAdExternalServicePort formulaOneAdExternalServicePort;
	
	@Mock
	private SportAdExternalServicePort sportAdExternalServicePort;
	
	private AdServiceImpl adService;
	
	@BeforeEach
	void setup() {
		adService = new AdServiceImpl(formulaOneAdExternalServicePort, sportAdExternalServicePort);
	}
	
	@Test
	void givenSeveralAds_whenGetPersonalizedAds_thenAllAdsRetrieved() {
		// Given
		String driverId = "test_driver_id";
		
		Ad formulaOneRepositoryAd1 = Ad.builder()
			.id("test_id1")
			.category(AdCategory.FORMULA_ONE)
			.message("Formula one message 1")
			.build();
		Ad formulaOneRepositoryAd2 = Ad.builder()
			.id("test_id2")
			.category(AdCategory.FORMULA_ONE)
			.message("Formula one message 2")
			.build();
		List<Ad> formulaOneRepositoryAds = List.of(formulaOneRepositoryAd1, formulaOneRepositoryAd2);
		when(formulaOneAdExternalServicePort.getPersonalizedAds(driverId))
			.thenReturn(formulaOneRepositoryAds);
		
		Ad sportRepositoryAd1 = Ad.builder()
			.id("test_id3")
			.category(AdCategory.SPORT)
			.message("Sport message 1")
			.build();
		Ad sportRepositoryAd2 = Ad.builder()
			.id("test_id4")
			.category(AdCategory.SPORT)
			.message("Sport message 2")
			.build();
		List<Ad> sportRepositoryAds = List.of(sportRepositoryAd1, sportRepositoryAd2);
		when(sportAdExternalServicePort.getPersonalizedAds(driverId))
			.thenReturn(sportRepositoryAds);
		
		// When
		List<Ad> ads = adService.getPersonalizedAds(driverId);
		
		// Then
		assertEquals(4, ads.size());
		
		Ad ad1 = ads.get(0);
		assertEquals("formulaone-test_id1", ad1.getId());
		assertEquals(AdCategory.FORMULA_ONE, ad1.getCategory());
		assertEquals("Formula one message 1", ad1.getMessage());
		
		Ad ad2 = ads.get(1);
		assertEquals("formulaone-test_id2", ad2.getId());
		assertEquals(AdCategory.FORMULA_ONE, ad2.getCategory());
		assertEquals("Formula one message 2", ad2.getMessage());
		
		Ad ad3 = ads.get(2);
		assertEquals("sport-test_id3", ad3.getId());
		assertEquals(AdCategory.SPORT, ad3.getCategory());
		assertEquals("Sport message 1", ad3.getMessage());
		
		Ad ad4 = ads.get(3);
		assertEquals("sport-test_id4", ad4.getId());
		assertEquals(AdCategory.SPORT, ad4.getCategory());
		assertEquals("Sport message 2", ad4.getMessage());
	}
	
	@Test
	void givenErrorWhenRetrievingSportAds_whenGetPersonalizedAds_thenOtherAdsRetrieved() {
		// Given
		String driverId = "test_driver_id";
		
		Ad formulaOneRepositoryAd1 = Ad.builder()
			.id("test_id1")
			.category(AdCategory.FORMULA_ONE)
			.message("Formula one message 1")
			.build();
		Ad formulaOneRepositoryAd2 = Ad.builder()
			.id("test_id2")
			.category(AdCategory.FORMULA_ONE)
			.message("Formula one message 2")
			.build();
		List<Ad> formulaOneRepositoryAds = List.of(formulaOneRepositoryAd1, formulaOneRepositoryAd2);
		when(formulaOneAdExternalServicePort.getPersonalizedAds(driverId))
			.thenReturn(formulaOneRepositoryAds);
		
		when(sportAdExternalServicePort.getPersonalizedAds(driverId))
			.thenThrow(new RuntimeException("Test"));
		
		// When
		List<Ad> ads = adService.getPersonalizedAds(driverId);
		
		// Then
		assertEquals(2, ads.size());
		
		Ad ad1 = ads.get(0);
		assertEquals("formulaone-test_id1", ad1.getId());
		assertEquals(AdCategory.FORMULA_ONE, ad1.getCategory());
		assertEquals("Formula one message 1", ad1.getMessage());
		
		Ad ad2 = ads.get(1);
		assertEquals("formulaone-test_id2", ad2.getId());
		assertEquals(AdCategory.FORMULA_ONE, ad2.getCategory());
		assertEquals("Formula one message 2", ad2.getMessage());
	}
	
	@Test
	void givenErrorWhenRetrievingFormulaOneAds_whenGetPersonalizedAds_thenOtherAdsRetrieved() {
		// Given
		String driverId = "test_driver_id";
		
		when(formulaOneAdExternalServicePort.getPersonalizedAds(driverId))
			.thenThrow(new RuntimeException("Test"));
		
		Ad sportRepositoryAd1 = Ad.builder()
			.id("test_id1")
			.category(AdCategory.SPORT)
			.message("Sport message 1")
			.build();
		Ad sportRepositoryAd2 = Ad.builder()
			.id("test_id2")
			.category(AdCategory.SPORT)
			.message("Sport message 2")
			.build();
		List<Ad> sportRepositoryAds = List.of(sportRepositoryAd1, sportRepositoryAd2);
		when(sportAdExternalServicePort.getPersonalizedAds(driverId))
			.thenReturn(sportRepositoryAds);
		
		// When
		List<Ad> ads = adService.getPersonalizedAds(driverId);
		
		// Then
		assertEquals(2, ads.size());
		
		Ad ad1 = ads.get(0);
		assertEquals("sport-test_id1", ad1.getId());
		assertEquals(AdCategory.SPORT, ad1.getCategory());
		assertEquals("Sport message 1", ad1.getMessage());
		
		Ad ad2 = ads.get(1);
		assertEquals("sport-test_id2", ad2.getId());
		assertEquals(AdCategory.SPORT, ad2.getCategory());
		assertEquals("Sport message 2", ad2.getMessage());
	}
	
	@Test
	void givenErrorWhenRetrievingSportAndFormulaOneAds_whenGetPersonalizedAds_thenEmptyListReturned() {
		// Given
		String driverId = "test_driver_id";
		
		when(formulaOneAdExternalServicePort.getPersonalizedAds(driverId))
			.thenThrow(new RuntimeException("Test"));
		
		when(sportAdExternalServicePort.getPersonalizedAds(driverId))
			.thenThrow(new RuntimeException("Test"));
		
		// When
		List<Ad> ads = adService.getPersonalizedAds(driverId);
		
		// Then
		assertTrue(ads.isEmpty());
	}
	
	@Test
	void givenValidDriverId_whenUnsubscribePersonalizedAds_thenUnsubscribeFromBothFormulaOneAndSportAds() {
		// Given
		String driverId = "test_driver_id";
		
		// When
		adService.unsubscribePersonalizedAds(driverId);
		
		// Then
		verify(formulaOneAdExternalServicePort, times(1))
			.unsubscribePersonalizedAds("test_driver_id");
		verify(sportAdExternalServicePort, times(1))
			.unsubscribePersonalizedAds("test_driver_id");
	}
	
	@Test
	void givenErrorWhenUnsubscribingFromFormulaOneAds_whenUnsubscribePersonalizedAds_thenError() {
		// Given
		String driverId = "test_driver_id";
		
		doThrow(new RuntimeException("Test"))
			.when(formulaOneAdExternalServicePort)
			.unsubscribePersonalizedAds(driverId);
		
		// Then
		assertThrows(ExecutionException.class,() -> {
			// When
			adService.unsubscribePersonalizedAds(driverId);
		});
	}
	
	@Test
	void givenErrorWhenUnsubscribingFromSportAds_whenUnsubscribePersonalizedAds_thenError() {
		// Given
		String driverId = "test_driver_id";
		
		doThrow(new RuntimeException("Test"))
			.when(sportAdExternalServicePort)
			.unsubscribePersonalizedAds(driverId);
		
		// Then
		assertThrows(ExecutionException.class,() -> {
			// When
			adService.unsubscribePersonalizedAds(driverId);
		});
	}
	
}