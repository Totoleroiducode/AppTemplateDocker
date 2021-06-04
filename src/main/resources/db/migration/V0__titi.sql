CREATE TABLE Customer (
                          id SERIAL PRIMARY KEY,
                          first_name varchar NOT NULL UNIQUE,
                          last_name varchar NOT NULL UNIQUE
);