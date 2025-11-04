CREATE SEQUENCE IF NOT EXISTS donation_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS donations
(
    id                BIGINT PRIMARY KEY    DEFAULT nextval('donation_sequence'),

    campaign_id       BIGINT       NOT NULL,
    amount_rials      BIGINT       NOT NULL,
    username          VARCHAR(500) NOT NULL,
    unique_identifier varchar(500) NOT NULL UNIQUE,
    status            VARCHAR(11)  NOT NULL,

    paid_at           TIMESTAMPTZ,

    created_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version           BIGINT       NOT NULL DEFAULT 0
);
