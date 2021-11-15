CREATE DATABASE team021
USE team021

CREATE TABLE person(
email VARCHAR(30) PRIMARY KEY,
title VARCHAR(25),
forename VARCHAR(20),
sumname VARCHAR(20),
mobile INT(30),
roles VARCHAR(25),
addressID INT(25) REFERENCES address.`addressID`
)

CREATE TABLE address(
addressID INT(25) PRIMARY KEY,
house VARCHAR(25),
streetName VARCHAR(25),
palaceName VARCHAR(25),
postcode   VARCHAR(20)

)

CREATE TABLE Guest(
guestID INT PRIMARY KEY,
guestName  VARCHAR(30),
email VARCHAR(30) REFERENCES person.`email`

)

CREATE TABLE host1(
hostID VARCHAR(15) PRIMARY KEY,
hostName  VARCHAR(25),
email VARCHAR(30) REFERENCES person.`email`,
isSuperhost TINYINT(1)

)

CREATE TABLE Booking(
guestID INT PRIMARY KEY,
propertyID INT REFERENCES property.`propertyID`,
isAccepted TINYINT(1)

)
-- drop table Booking;
-- alter table Booking add constraint column propertyID

CREATE TABLE property(
propertyID INT PRIMARY KEY,
nam VARCHAR(25),
description VARCHAR(30),
location VARCHAR(25),
offerBreakfast TINYINT(1),
addressID INT(25) REFERENCES address.`addressID`,
maxGuests INT,
numBeds INT,
numBedrooms INT,
numbathrooms INT

)

CREATE TABLE ChargeBand(
chargeBandID INT PRIMARY KEY,
startDate DATE,
endDate DATE,
pricePerNight INT,
serviceCharge INT,
cleaningCharge INT,
proertyID INT  REFERENCES property.`propertyID`

)
CREATE TABLE Review(
reviewID INT PRIMARY KEY,
cleaninessScore INT,
communicationScore INT,
checkinScore INT,
accuracyScore INT,
locationScore INT,
valueScore INT,
guestID INT REFERENCES Guest.`guestID`,

averageScore DECIMAL(5,2)
)

CREATE TABLE OutdoorFacility(
outDoorFacilityID INT PRIMARY KEY,
hasFreeOnsiteParking TINYINT(1),
hasOnroadParking TINYINT(1),
hasPaidCarPark TINYINT(1),
hasPatio TINYINT(1),
hasBarbecue TINYINT(1),
propertyID INT REFERENCES property.`propertyID`

)

CREATE TABLE BathingFacility(
bathingFacilityID INT PRIMARY KEY,
hasHairDryer TINYINT(1),
hasShampoo TINYINT(1),
hasToiletPaper TINYINT(1),
propertyID INT REFERENCES property.`propertyID`,
numBathrooms INT

)
CREATE TABLE SleepingFacility(
sleepingFacilityID INT PRIMARY KEY,
hasBedLinen TINYINT(1),
hasTowels TINYINT(1),
propertyID INT REFERENCES property.`propertyID`,
numBedrooms INT,
numBeds INT,
numSleepers INT

)
CREATE TABLE KitchenFacility(
kitchienFacilityID INT PRIMARY KEY,
hasRefrigerator TINYINT(1),
hasMicrowave TINYINT(1),
hasOven TINYINT(1),
hasStove TINYINT(1),
hasDishwasher TINYINT(1),
hasTableware TINYINT(1),
hasCookware TINYINT(1),
hasBasicProvisions TINYINT(1),
propertyID INT REFERENCES property.`propertyID`

)
CREATE TABLE UtilityFacility(
utilityFacilityID INT PRIMARY KEY,
hasHeating TINYINT(1),
hasWashingMachine TINYINT(1),
hasDryingMachine TINYINT(1),
hasFireExtingguisher TINYINT(1),
hasSmokeAlam TINYINT(1),
hasFirstAidkit TINYINT(1),
propertyID INT REFERENCES property.`propertyID`

)

CREATE TABLE LivingFacility(
livingFacilityID INT PRIMARY KEY,
hasWifi TINYINT(1),
hasTelevision TINYINT(1),
hasSatellite TINYINT(1),
hasStreaming TINYINT(1),
hasDvdPlayer TINYINT(1),
hasBoardGames TINYINT(1),
propertyID INT REFERENCES property.`propertyID`

)

CREATE TABLE Bathroom(
bathroomID INT PRIMARY KEY,
hasTilet TINYINT(1),
hasBath TINYINT(1),
hasShower TINYINT(1),
bathroomShared TINYINT(1),
bathingFacilityID INT REFERENCES bathingFacility.`bathingFacilityID`


)
CREATE TABLE Bedroom(
bedroomID INT PRIMARY KEY,
bed1 VARCHAR(20),
bed2 VARCHAR(20),
sleepingFacilityID INT REFERENCES SleepingFacility.`sleepingFacilityID`,
numBeds INT,
maxSleepers INT

)





