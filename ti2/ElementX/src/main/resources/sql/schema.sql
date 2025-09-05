CREATE TABLE powerpuff_girls (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    superpower VARCHAR(100) NOT NULL,
    age INT NOT NULL
);

INSERT INTO powerpuff_girls (name, superpower, age) VALUES
('Blossom', 'Ice Breath', 6),
('Bubbles', 'Flight & Sound Powers', 6),
('Buttercup', 'Super Strength', 6);
