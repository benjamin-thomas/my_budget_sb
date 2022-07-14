CREATE TABLE tenants (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY
  , email TEXT NOT NULL UNIQUE CHECK (email <> '')
  , created_at TIMESTAMPTZ NOT NULL DEFAULT current_timestamp
);
