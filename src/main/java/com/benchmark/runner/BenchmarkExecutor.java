package com.benchmark.runner;

import com.benchmark.domain.BenchmarkData;
import com.benchmark.domain.DatabaseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Component
public class BenchmarkExecutor {

    private static final Logger log = LoggerFactory.getLogger(BenchmarkExecutor.class);

    private final BenchmarkDataGenerator dataGenerator;
    private final SystemMonitor systemMonitor;

    public BenchmarkExecutor(BenchmarkDataGenerator dataGenerator, SystemMonitor systemMonitor) {
        this.dataGenerator = dataGenerator;
        this.systemMonitor = systemMonitor;
    }

    public Report execute(DatabaseAdapter adapter, String dbName, int batchSize, int totalRecords, int maxConcurrentConnections) throws InterruptedException {
        int totalBatches = totalRecords / batchSize;
        log.info(">>> Scenario: {} com um Batch Size = {}", dbName, batchSize);

        long startMem = systemMonitor.getUsedMemory();
        double startCpu = systemMonitor.getCpuLoad();
        
        long startTotal = System.nanoTime();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Semaphore semaphore = new Semaphore(maxConcurrentConnections);
            CountDownLatch latch = new CountDownLatch(totalBatches);

            for (int batch = 1; batch <= totalBatches; batch++) {
                final int currentBatch = batch;

                executor.submit(() -> {
                    try {
                        semaphore.acquire();
                        
                        List<BenchmarkData> dataBatch = dataGenerator.generateBatch(batchSize);

                        adapter.saveBatch(dataBatch);
                        
                        if (currentBatch % (Math.max(1, totalBatches / 20)) == 0) {
                            log.info("Processed {}/{} ({}%)", currentBatch, totalBatches, (currentBatch * 100) / totalBatches);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("Batch {} interrupted", currentBatch);
                    } catch (Exception e) {
                        log.error("Error inserting batch {}", currentBatch, e);
                    } finally {
                        semaphore.release();
                        latch.countDown();
                    }
                });
            }
            latch.await();
        }

        long endTotal = System.nanoTime();
        double timeSeconds = (endTotal - startTotal) / 1_000_000_000.0;
        
        long endMem = systemMonitor.getUsedMemory();
        double endCpu = systemMonitor.getCpuLoad();

        double memUsedMb = systemMonitor.calculateMemoryUsedMb(startMem, endMem);
        double recordsPerSec = totalRecords / timeSeconds;

        log.info("<<< Finished {} Batch={} | Time: {}s | Rec/s: {}", dbName, batchSize, String.format("%.2f", timeSeconds), String.format("%.2f", recordsPerSec));

        return new Report(dbName, batchSize, timeSeconds, recordsPerSec, memUsedMb, startCpu, endCpu);
    }
}
