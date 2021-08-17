USE accounts;

DROP TABLE IF EXISTS friendPairs;
DROP TABLE IF EXISTS requestPairs;
DROP TABLE IF EXISTS invitations;

CREATE TABLE friendPairs(
                            username1 VARCHAR(255) NOT NULL,
                            username2 VARCHAR(255) NOT NULL
);

CREATE TABLE requestPairs(
                             username1 VARCHAR(255) NOT NULL,
                             username2 VARCHAR(255) NOT NULL
);

CREATE TABLE invitations(
                            lobby VARCHAR(255) NOT NULL,
                            username VARCHAR(255) NOT NULL,
                            inviter VARCHAR(255) NOT NULL
);