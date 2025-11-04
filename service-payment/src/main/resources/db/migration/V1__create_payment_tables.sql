CREATE SEQUENCE IF NOT EXISTS transaction_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS transactions
(
    id                BIGINT PRIMARY KEY    DEFAULT nextval('transaction_sequence'),

    amount_rials      BIGINT       NOT NULL,
    username          VARCHAR(255) NOT NULL,
    unique_identifier VARCHAR(255) NOT NULL UNIQUE,
    description       TEXT,

    created_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version           BIGINT       NOT NULL DEFAULT 0
);
