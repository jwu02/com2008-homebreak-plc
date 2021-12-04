-- Naming Conventions
-- plural for table names
-- use PascalCase for both table and column names

CREATE TABLE Users (
    UserID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Forename VARCHAR(30),
    Surname VARCHAR(30),
    Email VARCHAR(50),
    Password VARCHAR(255),
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
    Postcode VARCHAR(10)
);

-- now we can bind the foreign key
ALTER TABLE Users
ADD FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID);

CREATE TABLE Properties (
    PropertyID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(30),
    Description TEXT,
    PricePerNight DECIMAL(10,2),
    OfferBreakfast TINYINT,
    UserID INT,
    AddressID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID)
);

CREATE TABLE Bookings (
    UserID INT,
    PropertyID INT,
    StartDate date,
    EndDate date,
    IsAccepted TINYINT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE ChargeBands (
    ChargeBandID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    StartDate Date,
    EndDate Date,
    ServiceCharge DECIMAL(10,2),
    CleaningCharge DECIMAL(10,2),
    PropertyID INT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE Reviews (
    UserID INT,
    PropertyID INT,
    CleaninessScore INT,
    CommunicationScore INT,
    CheckinScore INT,
    AccuracyScore INT,
    LocationScore INT,
    ValueScore INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

-- tables for facilities
CREATE TABLE SleepingFacilities (
    PropertyID INT,
    HasBedLinen TINYINT,
    HasTowels TINYINT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE Bedrooms (
    BedroomID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Bed1 VARCHAR(20),
    Bed2 VARCHAR(20),
    PropertyID INT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE BathingFacilities (
    PropertyID INT,
    HasHairDryer TINYINT,
    HasShampoo TINYINT,
    HasToiletPaper TINYINT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE Bathrooms (
    BathroomID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    HasToilet TINYINT,
    HasBath TINYINT,
    HasShower TINYINT,
    SharedWithHost TINYINT,
    PropertyID INT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE KitchenFacilities (
    PropertyID INT,
    HasRefrigerator TINYINT,
    HasMicrowave TINYINT,
    HasOven TINYINT,
    HasStove TINYINT,
    HasDishwasher TINYINT,
    HasTableware TINYINT,
    HasCookware TINYINT,
    HasBasicProvisions TINYINT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE LivingFacilities (
    PropertyID INT,
    HasWifi TINYINT,
    HasTelevision TINYINT,
    HasSatellite TINYINT,
    HasStreaming TINYINT,
    HasDvdPlayer TINYINT,
    HasBoardGames TINYINT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE UtilityFacilities (
    PropertyID INT,
    HasHeating TINYINT,
    HasWashingMachine TINYINT,
    HasDryingMachine TINYINT,
    HasFireExtinguisher TINYINT,
    HasSmokeAlarm TINYINT,
    HasFirstAidKit TINYINT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE OutdoorFacilities (
    PropertyID INT,
    HasFreeOnsiteParking TINYINT,
    HasOnroadParking TINYINT,
    HasPaidCarPark TINYINT,
    HasPatio TINYINT,
    HasBarbecue TINYINT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);