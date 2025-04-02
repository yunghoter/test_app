CREATE TABLE tariffs (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    call_rate DECIMAL(10,2) NOT NULL,
    sms_rate DECIMAL(10,2) NOT NULL
);

CREATE TABLE subscribers (
    id SERIAL PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    balance DECIMAL(10,2) NOT NULL,
    tariff_id INTEGER REFERENCES tariffs(id)
);