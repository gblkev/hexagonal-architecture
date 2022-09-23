package com.gebel.hexagonalarchitecture.outbound.rest.adapter.converter;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Ad;
import com.gebel.hexagonalarchitecture.hexagon.domain.AdCategory;
import com.gebel.hexagonalarchitecture.outbound.rest.dto.FormulaOneAdDto;
import com.gebel.hexagonalarchitecture.outbound.rest.dto.SportAdDto;

@Component
public class DomainAdConverter {
	
	public Ad formulaOneAdToDomain(FormulaOneAdDto dtoAd) {
		if (dtoAd == null) {
			return null;
		}
		return Ad.builder()
			.id(dtoAd.getId())
			.category(AdCategory.FORMULA_ONE)
			.message(dtoAd.getMessage())
			.build();
	}

	public List<Ad> formulaOneAdsToDomain(List<FormulaOneAdDto> dtosAds) {
		return CollectionUtils.emptyIfNull(dtosAds)
			.stream()
			.map(this::formulaOneAdToDomain)
			.toList();
	}
	
	public Ad sportAdToDomain(SportAdDto dtoAd) {
		if (dtoAd == null) {
			return null;
		}
		return Ad.builder()
			.id(dtoAd.getId())
			.category(AdCategory.SPORT)
			.message(dtoAd.getMessage())
			.build();
	}

	public List<Ad> sportAdsToDomain(List<SportAdDto> dtosAds) {
		return CollectionUtils.emptyIfNull(dtosAds)
			.stream()
			.map(this::sportAdToDomain)
			.toList();
	}

}