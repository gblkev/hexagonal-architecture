package com.gebel.hexagonalarchitecture.outbound.mysql.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gebel.hexagonalarchitecture.outbound.mysql.entity.ColorEntity;

public interface ColorRepository extends JpaRepository<ColorEntity, String> {

	Optional<ColorEntity> findOneByHexaCodeIgnoreCase(String hexaCode);
	
}