package com.benchmark.adapters.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenchmarkMongoRepository extends MongoRepository<MongoEntity, String> {
}
