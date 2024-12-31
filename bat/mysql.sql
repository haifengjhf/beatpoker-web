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

# DROP PROCEDURE IF EXISTS user_profit_init_data;
# CREATE PROCEDURE user_profit_init_data()
# BEGIN
#     DECLARE i BIGINT DEFAULT 1;
#     WHILE i < 1000 DO
#         INSERT INTO user_profit value("i",CURRENT_DATE(),i);
#         SET i = i + 1;
#     END WHILE;
# END;
# call user_profit_init_data();