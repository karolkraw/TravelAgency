--liquibase formatted sql
--changeset kkrawczyk:3

CREATE TABLE guide (
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL
);