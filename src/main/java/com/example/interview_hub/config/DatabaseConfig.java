package com.example.interview_hub.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Autowired
    private DataSource dataSource;

    @PreDestroy
    public void closeDataSource() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
            System.out.println("✅ HikariDataSource shut down gracefully.");
        }
    }
}
