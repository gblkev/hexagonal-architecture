package com.gebel.hexagonalarchitecture.hexagon.service.impl;

import org.springframework.stereotype.Service;

import com.gebel.hexagonalarchitecture.hexagon.domain.DataReport;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.BrandService;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.CarService;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.ColorService;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.DataReportService;
import com.gebel.hexagonalarchitecture.hexagon.service.interfaces.DriverService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DataReportServiceImpl implements DataReportService {
	
	private final ColorService colorService;
	private final DriverService driverService;
	private final CarService carService;
	private final BrandService brandService;

	@Override
	public DataReport generateDataReport() {
		long colorsCount = colorService.countColors();
		long driversCount = driverService.countDrivers();
		long carsCount = carService.countCars();
		long brandsCount = brandService.countBrands();
		return new DataReport(colorsCount, driversCount, carsCount, brandsCount);
	}

}