DROP TABLE IF EXISTS trip_client;
DROP TABLE IF EXISTS trip;
DROP TABLE IF EXISTS tour_guide;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS destination;

CREATE TABLE destination (
    id SERIAL PRIMARY KEY,
    destination VARCHAR (50) NOT NULL
);

CREATE TABLE tour_guide (
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
    tour_guide_id BIGINT,
    CONSTRAINT fk_destination FOREIGN KEY (destination_id)
        REFERENCES destination (id),
    CONSTRAINT fk_tour_guide FOREIGN KEY (tour_guide_id)
        REFERENCES tour_guide (id)
);

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    passport_number VARCHAR(50) NOT NULL
);

CREATE TABLE trip_client (
    trip_id BIGINT,
    client_id BIGINT,
    CONSTRAINT pk_trip_client PRIMARY KEY (trip_id, client_id),
    CONSTRAINT fk_trip FOREIGN KEY (trip_id)
        REFERENCES trip(id),
    CONSTRAINT fk_client FOREIGN KEY (client_id)
        REFERENCES client(id)
);
