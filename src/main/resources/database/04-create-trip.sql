--liquibase formatted sql
--changeset kkrawczyk:4

CREATE TABLE trip (
    id             SERIAL PRIMARY KEY,
    price          DECIMAL,
    departure_date DATE,
    return_date    DATE,
    destination_id BIGINT,
    guide_id       BIGINT,
    CONSTRAINT fk_destination FOREIGN KEY (destination_id)
        REFERENCES destination (id),
    CONSTRAINT fk_guide FOREIGN KEY (guide_id)
        REFERENCES guide (id)
);