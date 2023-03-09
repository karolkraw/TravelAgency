--liquibase formatted sql
--changeset kkrawczyk:1

DROP TABLE IF EXISTS app_user_trips;
DROP TABLE IF EXISTS trip;
DROP TABLE IF EXISTS guide;
DROP TABLE IF EXISTS confirmation_token;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS destination;