package com.gebel.hexagonalarchitecture.inbound.jmx;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

@Configuration
@EnableMBeanExport(defaultDomain = "hexagonalarchitecture", registration = RegistrationPolicy.REPLACE_EXISTING)
public class JmxConfiguration {

}