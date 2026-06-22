package com.benchmark.domain;

import java.util.List;

public interface DatabaseAdapter {
    void saveBatch(List<BenchmarkData> dataBatch);
}
