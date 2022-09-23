package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessErrorCode;
import com.gebel.hexagonalarchitecture.hexagon.domain.BusinessException;
import com.gebel.hexagonalarchitecture.hexagon.domain.Color;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.ColorRepositoryPort;

@ExtendWith(MockitoExtension.class)
class ColorServiceImplTest {
	
	@Mock
	private ColorRepositoryPort colorRepositoryPort;
	
	private ColorServiceImpl colorService;
	
	@BeforeEach
	void setup() {
		colorService = new ColorServiceImpl(colorRepositoryPort);
	}
	
	@Test
	void givenExistingColor_whenGetById_thenColorRetrieved() {
		// Given
		String colorId = "test_id1";
		Color repositoryColor = Color.builder()
			.id(colorId)
			.hexaCode("#000001")
			.build();
		when(colorRepositoryPort.findById(colorId))
			.thenReturn(Optional.of(repositoryColor));
		
		// When
		Optional<Color> optionalColor = colorService.findById(colorId);
		
		// Then
		assertTrue(optionalColor.isPresent());
		
		Color color = optionalColor.get();
		assertEquals("test_id1", color.getId());
		assertEquals("#000001", color.getHexaCode());
	}
	
	@Test
	void givenNonExistingColor_whenGetById_thenEmptyResult() {
		// Given
		String colorId = "test_id1";
		when(colorRepositoryPort.findById(colorId))
			.thenReturn(Optional.empty());
		
		// When
		Optional<Color> optionalColor = colorService.findById(colorId);
		
		// Then
		assertTrue(optionalColor.isEmpty());
	}

	@Test
	void givenSeveralColors_whenGetAllColors_thenAllColorsRetrieved() {
		// Given
		Color repositoryColor1 = Color.builder()
			.id("test_id1")
			.hexaCode("#000001")
			.build();
		Color repositoryColor2 = Color.builder()
			.id("test_id2")
			.hexaCode("#000002")
			.build();
		List<Color> repositoryColors = List.of(repositoryColor1, repositoryColor2);
		when(colorRepositoryPort.findAll())
			.thenReturn(repositoryColors);
		
		// When
		List<Color> colors = colorService.getAllColors();
		
		// Then
		assertEquals(2, colors.size());
		
		Color color1 = colors.get(0);
		assertEquals("test_id1", color1.getId());
		assertEquals("#000001", color1.getHexaCode());
		
		Color color2 = colors.get(1);
		assertEquals("test_id2", color2.getId());
		assertEquals("#000002", color2.getHexaCode());
	}
	
	@Test
	void givenSeveralColors_whenCountColors_thenRightCount() {
		// Given
		when(colorRepositoryPort.count())
			.thenReturn(17L);
		
		// When
		long count = colorService.countColors();
		
		// Then
		assertEquals(17, count);
	}
	
	@Test
	void givenValidColor_whenCreateColor_thenColorCreated() throws BusinessException {
		// Given
		Color createdRepositoryColor = Color.builder()
			.id("test_id")
			.hexaCode("#000000")
			.build();
		ArgumentCaptor<Color> repositoryColorArgumentCaptor = ArgumentCaptor.forClass(Color.class);
		when(colorRepositoryPort.save(repositoryColorArgumentCaptor.capture()))
			.thenReturn(createdRepositoryColor);
		
		String hexaCodeOfColorToCreate = "#000000";
		
		// When
		Color createdColor = colorService.createColor(hexaCodeOfColorToCreate);
		
		// Then
		assertNull(repositoryColorArgumentCaptor.getValue().getId());
		assertEquals("#000000", repositoryColorArgumentCaptor.getValue().getHexaCode());
		
		assertEquals("test_id", createdColor.getId());
		assertEquals("#000000", createdColor.getHexaCode());
	}
	
	@Test
	void givenInvalidHexadecimalCode_whenCreateColor_thenThrowBusinessException() throws BusinessException {
		// Given
		String hexaCodeOfColorToCreate = null;
		
		BusinessException businessException = assertThrows(BusinessException.class, () -> {
			// When
			colorService.createColor(hexaCodeOfColorToCreate);
		});

		// Then
		verifyNoInteractions(colorRepositoryPort);
		assertEquals(BusinessErrorCode.COLOR_INVALID_HEXA_CODE, businessException.getBusinessError());
	}
	
	@Test
	void givenAlreadyExistingColor_whenCreateColor_thenThrowBusinessException() throws BusinessException {
		// Given
		String hexaCodeOfColorToCreate = "#000000";
		Color existingRepositoryColor = Color.builder()
			.id("test_id")
			.hexaCode("#000000")
			.build();
		when(colorRepositoryPort.findOneByHexaCodeIgnoreCase("#000000"))
			.thenReturn(Optional.of(existingRepositoryColor));
		
		BusinessException businessException = assertThrows(BusinessException.class, () -> {
			// When
			colorService.createColor(hexaCodeOfColorToCreate);
		});

		// Then
		verify(colorRepositoryPort, never())
			.save(any());
		assertEquals(BusinessErrorCode.COLOR_SAME_HEXA_CODE_ALREADY_EXISTS, businessException.getBusinessError());
	}
	
	@Test
	void givenExistingColor_whenDeleteColor_thenColorDeleted() {
		// Given
		String colorId = "color_id";
		
		// When
		colorService.deleteColor(colorId);
		
		// Then
		verify(colorRepositoryPort, times(1))
			.deleteById("color_id");
	}
	
}