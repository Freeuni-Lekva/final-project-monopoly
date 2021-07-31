-- !!!!!!!! write your database name, your user name and your password here !!!!!!!!
USE database_name_here;

DROP TABLE IF EXISTS utility_cards;

CREATE TABLE utility_cards
(
    card_name VARCHAR(50),
    card_img_name VARCHAR(50),
    cost INTEGER,
    mortgage_value INTEGER
);

INSERT INTO utility_cards VALUES
    ('ELECTRIC COMPANY', 'electric-company.png', 150, 75),
    ('WATER WORKS', 'water-works.png', 150, 75);