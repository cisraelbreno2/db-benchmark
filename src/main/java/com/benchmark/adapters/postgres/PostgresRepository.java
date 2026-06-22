package com.benchmark.adapters.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresRepository extends JpaRepository<PostgresEntity, String> {
}
