package com.benchmark.runner;

import com.benchmark.domain.BenchmarkData;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BenchmarkDataGenerator {

    private final Faker faker;

    private static final String JSON_TEMPLATE = "{" +
            "\"firstName\": \"%s\"," +
            "\"lastName\": \"%s\"," +
            "\"address\": \"%s\"," +
            "\"email\": \"%s\"," +
            "\"phone\": \"%s\"," +
            "\"jobTitle\": \"%s\"," +
            "\"company\": \"%s\"," +
            "\"notes\": \"%s\"" +
            "}";

    public BenchmarkDataGenerator() {
        this.faker = new Faker();
    }

    public List<BenchmarkData> generateBatch(int size) {
        List<BenchmarkData> dataBatch = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            BenchmarkData data = new BenchmarkData();
            data.setId(UUID.randomUUID().toString());
            data.setHash(UUID.randomUUID().toString().replace("-", ""));
            data.setSituacao(faker.options().option("PENDENTE", "PROCESSADO", "ERRO"));
            data.setDataCriacao(Instant.now());
            
            String notes = faker.lorem().characters(500, 700);
            String payload = String.format(JSON_TEMPLATE,
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.address().fullAddress(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone(),
                    faker.job().title(),
                    faker.company().name(),
                    notes
            );
            data.setPayload(payload);
            dataBatch.add(data);
        }
        return dataBatch;
    }
}
