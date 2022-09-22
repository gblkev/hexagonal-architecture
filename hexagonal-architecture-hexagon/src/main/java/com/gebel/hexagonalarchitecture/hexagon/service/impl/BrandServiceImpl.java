package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gebel.hexagonalarchitecture.hexagon.domain.Brand;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.BrandRepositoryPort;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.BrandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

	private final BrandRepositoryPort brandRepositoryPort;
	
	@Override
	@Transactional(readOnly = true)
	public List<Brand> getAllBrands() {
		return brandRepositoryPort.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public long countBrands() {
		return brandRepositoryPort.count();
	}

}