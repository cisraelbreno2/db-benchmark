package com.benchmark.adapters.mongo;

import com.benchmark.domain.BenchmarkData;
import com.benchmark.domain.DatabaseAdapter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoAdapter implements DatabaseAdapter {

    private final BenchmarkMongoRepository repository;

    public MongoAdapter(BenchmarkMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveBatch(List<BenchmarkData> dataBatch) {
        List<MongoEntity> entities = dataBatch.stream()
                .map(d -> new MongoEntity(d.getId(), d.getHash(), d.getSituacao(), d.getDataCriacao(), d.getPayload()))
                .collect(Collectors.toList());
        // insertAll is much faster than saveAll for MongoDB batch inserts
        repository.insert(entities);
    }
}
