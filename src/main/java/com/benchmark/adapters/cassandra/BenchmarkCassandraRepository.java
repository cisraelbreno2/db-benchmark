package com.benchmark.adapters.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenchmarkCassandraRepository extends CassandraRepository<CassandraEntity, String> {
}
