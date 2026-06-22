package com.benchmark.runner;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportPrinter {

    public void printReports(List<Report> reports) {
        System.out.printf("%-15s | %-10s | %-15s | %-12s | %-15s%n", "Database", "Batch Size", "Time (sec)", "Records/sec", "Mem Used (MB)");
        System.out.println("--------------------------------------------------------------------------------");
        for (Report r : reports) {
            System.out.printf("%-15s | %-10d | %-15.2f | %-12.2f | %-15.2f%n", 
                    r.database, r.batchSize, r.timeSeconds, r.recordsPerSecond, r.memoryUsedMb);
        }
    }
}
