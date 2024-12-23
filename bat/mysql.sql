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