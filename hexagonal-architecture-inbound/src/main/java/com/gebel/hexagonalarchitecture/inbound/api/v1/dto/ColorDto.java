package com.gebel.hexagonalarchitecture.inbound.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto {

	@Schema(required = true)
	private String id;
	
	@Schema(required = true, example = "#000000")
	private String hexaCode;
	
}