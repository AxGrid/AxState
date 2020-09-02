package com.axgrid.state.jdbc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = "com.axgrid.state.jdbc")
@EnableScheduling
public class AxStateJdbcConfiguration {
}
