package com.gebel.hexagonalarchitecture.outbound.rest.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.gebel.hexagonalarchitecture.outbound.rest.dto.FormulaOneAdDto;
import com.gebel.hexagonalarchitecture.outbound.rest.interfaces.FormulaOneAdRestWs;

@Component
public class FormulaOneAdRestWsImpl implements FormulaOneAdRestWs {

	private static final String DRIVER_ID_URI_PARAMETER = "driverId";
	
	@Value("${dao.rest.formula-one.ad.url}")
	private String baseUrl;
	
	@Value("${dao.rest.formula-one.ad.connect-timeout-in-millis}")
	private int connectTimeoutInMillis;
	
	@Value("${dao.rest.formula-one.ad.read-timeout-in-millis}")
	private int readTimeoutInMillis;
	
	private UriComponents getPersonalizedAdUrlTemplate;
	private UriComponents unsubscribePersonalizedAdUrlTemplate;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@PostConstruct
	private void init() {
		getPersonalizedAdUrlTemplate = UriComponentsBuilder.fromUriString(baseUrl)
			.path("/{" + DRIVER_ID_URI_PARAMETER + "}")
			.build();
		unsubscribePersonalizedAdUrlTemplate = UriComponentsBuilder.fromUriString(baseUrl)
			.path("/unsubscribe/{" + DRIVER_ID_URI_PARAMETER + "}")
			.build();
		setTimeouts();
	}
		
	private void setTimeouts() {
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
		simpleClientHttpRequestFactory.setConnectTimeout(connectTimeoutInMillis);
		simpleClientHttpRequestFactory.setReadTimeout(readTimeoutInMillis);
	}
	
	@Override
	public List<FormulaOneAdDto> getPersonalizedAds(String driverId) {
		String getPersonalizedAdUrl = getPersonalizedAdUrlTemplate
			.expand(Map.of(DRIVER_ID_URI_PARAMETER, driverId))
			.toUriString();
		try {
			return Arrays.asList(restTemplate.getForObject(getPersonalizedAdUrl, FormulaOneAdDto[].class, driverId));
		}
		catch (Exception e) {
			String message = String.format(
				"Error while calling FormulaOneAdRestWs.getPersonalizedAd for driverId=%s and url=%s",
				driverId,
				getPersonalizedAdUrl);
			throw new RuntimeException(message, e);
		}
	}

	@Override
	public void unsubscribePersonalizedAds(String driverId) {
		String unsubscribePersonalizedAdUrl = unsubscribePersonalizedAdUrlTemplate
			.expand(Map.of(DRIVER_ID_URI_PARAMETER, driverId))
			.toUriString();
		
		try {
			restTemplate.postForEntity(unsubscribePersonalizedAdUrl, HttpEntity.EMPTY, Void.class, driverId);
		}
		catch (Exception e) {
			String message = String.format(
				"Error while calling FormulaOneAdRestWs.unsubscribePersonalizedAd for driverId=%s and url=%s",
				driverId,
				unsubscribePersonalizedAdUrl);
			throw new RuntimeException(message, e);
		}
	}
	
}