package com.gebel.hexagonalarchitecture.outbound.mysql.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gebel.hexagonalarchitecture.outbound.mysql.entity.DriverEntity;

public interface DriverRepository extends JpaRepository<DriverEntity, String> {

}