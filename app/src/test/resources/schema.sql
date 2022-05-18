CREATE TABLE user (
	id SERIAL PRIMARY KEY,
	username varchar(255) NOT NULL UNIQUE,
	firstname varchar(255) NOT NULL,
	lastname varchar(255) NOT NULL,
	phonenum varchar(64) NOT NULL UNIQUE,
	emailaddress varchar(255) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE listings (
	id SERIAL PRIMARY KEY,
	userid BIGINT UNSIGNED,
	addressid BIGINT UNSIGNED,
	term ENUM('monthly', 'yearly', 'weekly') NOT NULL,
	startdate DATETIME NOT NULL,
	price int NOT NULL,
	description TEXT
);

CREATE TABLE listing_address(
	id SERIAL PRIMARY KEY,
	streetname varchar(255) NOT NULL,
	state varchar(255) NOT NULL,
	zipcode varchar(64) NOT NULL,
	latitude float NOT NULL,
	longitude float NOT NULL
);
