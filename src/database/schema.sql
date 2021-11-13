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
    PropertyID INT
);

-- now we can bind the foreign key
ALTER TABLE Users
ADD FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID);

CREATE TABLE Properties (
    PropertyID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    PropertyName VARCHAR(30),
    Description TEXT,
    Location VARCHAR(50),
    OfferBreakfast TINYINT,
    UserID INT FOREIGN KEY REFERENCES Users(UserID)
);

ALTER TABLE Addresses
ADD FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID);

CREATE TABLE Bookings (
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    isAccepted TINYINT
);

CREATE TABLE ChargeBands (
    ChargeBandID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    StartDate Date,
    EndDate Date,
    PricePerNight DECIMAL(10,2),
    ServiceCharge DECIMAL(10,2),
    CleaningCharge DECIMAL(10,2)
);

CREATE TABLE PropertyChargeBands (
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    ChargeBandID INT FOREIGN KEY REFERENCES ChargeBands(ChargeBandID)
);

CREATE TABLE Reviews (
    UserID INT FOREIGN KEY REFERENCES Users(UserID),
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    CleaninessScore INT,
    CommunicationScore INT,
    CheckinScore INT,
    AccuracyScore INT,
    LocationScore INT,
    ValueScore INT
);

-- tables for facilities
CREATE TABLE SleepingFacilities (
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    HasBedLinen TINYINT,
    HasTowels TINYINT
);

CREATE TABLE Bedrooms (
    BedroomID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Bed1 VARCHAR(20),
    Bed2 VARCHAR(20),
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID)
);

CREATE TABLE BathingFacilities (
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    HasHairDryer TINYINT,
    HasShampoo TINYINT,
    HasToiletPaper TINYINT
);

CREATE TABLE Bathrooms (
    BathroomID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    HasToilet TINYINT,
    HasBath TINYINT,
    HasShower TINYINT,
    SharedWithHost TINYINT,
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
);

CREATE TABLE KitchenFacilities (
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    HasRefrigerator TINYINT,
    HasMicrowave TINYINT,
    HasOven TINYINT,
    HasStove TINYINT,
    HasDishwasher TINYINT,
    HasTableware TINYINT,
    HasCookware TINYINT,
    HasBasicProvisions TINYINT
);

CREATE TABLE LivingFacilities (
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    HasWifi TINYTEXT,
    HasTelevision TINYTEXT,
    HasSatellite TINYTEXT,
    HasStreaming TINYTEXT,
    HasDvdPlayer TINYTEXT,
    HasBoardGames TINYTEXT
);

CREATE TABLE UtilityFacilities (
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    HasHeating TINYTEXT,
    HasWashingMachine TINYTEXT,
    HasDryingMachine TINYTEXT,
    HasFireExtinguisher TINYTEXT,
    HasSmokeAlarm TINYTEXT,
    HasFirstAidKit TINYTEXT
);

CREATE TABLE OutdoorFacilities (
    PropertyID INT FOREIGN KEY REFERENCES Properties(PropertyID),
    HasFreeOnsiteParking TINYTEXT,
    HasOnroadParking TINYTEXT,
    HasPaidCarPark TINYTEXT,
    HasPatio TINYTEXT,
    HasBarbecue TINYTEXT
);