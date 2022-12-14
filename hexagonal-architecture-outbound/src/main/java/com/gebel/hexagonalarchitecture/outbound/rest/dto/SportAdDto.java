package com.gebel.hexagonalarchitecture.outbound.rest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SportAdDto {
	
	private String id;
	private LocalDateTime expirationDate;
	private String message;

}