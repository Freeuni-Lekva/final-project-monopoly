-- !!!!!!!! write your database name, your user name and your password here !!!!!!!!
USE database_name_here;

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
