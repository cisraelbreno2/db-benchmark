package com.benchmark.runner;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Report {

    String database;
    int batchSize;
    double timeSeconds;
    double recordsPerSecond;
    double memoryUsedMb;
    double startCpu;
    double endCpu;
}
