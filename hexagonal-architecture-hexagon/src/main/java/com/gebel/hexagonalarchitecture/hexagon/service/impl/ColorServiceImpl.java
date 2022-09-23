package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessErrorCode;
import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Color;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.ColorRepositoryPort;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.ColorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

	private final ColorRepositoryPort colorRepositoryPort;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Color> findById(String id) {
		return colorRepositoryPort.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Color> getAllColors() {
		return colorRepositoryPort.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countColors() {
		return colorRepositoryPort.count();
	}

	@Override
	@Transactional
	public Color createColor(String hexaCode) throws BusinessException {
		Color color = Color.builder()
			.hexaCode(hexaCode)
			.build();
		checkColorCreationPrerequisites(color);
		return colorRepositoryPort.save(color);
	}
	
	private void checkColorCreationPrerequisites(Color color) throws BusinessException {
		color.validate();
		checkIfColorAlreadyExists(color);
	}
	
	private void checkIfColorAlreadyExists(Color color) throws BusinessException {
		boolean doesColorAlreadyExist = colorRepositoryPort.findOneByHexaCodeIgnoreCase(color.getHexaCode()).isPresent();
		if (doesColorAlreadyExist) {
			throw new BusinessException("Color " + color.getHexaCode() + " already exists", BusinessErrorCode.COLOR_SAME_HEXA_CODE_ALREADY_EXISTS);
		}
	}

	@Override
	@Transactional
	public void deleteColor(String colorId) {
		colorRepositoryPort.deleteById(colorId);
	}

}