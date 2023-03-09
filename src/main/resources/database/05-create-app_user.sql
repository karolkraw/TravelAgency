--liquibase formatted sql
--changeset kkrawczyk:5

CREATE TABLE app_user (
    id              SERIAL PRIMARY KEY,
    first_name      VARCHAR(50)  NOT NULL,
    last_name       VARCHAR(50)  NOT NULL,
    passport_number VARCHAR(50)  NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    app_user_role   VARCHAR(255) NOT NULL,
    locked          BOOLEAN      NOT NULL DEFAULT false,
    enabled         BOOLEAN      NOT NULL DEFAULT false
);