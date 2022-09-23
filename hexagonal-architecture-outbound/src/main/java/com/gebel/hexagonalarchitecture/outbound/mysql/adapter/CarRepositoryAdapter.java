package com.gebel.hexagonalarchitecture.outbound.mysql.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.Car;
import com.gebel.hexagonalarchitecture.hexagon.port.outbound.CarRepositoryPort;
import com.gebel.hexagonalarchitecture.outbound.mysql.adapter.converter.DomainCarConverter;
import com.gebel.hexagonalarchitecture.outbound.mysql.entity.CarEntity;
import com.gebel.hexagonalarchitecture.outbound.mysql.interfaces.CarRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarRepositoryAdapter implements CarRepositoryPort {

	private final CarRepository carRepository;
	private final DomainCarConverter carConverter;
	
	@Override
	public List<Car> findAll() {
		return carConverter.toDomain(carRepository.findAll());
	}

	@Override
	public long count() {
		return carRepository.count();
	}

	@Override
	public Car save(Car car) {
		CarEntity carEntityToSave = carConverter.toEntity(car);
		return carConverter.toDomain(carRepository.save(carEntityToSave));
	}

	@Override
	public void deleteById(String carId) {
		carRepository.deleteById(carId);
	}

}