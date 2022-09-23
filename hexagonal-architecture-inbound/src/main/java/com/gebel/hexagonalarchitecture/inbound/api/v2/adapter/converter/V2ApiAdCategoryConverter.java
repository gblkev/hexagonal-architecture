package com.gebel.hexagonalarchitecture.inbound.api.v2.adapter.converter;

import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.AdCategory;
import com.gebel.hexagonalarchitecture.inbound.api.v2.dto.AdCategoryDto;

@Component
public class V2ApiAdCategoryConverter {

	public AdCategoryDto toDto(AdCategory adCategory) {
		switch (adCategory) {
			case FORMULA_ONE: return AdCategoryDto.FORMULA_ONE;
			case SPORT: return AdCategoryDto.SPORT;
			default: throw new IllegalArgumentException("Category " + adCategory + " is not handled");
		}
	}
	
}