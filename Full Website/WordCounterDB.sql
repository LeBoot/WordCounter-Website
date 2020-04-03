-- Destroys existing database, if exists; this way, start from scratch each time.
DROP DATABASE IF EXISTS WordCounterDB;

-- Create database.
CREATE DATABASE WordCounterDB;

-- Ensure that the schema is using the correct database.
USE WordCounterDB;

-- Create tables.
CREATE TABLE Accounts (
	ID INT PRIMARY KEY AUTO_INCREMENT,
    Email VARCHAR(50) NOT NULL,
    Pass VARCHAR(64) NOT NULL,
    Passkey VARCHAR(200) NOT NULL
);

CREATE TABLE Texts (
	ID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(50) NOT NULL,
    Content VARCHAR(5000) NOT NULL,
    AccountID INT NOT NULL,
    FOREIGN KEY fk_Accounts_ID (AccountID) REFERENCES Accounts (ID)
);


-- TEST DATABASE ====================================================================
-- ==================================================================================

DROP DATABASE IF EXISTS WordCounterDBTest;
CREATE DATABASE WordCounterDBTest;
USE WordCounterDBTest;

CREATE TABLE Accounts (
	ID INT PRIMARY KEY AUTO_INCREMENT,
    Email VARCHAR(50) NOT NULL,
    Pass VARCHAR(64) NOT NULL,
    Passkey VARCHAR(200) NOT NULL
);

CREATE TABLE Texts (
	ID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(50) NOT NULL,
    Content VARCHAR(5000) NOT NULL,
    AccountID INT NOT NULL,
    FOREIGN KEY fk_Accounts_ID (AccountID) REFERENCES Accounts (ID)
);