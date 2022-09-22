package com.gebel.hexagonalarchitecture.outbound.mysql.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gebel.hexagonalarchitecture.outbound.mysql.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, String> {

}