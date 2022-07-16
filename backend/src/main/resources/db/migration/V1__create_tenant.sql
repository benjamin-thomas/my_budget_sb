CREATE TABLE tenant (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY
  , email TEXT NOT NULL UNIQUE CHECK (email <> '')
  , password_hash TEXT NOT NULL CHECK (length(password_hash) = 60)
  , registered_on TIMESTAMPTZ NOT NULL DEFAULT current_timestamp
  , email_confirmed_on TIMESTAMPTZ NULL
);

COMMENT ON COLUMN tenant.password_hash IS 'Generated BCrypt length is 60';
