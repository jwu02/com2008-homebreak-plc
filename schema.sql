-- Naming Conventions
-- plural for table names
-- use PascalCase for both table and column names

CREATE TABLE Users (
    UserID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Forename VARCHAR(30),
    Surname VARCHAR(30),
    Email VARCHAR(50),
    Mobile VARCHAR(30),
    Role VARCHAR(30),
    -- can't bind foreign key here since the Addresses table isn't defined yet
    AddressID INT
);

-- didn't create the tables for Hosts or Guests since there aren't any extra attributes that need to be stored, unique to either tables, within the scope of this project

CREATE TABLE Addresses (
    AddressID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    House VARCHAR(50),
    Street VARCHAR(50),
    Place VARCHAR(50),
    Postcode VARCHAR(10),
    PropertyID INT -- *** need to bind this foreign key later after the Properties table has been created ***
);

-- now we can bind the foreign key
ALTER TABLE Users
ADD FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID);
