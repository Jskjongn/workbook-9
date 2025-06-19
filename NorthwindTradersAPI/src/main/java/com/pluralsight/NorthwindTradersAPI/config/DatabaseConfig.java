package com.pluralsight.NorthwindTradersAPI.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private BasicDataSource basicDataSource;

    @Bean
    public DataSource dataSource() {
        return basicDataSource;
    }

    public DatabaseConfig(@Value("${datasource.url}") String url) {

        // read username and password from system properties — these were passed as command-line args.
        String username = System.getProperty("dbUsername");
        String password = System.getProperty("dbPassword");

        // build the BasicDataSource
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
    }
}
