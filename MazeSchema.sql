CREATE DATABASE IF NOT EXISTS mazerunnerdb;
USE mazerunnerdb;

CREATE TABLE mazerunnerdb.playertable (
varchar(255) primary key playerName,
int score
);

ALTER TABLE MazeRunnerDb.playertable
ALTER score SET DEFAULT 0;