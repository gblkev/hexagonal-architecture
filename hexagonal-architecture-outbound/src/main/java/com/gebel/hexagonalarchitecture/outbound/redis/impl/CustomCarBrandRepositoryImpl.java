package com.gebel.hexagonalarchitecture.outbound.redis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import com.gebel.hexagonalarchitecture.outbound.redis.interfaces.CarBrandRepository;
import com.gebel.hexagonalarchitecture.outbound.redis.interfaces.CustomCarBrandRepository;
import com.gebel.hexagonalarchitecture.outbound.redis.model.CarBrandModel;

import lombok.RequiredArgsConstructor;

// I had to implement a custom repository because of a bug in Spring data Redis at the moment:
// https://stackoverflow.com/questions/68454213/spring-boot-redis-crud-repository-findbyid-or-findall-always-returns-optional-e
@Repository
@RequiredArgsConstructor
public class CustomCarBrandRepositoryImpl implements CustomCarBrandRepository {

	private static final String HASH_KEY = "car_brand";
	private static final String HASH_KEY_PREFIX = HASH_KEY + ":";
	private final RedisTemplate<String, Map<String, Object>> redisTemplate;
    private final CarBrandRepository carBrandRepository;
    
	@Override
	public Iterable<CarBrandModel> findAllWithoutSpringBug() {
		return carBrandRepository.findAllById(findAllIds());
	}
	
	private List<String> findAllIds() {
		ScanOptions scanOptions = ScanOptions.scanOptions()
			.type(DataType.HASH)
			.match(HASH_KEY_PREFIX + "*")
			.count(Integer.MAX_VALUE)
			.build();
		List<String> carBrandModelsIds = new ArrayList<>();
		try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
			cursor.forEachRemaining(id -> carBrandModelsIds.add(removeHashKeyPrefixFromId(id)));
		}
		return carBrandModelsIds;
	}
	
	private String removeHashKeyPrefixFromId(String id) {
		return id.substring(HASH_KEY_PREFIX.length(), id.length());
	}

	@Override
	public long countWithoutSpringBug() {
		return findAllIds().size();
	}

}