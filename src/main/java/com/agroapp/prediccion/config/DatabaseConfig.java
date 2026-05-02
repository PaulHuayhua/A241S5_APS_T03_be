package com.agroapp.prediccion.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.agroapp.prediccion")
@EntityScan(basePackages = "com.agroapp.prediccion")
@EnableTransactionManagement
public class DatabaseConfig {
}