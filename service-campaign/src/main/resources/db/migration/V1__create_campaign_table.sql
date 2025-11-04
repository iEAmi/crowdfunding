CREATE SEQUENCE IF NOT EXISTS campaign_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS campaigns
(
    id                             BIGINT PRIMARY KEY    DEFAULT nextval('campaign_sequence'),

    name                           VARCHAR(255) NOT NULL,
    description                    TEXT,
    current_amount_rials           BIGINT       NOT NULL DEFAULT 0,

    target_amount_rials            BIGINT       NOT NULL,
    target_amount_rials_reached_at TIMESTAMPTZ,

    created_at                     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at                     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    version                        BIGINT       NOT NULL DEFAULT 0
);
