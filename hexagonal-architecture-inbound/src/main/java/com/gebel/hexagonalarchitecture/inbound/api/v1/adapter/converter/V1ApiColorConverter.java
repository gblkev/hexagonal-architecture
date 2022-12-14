package com.gebel.hexagonalarchitecture.inbound.api.v1.adapter.converter;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Color;
import com.gebel.hexagonalarchitecture.inbound.api.v1.dto.ColorDto;

@Component
public class V1ApiColorConverter {
	
	public ColorDto toDto(Color domainColor) {
		return ColorDto.builder()
			.id(domainColor.getId())
			.hexaCode(domainColor.getHexaCode())
			.build();
	}

	public List<ColorDto> toDto(List<Color> domainColors) {
		return CollectionUtils.emptyIfNull(domainColors)
			.stream()
			.map(this::toDto)
			.toList();
	}

}