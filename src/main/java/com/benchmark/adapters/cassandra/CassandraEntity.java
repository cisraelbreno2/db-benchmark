package com.benchmark.adapters.cassandra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.annotation.processing.Generated;
import java.time.Instant;

@Table("controle_lotes")
@Getter
@Setter
@AllArgsConstructor
public class CassandraEntity {
    @PrimaryKey
    private String id;
    private String hash;
    private String situacao;
    private Instant dataCriacao;
    private String payload;

}
