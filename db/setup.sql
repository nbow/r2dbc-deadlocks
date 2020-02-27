CREATE DATABASE Deadlocks;
GO
USE Deadlocks;
GO
CREATE TABLE dbo.Elements (
    id   INTEGER      NOT NULL,
    data VARCHAR(128) NOT NULL,
    new_data VARCHAR(128) NULL,
    PRIMARY KEY (id)
);
GO