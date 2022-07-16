CREATE TABLE signup_token (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY
  , tenant_id INT NOT NULL REFERENCES tenant(id) UNIQUE
  , token UUID NOT NULL
  , created_on TIMESTAMPTZ NOT NULL DEFAULT current_timestamp
);
