package com.gebel.hexagonalarchitecture.outbound.mysql.adapter.converter;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Color;
import com.gebel.hexagonalarchitecture.outbound.mysql.entity.ColorEntity;

@Component
public class DomainColorConverter {
	
	public Color toDomain(ColorEntity entityColor) {
		if (entityColor == null) {
			return null;
		}
		return Color.builder()
			.id(entityColor.getId())
			.hexaCode(entityColor.getHexaCode())
			.build();
	}
	
	public Optional<Color> toDomain(Optional<ColorEntity> optionalEntityColor) {
		if (optionalEntityColor.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(toDomain(optionalEntityColor.get()));
	}

	public List<Color> toDomain(List<ColorEntity> entitiesColors) {
		return CollectionUtils.emptyIfNull(entitiesColors)
			.stream()
			.map(this::toDomain)
			.toList();
	}
	
	public ColorEntity toEntity(Color domainColor) {
		if (domainColor == null) {
			return null;
		}
		return ColorEntity.builder()
			.id(domainColor.getId())
			.hexaCode(domainColor.getHexaCode())
			.build();
	}

}