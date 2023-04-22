-- flight database

CREATE DATABASE flightData;

USE flightData;

CREATE TABLE flights (
    flight_id varchar(30) NOT NULL,
    origin varchar(30) NOT NULL,
    destination varchar(30) NOT NULL,
    date date NOT NULL,
    time time NOT NULL,
    price int NOT NULL,
    seat int NOT NULL,
    PRIMARY KEY(flight_id)
);

CREATE TABLE users (
    username varchar(40) NOT NULL PRIMARY KEY,
    password varchar(40) NOT NULL,
    balance int NOT NULL
);

CREATE TABLE tickets (
    ticket_id varchar(40) NOT NULL PRIMARY KEY,
    flightId_ti varchar(30) NOT NULL,
    username_ti varchar(40) NOT NULL,
    FOREIGN KEY(flightId_ti) REFERENCES flights(flight_id) ON DELETE CASCADE,
    FOREIGN KEY(username_ti) REFERENCES users(username) ON DELETE CASCADE
);

INSERT INTO flights VALUES('FA-17', 'Yazd', 'Shiraz', '1401-12-10', '12:30', 500000, 73);
INSERT INTO flights VALUES('TA-55', 'Yazd', 'Isfahan', '1401-10-10', '13:30', 600000, 100);
INSERT INTO flights VALUES('TR-64', 'Shiraz', 'Mashhad', '1401-11-10', '14:30', 700000, 201);
INSERT INTO flights VALUES('WX-12', 'Mashhad', 'Tehran', '1401-12-15', '15:30', 700000, 64);
INSERT INTO flights VALUES('WX-15', 'Tehran', 'Ahvaz', '1402-01-11', '17:00', 900000, 245);
INSERT INTO flights VALUES('WX-17', 'Yazd', 'Tehran', '1402-02-10', '21:30', 1000000, 72);
INSERT INTO flights VALUES('BG-22', 'Semnan', 'Tabriz', '1402-05-12', '23:30', 1100000, 63);
INSERT INTO flights VALUES('WA-25', 'Tabriz', 'Mashhad', '1402-04-10', '16:30', 1200000, 45);
INSERT INTO flights VALUES('WA-27', 'Semnan', 'Yazd', '1402-06-10', '17:30', 1500000, 82);
INSERT INTO flights VALUES('FS-12', 'Mashhad', 'Ahvaz', '1401-12-20', '22:30', 1700000, 12);

INSERT INTO users VALUES('Melodiw82', 'Zara72', 7000000);

INSERT INTO tickets VALUES('WX-120', 'WX-12', 'Melodiw82');

COMMIT;

SELECT * FROM flights
ORDER BY date;
SELECT * FROM users
ORDER BY balance;
SELECT * FROM tickets
order by ticket_id;