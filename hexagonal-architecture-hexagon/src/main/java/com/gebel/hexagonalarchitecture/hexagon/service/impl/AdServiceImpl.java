package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import com.gebel.hexagonalarchitecture.hexagon.domain.Ad;
import com.gebel.hexagonalarchitecture.hexagon.domain.AdCategory;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.FormulaOneAdExternalServicePort;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.SportAdExternalServicePort;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.AdService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
	
	private final FormulaOneAdExternalServicePort formulaOneAdExternalServicePort;
	private final SportAdExternalServicePort sportAdExternalServicePort;

	@Override
	@SneakyThrows({ InterruptedException.class, ExecutionException.class })
	public List<Ad> getPersonalizedAds(String driverId) {
		CompletableFuture<List<Ad>> formulaOneAdsFuture = CompletableFuture.supplyAsync(() -> getFormulaOneAdsSilently(driverId));
		CompletableFuture<List<Ad>> sportAdsFuture = CompletableFuture.supplyAsync(() -> getSportAdsSilently(driverId));
		CompletableFuture.allOf(formulaOneAdsFuture, sportAdsFuture).get();
		return ListUtils.union(formulaOneAdsFuture.get(), sportAdsFuture.get());
	}
	
	private List<Ad> getFormulaOneAdsSilently(String driverId) {
		try {
			List<Ad> ads = formulaOneAdExternalServicePort.getPersonalizedAds(driverId);
			ads.forEach((ad) -> {
				ad.setId("formulaone-" + ad.getId());
				ad.setCategory(AdCategory.FORMULA_ONE);
			});
			return ads;
		}
		catch (Exception e) {
			LOGGER.error("Error while retrieving formula one ads for driverId={}", driverId, e);
			return Collections.emptyList();
		}
	}
	
	private List<Ad> getSportAdsSilently(String driverId) {
		try {
			List<Ad> ads = sportAdExternalServicePort.getPersonalizedAds(driverId);
			ads.forEach((ad) -> {
				ad.setId("sport-" + ad.getId());
				ad.setCategory(AdCategory.SPORT);
			});
			return ads;
		}
		catch (Exception e) {
			LOGGER.error("Error while retrieving sport ads for driverId={}", driverId, e);
			return Collections.emptyList();
		}
	}

	@Override
	@SneakyThrows({ InterruptedException.class, ExecutionException.class })
	public void unsubscribePersonalizedAds(String driverId) {
		CompletableFuture<Void> formulaOneAdsFuture = CompletableFuture.runAsync(() -> formulaOneAdExternalServicePort.unsubscribePersonalizedAds(driverId));
		CompletableFuture<Void> sportAdsFuture = CompletableFuture.runAsync(() -> sportAdExternalServicePort.unsubscribePersonalizedAds(driverId));
		CompletableFuture.allOf(formulaOneAdsFuture, sportAdsFuture).get();
	}

}