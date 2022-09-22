package com.gebel.hexagonalarchitecture.outbound.mysql.adapter.converter;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Driver;
import com.gebel.hexagonalarchitecture.outbound.mysql.entity.DriverEntity;

@Component
public class DomainDriverConverter {
	
	public Driver toDomain(DriverEntity entityDriver) {
		if (entityDriver == null) {
			return null;
		}
		return Driver.builder()
			.id(entityDriver.getId())
			.firstName(entityDriver.getFirstName())
			.lastName(entityDriver.getLastName())
			.build();
	}
	
	public Optional<Driver> toDomain(Optional<DriverEntity> optionalEntityDriver) {
		if (optionalEntityDriver.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(toDomain(optionalEntityDriver.get()));
	}

	public List<Driver> toDomain(List<DriverEntity> entitiesDrivers) {
		return CollectionUtils.emptyIfNull(entitiesDrivers)
			.stream()
			.map(this::toDomain)
			.toList();
	}
	
	public DriverEntity toEntity(Driver domainDriver) {
		if (domainDriver == null) {
			return null;
		}
		return DriverEntity.builder()
			.id(domainDriver.getId())
			.firstName(domainDriver.getFirstName())
			.lastName(domainDriver.getLastName())
			.build();
	}

}