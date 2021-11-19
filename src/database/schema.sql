-- Naming Conventions
-- plural for table names
-- use PascalCase for both table and column names

CREATE TABLE Users (
    UserID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Forename VARCHAR(30) NOT NULL,
    Surname VARCHAR(30),
    Email VARCHAR(50) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Mobile VARCHAR(30),
    Role VARCHAR(30) NOT NULL,
    -- can't bind foreign key here since the Addresses table isn't defined yet
    AddressID INT
);

-- didn't create the tables for Hosts or Guests since there aren't any extra attributes that need to be stored, unique to either tables, within the scope of this project

CREATE TABLE Addresses (
    AddressID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    House VARCHAR(50) NOT NULL,
    Street VARCHAR(50),
    Place VARCHAR(50),
    Postcode VARCHAR(10) NOT NULL
);

-- now we can bind the foreign key
ALTER TABLE Users
ADD FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID);

CREATE TABLE Properties (
    PropertyID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(30) NOT NULL,
    Description TEXT NOT NULL,
    OfferBreakfast TINYINT(1) NOT NULL,
    UserID INT NOT NULL,
    AddressID INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (AddressID) REFERENCES Addresses(AddressID)
);

CREATE TABLE Bookings (
    UserID INT NOT NULL,
    PropertyID INT NOT NULL,
    isAccepted TINYINT(1),
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE ChargeBands (
    ChargeBandID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    StartDate Date NOT NULL,
    EndDate Date NOT NULL,
    PricePerNight DECIMAL(10,2) NOT NULL,
    ServiceCharge DECIMAL(10,2) NOT NULL,
    CleaningCharge DECIMAL(10,2) NOT NULL,
    PropertyID INT NOT NULL,
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
    HasBedLinen TINYINT(1),
    HasTowels TINYINT(1),
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
    HasHairDryer TINYINT(1),
    HasShampoo TINYINT(1),
    HasToiletPaper TINYINT(1),
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE Bathrooms (
    BathroomID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    HasToilet TINYINT(1),
    HasBath TINYINT(1),
    HasShower TINYINT(1),
    SharedWithHost TINYINT(1),
    PropertyID INT,
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE KitchenFacilities (
    PropertyID INT,
    HasRefrigerator TINYINT(1),
    HasMicrowave TINYINT(1),
    HasOven TINYINT(1),
    HasStove TINYINT(1),
    HasDishwasher TINYINT(1),
    HasTableware TINYINT(1),
    HasCookware TINYINT(1),
    HasBasicProvisions TINYINT(1),
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE LivingFacilities (
    PropertyID INT,
    HasWifi TINYINT(1),
    HasTelevision TINYINT(1),
    HasSatellite TINYINT(1),
    HasStreaming TINYINT(1),
    HasDvdPlayer TINYINT(1),
    HasBoardGames TINYINT(1),
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE UtilityFacilities (
    PropertyID INT,
    HasHeating TINYINT(1),
    HasWashingMachine TINYINT(1),
    HasDryingMachine TINYINT(1),
    HasFireExtinguisher TINYINT(1),
    HasSmokeAlarm TINYINT(1),
    HasFirstAidKit TINYINT(1),
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);

CREATE TABLE OutdoorFacilities (
    PropertyID INT,
    HasFreeOnsiteParking TINYINT(1),
    HasOnroadParking TINYINT(1),
    HasPaidCarPark TINYINT(1),
    HasPatio TINYINT(1),
    HasBarbecue TINYINT(1),
    FOREIGN KEY (PropertyID) REFERENCES Properties(PropertyID)
);