USE database_name_here;

drop table if exists Leaderboard;
drop table if exists Accounts;

create table Leaderboard(username varchar(64), moneyWon int, timeswon int);

insert into Leaderboard values('a1', 630, 3);
insert into Leaderboard values('a2', 3210, 2);
insert into Leaderboard values('a3', 60, 5);
insert into Leaderboard values('a7', 6330, 4131);
insert into Leaderboard values('a313', 41410, 32);
insert into Leaderboard values('a123', 4130, 415);
insert into Leaderboard values('a542', 1230, 5262);
insert into Leaderboard values('a634', 120, 5632);
insert into Leaderboard values('a1341', 5320, 3132);
insert into Leaderboard values('a5414', 3280, 1273);
insert into Leaderboard values('a563', 50000, 12318);
insert into Leaderboard values('a728', 17000, 132);
insert into Leaderboard values('a316', 12310, 422);

create table Accounts(username varchar(64), password varchar(64));





