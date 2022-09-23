package com.gebel.hexagonalarchitecture.outbound.redis.adapter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Brand;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.BrandRepositoryPort;
import com.gebel.hexagonalarchitecture.outbound.redis.adapter.converter.DomainBrandConverter;
import com.gebel.hexagonalarchitecture.outbound.redis.interfaces.CustomCarBrandRepository;
import com.gebel.hexagonalarchitecture.outbound.redis.model.CarBrandModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BrandRepositoryAdapter implements BrandRepositoryPort {
	
	private final CustomCarBrandRepository customCarBrandRepository;
	private final DomainBrandConverter domainBrandConverter;
	
	@Override
	public List<Brand> findAll() {
		Iterable<CarBrandModel> carBrandsModels = customCarBrandRepository.findAllWithoutSpringBug();
		List<CarBrandModel> carBrandsModelsAsList = StreamSupport.stream(carBrandsModels.spliterator(), false)
			.collect(Collectors.toList());
		return domainBrandConverter.toDomain(carBrandsModelsAsList);
	}

	@Override
	public long count() {
		return customCarBrandRepository.countWithoutSpringBug();
	}

}