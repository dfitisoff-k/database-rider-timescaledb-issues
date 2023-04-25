CREATE TABLE test (
    id BIGINT NOT NULL,
    name VARCHAR NOT NULL,
    birth_date TIMESTAMP NOT NULL
);

select create_hypertable('test', 'birth_date', chunk_time_interval => INTERVAL '1 year', if_not_exists := true)