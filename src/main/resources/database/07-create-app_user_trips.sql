--liquibase formatted sql
--changeset kkrawczyk:7

CREATE TABLE app_user_trips (
    trip_id     BIGINT,
    app_user_id BIGINT,
    CONSTRAINT pk_trip_app_user PRIMARY KEY (trip_id, app_user_id),
    CONSTRAINT fk_trip FOREIGN KEY (trip_id)
        REFERENCES trip (id),
    CONSTRAINT fk_app_user FOREIGN KEY (app_user_id)
        REFERENCES app_user (id)
);