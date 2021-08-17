-- !!!!!!!! write your database name here !!!!!!!!
USE database_name_here;

DROP TABLE IF EXISTS property_cards;

CREATE TABLE property_cards (
    card_name VARCHAR(50),
    card_img_name VARCHAR(50),
    color VARCHAR(15),
    cost INTEGER,
    default_rent INTEGER,
    color_set_rent INTEGER,
    one_house_rent INTEGER,
    two_house_rent INTEGER,
    three_house_rent INTEGER,
    four_house_rent INTEGER,
    hotel_rent INTEGER,
    mortgage_value INTEGER,
    house_cost INTEGER,
    hotel_cost INTEGER
);

INSERT INTO property_cards VALUES
    ('MEDITERRANEAN-AVENUE', 'mediterranean-avenue.png', 'brown', 60, 2, 5, 10, 30, 90, 160, 250, 30, 50, 50),
    ('BALTIC-AVENUE', 'baltic-avenue.png', 'brown', 60, 4, 8, 20, 60, 180, 320, 450, 30, 50, 50),
    ('ORIENTAL-AVENUE', 'oriental-avenue.png', 'lightblue', 100, 6, 12, 30, 90, 270, 400, 550, 50, 50, 50),
    ('VERMONT-AVENUE', 'vermont-avenue.png', 'lightblue', 100, 6, 12, 30, 90, 270, 400, 550, 50, 50, 50),
    ('CONNECTICUT-AVENUE', 'connecticut-avenue.png', 'lightblue', 120, 8, 16, 40, 100, 300, 450, 600, 60, 50, 50),
    ('ST.-CHARLES-PLACE', 'st-charles-place.png', 'pink', 140, 10, 20, 50, 150, 450, 625, 750, 70, 100, 100),
    ('STATES-AVENUE', 'states-avenue.png', 'pink', 140, 10, 20, 50, 150, 450, 625, 750, 70, 100, 100),
    ('VIRGINIA-AVENUE', 'virginia-avenue.png', 'pink', 160, 12, 24, 60, 180, 500, 700, 900, 80, 100, 100),
    ('ST.-JAMES-PLACE', 'st-james-place.png', 'orange', 180, 14, 28, 70, 200, 550, 750, 950, 90, 100, 100),
    ('TENNESSEE-AVENUE', 'tennessee-avenue.png', 'orange', 180, 14, 28, 70, 200, 550, 750, 950, 90, 100, 100),
    ('NEW-YORK-AVENUE', 'new-york-avenue.png', 'orange', 200, 16, 32, 80, 220, 600, 800, 1000, 100, 100, 100),
    ('KENTUCKY-AVENUE', 'kentucky-avenue.png', 'red', 220, 18, 36, 90, 250, 700, 875, 1050, 110, 150, 150),
    ('INDIANA-AVENUE', 'indiana-avenue.png', 'red', 220, 18, 36, 90, 250, 700, 875, 1050, 110, 150, 150),
    ('ILLINOIS-AVENUE', 'illinois-avenue.png', 'red', 240, 20, 40, 100, 300, 750, 925, 1100, 120, 150, 150),
    ('ATLANTIC-AVENUE', 'atlantic-avenue.png', 'yellow', 260, 22, 44, 110, 330, 800, 975, 1150, 130, 150, 150),
    ('VENTNOR-AVENUE', 'ventnor-avenue.png', 'yellow', 260, 22, 44, 110, 330, 800, 975, 1150, 130, 150, 150),
    ('MARVIN-GARDENS', 'marvin-gardens.png', 'yellow', 280, 24, 48, 120, 360, 850, 1025, 1200, 140, 150, 150),
    ('PACIFIC-AVENUE', 'pacific-avenue.png', 'green', 300, 26, 52, 130, 390, 900, 1100, 1275, 150, 200, 200),
    ('NORTH-CAROLINA-AVENUE', 'north-carolina-avenue.png', 'green', 300, 26, 52, 130, 390, 900, 1100, 1275, 150, 200, 200),
    ('PENNSYLVANIA-AVENUE', 'pennsylvania-avenue.png', 'green', 320, 28, 56, 150, 450, 1000, 1200, 1400, 160, 200, 200),
    ('PARK-PLACE', 'park-place.png', 'blue', 350, 35, 70, 175, 500, 1100, 1300, 1500, 175, 200, 200),
    ('BOARDWALK', 'boardwalk.png', 'blue', 400, 50, 100, 200, 600, 1400, 1700, 2000, 200, 200, 200);

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
('READING-RAILROAD', 'reading-railroad.png', 200, 25, 50, 100, 200, 100),
('PENNSYLVANIA-RAILROAD', 'pennsylvania-railroad.png', 200, 25, 50, 100, 200, 100),
('B.-&-O.-RAILROAD', 'b&o-railroad.png', 200, 25, 50, 100, 200, 100),
('SHORT-LINE', 'short-line.png', 200, 25, 50, 100, 200, 100);

DROP TABLE IF EXISTS random_event_cards;

CREATE TABLE random_event_cards
(
    card_type VARCHAR(20),
    card_image_name VARCHAR(40)
);

INSERT INTO random_event_cards VALUES
('chance', 'chance-advance-to-boardwalk.png'),
('chance', 'chance-advance-to-go.png'),
('chance', 'chance-advance-to-illinois.png'),
('chance', 'chance-advance-to-st-charles.png'),
('chance', 'chance-bank-pays-50.png'),
('chance', 'chance-building-loan-matures.png'),
('chance', 'chance-chairman.png'),
('chance', 'chance-get-out-of-jail.png'),
('chance', 'chance-go-back-3-spaces.png'),
('chance', 'chance-go-to-jail.png'),
('chance', 'chance-go-to-reading-railroad.png'),
('chance', 'chance-nearest-railroad.png'),
('chance', 'chance-nearest-railroad.png'),
('chance', 'chance-nearest-utility.png'),
('chance', 'chance-repairs.png'),
('chance', 'chance-speeding-fine.png'),
('community-chest', 'chest-advance-to-go.png'),
('community-chest', 'chest-bank-error.png'),
('community-chest', 'chest-beauty-contest.png'),
('community-chest', 'chest-birthday.png'),
('community-chest', 'chest-consultancy-fee.png'),
('community-chest', 'chest-doctors-fee.png'),
('community-chest', 'chest-get-out-of-jail.png'),
('community-chest', 'chest-go-to-jail.png'),
('community-chest', 'chest-holiday-fund-matures.png'),
('community-chest', 'chest-hospital-fees.png'),
('community-chest', 'chest-income-tax-refund.png'),
('community-chest', 'chest-inherit.png'),
('community-chest', 'chest-life-insurance-matures.png'),
('community-chest', 'chest-sale-of-stock.png'),
('community-chest', 'chest-school-fees.png'),
('community-chest', 'chest-street-repairs.png');

DROP TABLE IF EXISTS utility_cards;

CREATE TABLE utility_cards
(
    card_name VARCHAR(50),
    card_img_name VARCHAR(50),
    cost INTEGER,
    mortgage_value INTEGER
);

INSERT INTO utility_cards VALUES
('ELECTRIC-COMPANY', 'electric-company.png', 150, 75),
('WATER-WORKS', 'water-works.png', 150, 75);