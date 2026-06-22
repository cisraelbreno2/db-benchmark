package com.benchmark.runner;

import org.springframework.stereotype.Component;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Component
public class SystemMonitor {

    private final OperatingSystemMXBean osBean;

    public SystemMonitor() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
    }

    public double getCpuLoad() {
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            return ((com.sun.management.OperatingSystemMXBean) osBean).getCpuLoad() * 100;
        }
        return osBean.getSystemLoadAverage();
    }

    public long getUsedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public double calculateMemoryUsedMb(long startMem, long endMem) {
        return (endMem - startMem) / (1024.0 * 1024.0);
    }
}
