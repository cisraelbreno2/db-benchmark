package com.benchmark.adapters.postgres;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.Instant;

@Entity
@Table(name = "controle_lotes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostgresEntity {
    @Id
    private String id;
    private String hash;
    private String situacao;
    private Instant dataCriacao;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String payload;
}
