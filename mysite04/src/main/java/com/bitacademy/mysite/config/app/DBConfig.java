package com.bitacademy.mysite.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mariadb://192.168.0.162:3306/webdb?charset=utf8\"");
		dataSource.setUsername("webdb");
		dataSource.setPassword("webdb");
		dataSource.setInitialSize(0);
		dataSource.setMaxActive(20);	// 동시접속 수 제한
		
		return dataSource;
	}
}
