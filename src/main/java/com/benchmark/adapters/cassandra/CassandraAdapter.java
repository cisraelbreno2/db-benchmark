package com.benchmark.adapters.cassandra;

import com.benchmark.domain.BenchmarkData;
import com.benchmark.domain.DatabaseAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CassandraAdapter implements DatabaseAdapter {

    private final BenchmarkCassandraRepository repository;

    public CassandraAdapter(BenchmarkCassandraRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveBatch(List<BenchmarkData> dataBatch) {
        List<CassandraEntity> entities = dataBatch.stream()
                .map(d -> new CassandraEntity(d.getHash(), d.getId(), d.getSituacao(), d.getDataCriacao(), d.getPayload()))
                .collect(Collectors.toList());
        
        repository.saveAll(entities);
    }
}
