
INSERT INTO destination (destination) VALUES ('Paris'), ('New York'), ('Tokyo');


INSERT INTO guide (first_name, last_name) VALUES ('John', 'Doe'), ('Jane', 'Smith');

INSERT INTO trip (price, departure_date, return_date, destination_id, guide_id) VALUES
    (1000.00, '2023-03-01', '2023-03-07', 1, 1),
    (1500.00, '2023-04-01', '2023-04-07', 2, 2),
    (3000.00, '2023-08-01', '2023-05-07', 3, 1),
    (2000.00, '2023-05-01', '2023-05-07', 1, 1);


INSERT INTO app_user (first_name, last_name, passport_number, email, password, app_user_role, locked, enabled)
VALUES ('John', 'Doe', '123456', 'john.doe@example.com', 'password123', 'USER', false, true),
       ('Jane', 'Doe', '654321', 'jane.doe@example.com', 'password456', 'ADMIN', false, true),
       ('Bob', 'Smith', '987654', 'bob.smith@example.com', 'password789', 'USER', true, true);

INSERT INTO app_user_trips (trip_id, app_user_id) VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 1),
    (3, 2),
    (4, 3);



