package com.benchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.benchmark.adapters.postgres")
@EnableMongoRepositories(basePackages = "com.benchmark.adapters.mongo")
@EnableCassandraRepositories(basePackages = "com.benchmark.adapters.cassandra")
public class DbBenchmarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbBenchmarkApplication.class, args);
	}

}
