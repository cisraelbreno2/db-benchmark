package com.benchmark.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BenchmarkData {
    private String id;
    private String hash;
    private String situacao;
    private Instant dataCriacao;
    private String payload;
}
