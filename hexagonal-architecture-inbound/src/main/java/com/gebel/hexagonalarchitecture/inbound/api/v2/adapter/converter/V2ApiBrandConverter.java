package com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.converter;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Brand;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.BrandDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class V2ApiBrandConverter {
	
	private final V2ApiModelConverter modelConverter;
	
	public BrandDto toDto(Brand domainBrand) {
		return BrandDto.builder()
			.id(domainBrand.getId())
			.name(domainBrand.getName())
			.models(modelConverter.toDto(domainBrand.getModels()))
			.build();
	}

	public List<BrandDto> toDto(List<Brand> domainBrands) {
		return CollectionUtils.emptyIfNull(domainBrands)
			.stream()
			.map(this::toDto)
			.toList();
	}

}