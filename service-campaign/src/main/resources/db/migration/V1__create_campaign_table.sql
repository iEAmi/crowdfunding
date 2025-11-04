CREATE SEQUENCE IF NOT EXISTS campaign_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS campaigns
(
    id BIGINT PRIMARY KEY DEFAULT nextval('campaign_sequence'),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    target_amount_rials BIGINT NOT NULL,
    current_amount_rials BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    amount_rials_reached_at TIMESTAMP WITHOUT TIME ZONE,
    version BIGINT
);
