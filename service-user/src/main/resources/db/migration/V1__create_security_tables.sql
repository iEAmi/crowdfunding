CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR(128) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled  BOOLEAN       NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities
(
    username  VARCHAR(128) NOT NULL,
    authority VARCHAR(128) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_authorities_username_authority
    ON authorities (username, authority);
