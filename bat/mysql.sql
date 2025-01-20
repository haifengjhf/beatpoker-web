CREATE DATABASE IF NOT EXISTS beatpoker;

USE beatpoker;

CREATE TABLE IF NOT EXISTS master_user_table(
    user_id VARCHAR(32) NOT NULL,
    email_address VARCHAR(32) NOT NULL ,
    password VARCHAR(32) NOT NULL,
    nick_name VARCHAR(32) NOT NULL,
    created_time DATETIME NOT NULL,
    token VARCHAR(32),
    expired_time DATETIME,
    status INT,
    PRIMARY KEY(user_id),
    UNIQUE(email_address)
);

CREATE TABLE IF NOT EXISTS user_profit_table(
    user_id VARCHAR(32) NOT NULL,
    last_update_time DATETIME,
    score BIGINT,
    PRIMARY KEY(user_id)
);

CREATE TABLE IF NOT EXISTS password_reset_table(
    email_address VARCHAR(32) NOT NULL,
    expired_time DATETIME,
    verification_code VARCHAR(32) NOT NULL,
    PRIMARY KEY(email_address)
);

DESC master_user_table;
DESC user_profit_table;
DESC password_reset_table;