DROP TABLE IF EXISTS app_user_trips;
DROP TABLE IF EXISTS trip;
DROP TABLE IF EXISTS guide;
DROP TABLE IF EXISTS confirmation_token;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS destination;

CREATE TABLE destination (
    id SERIAL PRIMARY KEY,
    destination VARCHAR (50) NOT NULL
);

CREATE TABLE guide (
     id SERIAL PRIMARY KEY,
     first_name VARCHAR (50) NOT NULL,
     last_name VARCHAR (50) NOT NULL
);

CREATE TABLE trip (
    id SERIAL PRIMARY KEY,
    price DECIMAL,
    departure_date DATE,
    return_date DATE,
    destination_id BIGINT,
    guide_id BIGINT,
    CONSTRAINT fk_destination FOREIGN KEY (destination_id)
        REFERENCES destination (id),
    CONSTRAINT fk_guide FOREIGN KEY (guide_id)
        REFERENCES guide (id)
);

CREATE TABLE app_user (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    passport_number VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    app_user_role VARCHAR(255) NOT NULL,
    locked BOOLEAN NOT NULL DEFAULT false,
    enabled BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE confirmation_token
(
    id           SERIAL PRIMARY KEY,
    token        VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    expires_at   TIMESTAMP    NOT NULL,
    confirmed_at TIMESTAMP,
    app_user_id  BIGINT       NOT NULL,
    CONSTRAINT fk_app_user FOREIGN KEY (app_user_id)
        REFERENCES app_user (id)
);

CREATE TABLE app_user_trips (
    trip_id BIGINT,
    app_user_id BIGINT,
    CONSTRAINT pk_trip_app_user PRIMARY KEY (trip_id, app_user_id),
    CONSTRAINT fk_trip FOREIGN KEY (trip_id)
        REFERENCES trip(id),
    CONSTRAINT fk_app_user FOREIGN KEY (app_user_id)
        REFERENCES app_user(id)
);


