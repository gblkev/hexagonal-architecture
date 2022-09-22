package com.gebel.hexagonalarchitecture.outbound.mysql.adapter;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.DriverRepositoryPort;
import com.gebel.hexagonalarchitecture.outbound.mysql.adapter.converter.DomainDriverConverter;
import com.gebel.hexagonalarchitecture.outbound.mysql.entity.DriverEntity;
import com.gebel.hexagonalarchitecture.outbound.mysql.interfaces.DriverRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DriverRepositoryAdapter implements DriverRepositoryPort {

	private final DriverRepository driverRepository;
	private final DomainDriverConverter driverConverter;
	
	@Override
	public Optional<Driver> findById(String driverId) {
		if (StringUtils.isBlank(driverId)) {
			return Optional.empty();
		}
		return driverConverter.toDomain(driverRepository.findById(driverId));
	}
	
	@Override
	public List<Driver> findAll() {
		return driverConverter.toDomain(driverRepository.findAll());
	}

	@Override
	public long count() {
		return driverRepository.count();
	}

	@Override
	public Driver save(Driver driver) {
		DriverEntity driverEntityToSave = driverConverter.toEntity(driver);
		return driverConverter.toDomain(driverRepository.save(driverEntityToSave));
	}

	@Override
	public void deleteById(String driverId) {
		driverRepository.deleteById(driverId);
	}

}