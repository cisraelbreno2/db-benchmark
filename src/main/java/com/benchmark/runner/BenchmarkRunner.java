package com.benchmark.runner;

import com.benchmark.domain.DatabaseAdapter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BenchmarkRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BenchmarkRunner.class);

    @Value("${benchmark.total-records:50000000}")
    private int totalRecords;

    @Value("${benchmark.max-concurrent-connections:10000}")
    private int maxConcurrentConnections;

    private final List<DatabaseAdapter> adapters;
    private final BenchmarkExecutor executor;
    private final ReportPrinter printer;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting Multi-Database Benchmark for {} Records...", totalRecords);

        List<Report> reports = new ArrayList<>();

        for (DatabaseAdapter adapter : adapters) {
            String dbName = adapter.getClass().getSimpleName().replace("Adapter", "");

            log.info("Starting {}...", dbName);
            log.info("=========================================\n");

            reports.add(executor.execute(adapter, dbName, 1000, totalRecords, maxConcurrentConnections));
            
            System.gc();
            Thread.sleep(3000);

            reports.add(executor.execute(adapter, dbName, 1, totalRecords, maxConcurrentConnections));
            
            System.gc();
            Thread.sleep(3000);
        }

        printer.printReports(reports);
        
        System.exit(0);
    }
}
