package com.gebel.hexagonalarchitecture.inbound.scheduled.adapter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gebel.hexagonalarchitecture.hexagon.domain.DataReport;
import com.gebel.hexagonalarchitecture.hexagon.port.inbound.DataReportServicePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrintDataReportInLogs {

	private final DataReportServicePort dataReportServicePort;
	
	@Scheduled(cron = "0 0 18 * * *") // Every day at 18h.
	public void scheduledPrintDataReportInLogs() {
		try {
			printDataDataInLogs();
		}
		catch (Exception e) {
			LOGGER.error("Error during the execution of the scheduled task " + PrintDataReportInLogs.class.getSimpleName(), e);
		}
	}
	
	private void printDataDataInLogs() {
		DataReport dataReport = dataReportServicePort.generateDataReport();
		LOGGER.info("#######################");
		LOGGER.info("Data report:");
		LOGGER.info("    Total colors: {}", dataReport.getColorsCount());
		LOGGER.info("    Total drivers: {}", dataReport.getDriversCount());
		LOGGER.info("    Total cars: {}", dataReport.getCarsCount());
		LOGGER.info("    Total brands: {}", dataReport.getBrandsCount());
		LOGGER.info("#######################");
	}
	
}