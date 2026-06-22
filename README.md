# db-benchmark

Comparação de inserções em massa entre **PostgreSQL**, **MongoDB** e **Cassandra**. Este projeto foi construído para simular um cenário real de alta volumetria (ex: 50 milhões de transações diárias em lote), onde o controle de estado e a rastreabilidade dos registros no banco de dados são críticos para o sistema.

O objetivo principal é avaliar empiricamente qual banco de dados oferece o melhor custo-benefício (Tempo de Execução, Footprint de Memória, CPU e Registros por Segundo) quando submetido a estresse de concorrência.

---

##  Tecnologias Utilizadas

- **Java 21**: Usado especificamente para explorar as **Virtual Threads (Project Loom)**, garantindo a máxima utilização de CPU com pouquíssimo overhead de troca de contexto de threads durante o processamento assíncrono.
- **Spring Boot 3.x**: Orquestração, Injeção de Dependências e auto-configuração dos drivers.
- **Data Faker**: Geração de cargas de dados JSON (Mock) realistas com tamanhos variáveis para forçar I/O.
- **Bancos de Dados**: PostgreSQL 16, MongoDB 7.0, Cassandra 4.1, Redis.

---

## ️ Arquitetura

Para manter o código testável, escalável e de fácil manutenção, o projeto foi construído seguindo princípios **SOLID** e **Arquitetura Limpa (Portas e Adaptadores / Hexagonal)**:

- **Interface Abstrata (`DatabaseAdapter`)**: O motor de execução (`BenchmarkExecutor`) não conhece regras específicas do Postgres ou Cassandra. Ele depende apenas de uma interface que recebe uma lista de dados.
- **Padrão Strategy**: O Spring mapeia todos os adaptadores implementados e o Runner itera sobre eles automaticamente. Adicionar um novo banco de dados no futuro (ex: MySQL) exige apenas a criação de um novo Adapter, sem alterar o motor.
- **Separação de Responsabilidades (SRP)**:
  - `BenchmarkDataGenerator`: Fabrica os payloads.
  - `SystemMonitor`: Extrai métricas do Sistema Operacional (RAM, CPU).
  - `BenchmarkExecutor`: Gerencia a concorrência via `ExecutorService` (Virtual Threads) e `Semaphore` para controle de pool.
  - `ReportPrinter`: Apresenta os dados de forma legível.

---

##  Como Executar

### 1. Subir a Infraestrutura (Bancos de Dados)
Não é necessário instalar nenhum banco na máquina host. Basta ter o Docker instalado e rodar na raiz do projeto:

```bash
docker-compose up -d
```
*Nota: O container `init-cassandra` cuidará da criação do Keyspace automaticamente após o nó estar saudável.*

### 2. Configurações (`application.yml`)
Você pode alterar os parâmetros do teste acessando o arquivo `src/main/resources/application.yml`:
```yaml
benchmark:
  total-records: 1000000 # Total de registros por cenário
  max-concurrent-connections: 80 # Semáforo para não esgotar as conexões do banco
```

### 3. Executando o Benchmark
Como o projeto está usando Java 21, certifique-se de ter a JDK correta configurada.
Execute via Maven Wrapper:
```bash
./mvnw spring-boot:run
```
*(Ou execute a classe `DbBenchmarkApplication.java` diretamente pela sua IDE)*

### 4. Resultados
O sistema gerará relatórios tanto para inserções em *Batch* (lotes) quanto em *Single* (um por um), permitindo observar como o agrupamento afeta o desempenho relacional vs NoSQL. No final, uma tabela será impressa no console com os resultados agregados!
