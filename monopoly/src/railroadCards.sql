-- !!!!!!!! write your database name, your user name and your password here !!!!!!!!
USE database_name_here;

DROP TABLE IF EXISTS railroad_cards;

CREATE TABLE railroad_cards
(
    card_name VARCHAR(50),
    card_img_name VARCHAR(50),
    cost INTEGER,
    one_railroad_rent INTEGER,
    two_railroad_rent INTEGER,
    three_railroad_rent INTEGER,
    four_railroad_rent INTEGER,
    mortgage_value INTEGER
);

INSERT INTO railroad_cards VALUES
    ('READING RAILROAD', 'reading-railroad.png', 200, 25, 50, 100, 200, 100),
    ('PENNSYLVANIA RAILROAD', 'pennsylvania-railroad.png', 200, 25, 50, 100, 200, 100),
    ('B. & O. RAILROAD', 'b&o-railroad.png', 200, 25, 50, 100, 200, 100),
    ('SHORT LINE', 'short-line.png', 200, 25, 50, 100, 200, 100);