INSERT INTO destination (destination) VALUES ('Paris'), ('New York'), ('Tokyo');

INSERT INTO tour_guide (first_name, last_name) VALUES ('John', 'Doe'), ('Jane', 'Smith');

INSERT INTO trip (price, departure_date, return_date, destination_id, tour_guide_id) VALUES
    (1000.00, '2023-03-01', '2023-03-07', 1, 1),
    (1500.00, '2023-04-01', '2023-04-07', 2, 2),
    (2000.00, '2023-05-01', '2023-05-07', 3, 1);

INSERT INTO client (first_name, last_name, passport_number) VALUES ('Alice', 'Jones', '123456'), ('Bob', 'Smith', '789012');

INSERT INTO trip_client (trip_id, client_id) VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 1),
    (3, 2);