package com.benchmark.adapters.postgres;

import com.benchmark.domain.BenchmarkData;
import com.benchmark.domain.DatabaseAdapter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostgresAdapter implements DatabaseAdapter {

    private final PostgresRepository repository;

    public PostgresAdapter(PostgresRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveBatch(List<BenchmarkData> dataBatch) {
        List<PostgresEntity> entities = dataBatch.stream()
                .map(d -> new PostgresEntity(d.getId(), d.getHash(), d.getSituacao(), d.getDataCriacao(), d.getPayload()))
                .collect(Collectors.toList());
        repository.saveAll(entities);
    }
}
