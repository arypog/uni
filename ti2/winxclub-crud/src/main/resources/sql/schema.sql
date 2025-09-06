CREATE TABLE IF NOT EXISTS fairies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    power VARCHAR(100) NOT NULL,
    age INT NOT NULL
);

-- Insert Winx Club characters
INSERT INTO fairies (name, power, age) VALUES
('Bloom', 'Dragon Flame', 16),
('Stella', 'Shining Sun', 17),
('Flora', 'Nature & Plants', 16),
('Musa', 'Music & Sound Waves', 16),
('Tecna', 'Technology & Logic', 16),
('Aisha (Layla)', 'Morphix & Fluids', 17),
('Roxy', 'Animals & Nature Connection', 15);