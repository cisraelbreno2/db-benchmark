package com.benchmark.adapters.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "controle_lotes")
@Getter
@Setter
@AllArgsConstructor
public class MongoEntity {
    @Id
    private String id;
    private String hash;
    private String situacao;
    private Instant dataCriacao;
    private String payload;
}
