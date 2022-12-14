package com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.converter;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Ad;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.AdDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class V2ApiAdConverter {
	
	private final V2ApiAdCategoryConverter adCategoryConverter;
	
	public AdDto toDto(Ad domainAd) {
		return AdDto.builder()
			.id(domainAd.getId())
			.category(adCategoryConverter.toDto(domainAd.getCategory()))
			.message(domainAd.getMessage())
			.build();
	}

	public List<AdDto> toDto(List<Ad> domainAds) {
		return CollectionUtils.emptyIfNull(domainAds)
			.stream()
			.map(this::toDto)
			.toList();
	}

}