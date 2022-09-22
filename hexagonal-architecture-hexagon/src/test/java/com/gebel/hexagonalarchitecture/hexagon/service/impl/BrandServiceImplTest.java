package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gebel.hexagonalarchitecture.hexagon.domain.Brand;
import com.gebel.hexagonalarchitecture.hexagon.domain.Model;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.BrandRepositoryPort;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {
	
	@Mock
	private BrandRepositoryPort brandRepositoryPort;
	
	private BrandServiceImpl brandService;
	
	@BeforeEach
	void setup() {
		brandService = new BrandServiceImpl(brandRepositoryPort);
	}
	
	@Test
	void givenSeveralBrands_whenGetAllBrands_thenAllBrandsRetrieved() {
		// Given
		Model repositoryBrand1Model1 = new Model("model_id1", "model_name1");
		Model repositoryBrand1Model2 = new Model("model_id2", "model_name2");
		Brand repositoryBrand1 = new Brand("brand_id1", "brand_name1", List.of(repositoryBrand1Model1, repositoryBrand1Model2));
		
		Model modelBrand2ModelModel1 = new Model("model_id3", "model_name3");
		Brand repositoryBrand2 = new Brand("brand_id2", "brand_name2", List.of(modelBrand2ModelModel1));
		
		List<Brand> repositoryBrands = List.of(repositoryBrand1, repositoryBrand2);
		when(brandRepositoryPort.findAll())
			.thenReturn(repositoryBrands);

		// When
		List<Brand> foundBrands = brandService.getAllBrands();
		
		// Then
		assertEquals(2, foundBrands.size());
		
		Brand foundBrand1 = foundBrands.get(0);
		assertEquals("brand_id1", foundBrand1.getId());
		assertEquals("brand_name1", foundBrand1.getName());
		List<Model> foundBrand1Models = foundBrand1.getModels();
		assertEquals(2, foundBrand1Models.size());
		Model foundBrand1Model1 = foundBrand1Models.get(0);
		assertEquals("model_id1", foundBrand1Model1.getId());
		assertEquals("model_name1", foundBrand1Model1.getName());
		Model foundBrand1Model2 = foundBrand1Models.get(1);
		assertEquals("model_id2", foundBrand1Model2.getId());
		assertEquals("model_name2", foundBrand1Model2.getName());
		
		Brand foundBrand2 = foundBrands.get(1);
		assertEquals("brand_id2", foundBrand2.getId());
		assertEquals("brand_name2", foundBrand2.getName());
		List<Model> foundBrand2Models = foundBrand2.getModels();
		assertEquals(1, foundBrand2Models.size());
		Model foundBrand2Model1 = foundBrand2Models.get(0);
		assertEquals("model_id3", foundBrand2Model1.getId());
		assertEquals("model_name3", foundBrand2Model1.getName());
	}
	
	@Test
	void givenSeveralBrands_whenCountBrands_thenRightCount() {
		// Given
		when(brandRepositoryPort.count())
			.thenReturn(17L);
		
		// When
		long count = brandService.countBrands();
		
		// Then
		assertEquals(17, count);
	}
	
}