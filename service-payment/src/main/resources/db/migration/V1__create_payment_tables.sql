CREATE SEQUENCE IF NOT EXISTS donation_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS transaction_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS donations
(
    id BIGINT PRIMARY KEY DEFAULT nextval('donation_sequence'),
    amount_rials BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions
(
    id BIGINT PRIMARY KEY DEFAULT nextval('transaction_sequence'),
    amount_rials BIGINT NOT NULL,
    username VARCHAR(255) NOT NULL,
    unique_identifier VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    version BIGINT NOT NULL
);
