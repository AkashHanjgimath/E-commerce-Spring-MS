-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(100) NOT NULL
);

-- Insert users

-- User: john / password: password
INSERT INTO users (username, password, roles) VALUES (
    'john',
    '$2a$10$UZjWutePTBqravc9klUD5.AtofQFhEgvPEYLhL31DEDUepZ1/QHla',
    'ROLE_USER'
);

-- User: admin / password: admin123
INSERT INTO users (username, password, roles) VALUES (
    'admin',
    '$2a$10$eSGtpnyKjA9z6gW9UzHV9uxcfrK0uU2sdmISX6c1FwKuL6CNjBWLu',
    'ROLE_ADMIN'
);

-- User: alice / password: alicepwd
INSERT INTO users (username, password, roles) VALUES (
    'alice',
    '$2a$10$tq5jFq.W7WXMC4bRGqU0K.4Pb9kGpYIlRuw6TEoGH6LStxdHg2DRC',
    'ROLE_USER'
);
