CREATE TABLE client (
    id SERIAL PRIMARY KEY ,
    oib VARCHAR(11),
    first_name VARCHAR(150),
    last_name VARCHAR(150),
    card_status VARCHAR(20),
    CHECK (card_status IN ('PENDING','APPROVED','REJECTED'))
);
