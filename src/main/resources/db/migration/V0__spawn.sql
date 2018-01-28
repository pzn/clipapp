CREATE TABLE clipurl (
  id SERIAL NOT NULL,
  long_url VARCHAR(2500) NOT NULL
);
CREATE INDEX ON clipurl (long_url);
