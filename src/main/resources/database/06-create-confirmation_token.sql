--liquibase formatted sql
--changeset kkrawczyk:6

CREATE TABLE confirmation_token (
    id           SERIAL PRIMARY KEY,
    token        VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    expires_at   TIMESTAMP    NOT NULL,
    confirmed_at TIMESTAMP,
    app_user_id  BIGINT       NOT NULL,
    CONSTRAINT fk_app_user FOREIGN KEY (app_user_id)
        REFERENCES app_user (id)
);