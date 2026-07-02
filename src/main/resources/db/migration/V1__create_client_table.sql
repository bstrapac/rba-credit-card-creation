-- Flyway migration: create client table matching rba.card_creation.model.Client
CREATE TABLE client (
  oib VARCHAR(20) PRIMARY KEY,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  card_status VARCHAR(20) NOT NULL,
  CHECK (card_status IN ('PENDING','APPROVED','REJECTED'))
);
