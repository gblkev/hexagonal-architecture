package com.gebel.hexagonalarchitecture.outbound.mysql.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Color;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.ColorRepositoryPort;
import com.gebel.hexagonalarchitecture.outbound.mysql.adapter.converter.DomainColorConverter;
import com.gebel.hexagonalarchitecture.outbound.mysql.entity.ColorEntity;
import com.gebel.hexagonalarchitecture.outbound.mysql.interfaces.ColorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ColorRepositoryAdapter implements ColorRepositoryPort {

	private final ColorRepository colorRepository;
	private final DomainColorConverter colorConverter;
	
	@Override
	public Optional<Color> findById(String colorId) {
		return colorConverter.toDomain(colorRepository.findById(colorId));
	}

	@Override
	public Optional<Color> findOneByHexaCodeIgnoreCase(String hexaCode) {
		return colorConverter.toDomain(colorRepository.findOneByHexaCodeIgnoreCase(hexaCode));
	}
	
	@Override
	public List<Color> findAll() {
		return colorConverter.toDomain(colorRepository.findAll());
	}

	@Override
	public long count() {
		return colorRepository.count();
	}

	@Override
	public Color save(Color color) {
		ColorEntity colorEntityToSave = colorConverter.toEntity(color);
		return colorConverter.toDomain(colorRepository.save(colorEntityToSave));
	}

	@Override
	public void deleteById(String colorId) {
		colorRepository.deleteById(colorId);
	}

}