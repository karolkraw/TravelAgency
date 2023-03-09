--liquibase formatted sql
--changeset kkrawczyk:2

CREATE TABLE destination (
    id          SERIAL PRIMARY KEY,
    destination VARCHAR(50) NOT NULL
);