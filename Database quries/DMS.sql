--Execute the next line separately 
CREATE DATABASE DMS;

--Excute the next line separately
USE DMS;

CREATE TABLE Building(BuildingNumber varchar(20) primary key);

CREATE TABLE Dorm(BuildingNumber varchar(20) not null  foreign key references Building(BuildingNumber) ,RoomNumber varchar(20) not null, 
lockers  int, StudyTable int, chair int, BedNumber int, keyHolder varchar(20), maxCapacity int not null, dormType varchar(1), primary key(BuildingNumber, RoomNumber));

CREATE TABLE Student(SID varchar(20) primary key,Fname varchar(20), Lname varchar(20), Department varchar(20), Gender varchar(1) check(Gender in ('M','F')), 
pillow bit, BedBase bit, Matress bit, isEligible bit,place varchar(20), Year int, password varchar(25), RoomNumber varchar(20) , BuildingNumber varchar(20), 
foreign key (BuildingNumber, RoomNumber) references Dorm(BuildingNumber, RoomNumber));

ALTER TABLE Dorm ADD CONSTRAINT DFK foreign key(keyHolder) references Student(SID);

CREATE TABLE Report(ReportId int IDENTITY primary key,
ReporterID varchar(20) foreign key references Student(SID), 
ReportType varchar(25), Description varchar(200), 
RoomNumber varchar(20), 
BuildingNumber varchar(20), foreign key( BuildingNumber,RoomNumber) references Dorm(BuildingNumber,RoomNumber));

CREATE TABLE Proctor(EID varchar(25) primary key, Fname varchar(20), Lname varchar(20), 
Gender varchar(1) check(Gender in('M','F')), password varchar(25))

CREATE TABLE Stock(BuildingNumber varchar(20), RoomNumber varchar(20), TotalMatress int, TotalPillow int, TotalMatressBase int)

CREATE TABLE Bathroom(BuildingNumber varchar(20) foreign key references Building(BuildingNumber), RoomNumber varchar(20), 
shower int, toilet int, 
primary key(BuildingNumber, RoomNumber)) 

CREATE TABLE Proctor_Phonenumber(EID varchar(25) foreign key references Proctor(EID), PhoneNumber int)

CREATE TABLE Student_Phonenumber(SID varchar(20) foreign key references Student(SID), PhoneNUmber int)


CREATE TABLE StudentMakesReport(SID varchar(20) foreign key references Student(SID), 
ReportId int foreign key references Report(ReportId),reportedDate date)

CREATE TABLE ProctorHandlesReport(EID varchar(25) foreign key references Proctor(EID), ReportId int foreign key references Report(ReportId), handledDate date)

CREATE TABLE ClothTakeOut(ReportId int IDENTITY primary key, reportCount int, reportType varchar(25), ClothName varchar(30), Amount int)

CREATE TABLE StudentTakesClothOut(ReporterId varchar(20) foreign key references Student(SID), ClothRequestId int foreign key references ClothTakeOut(ReportId), reportedDate date)

CREATE TABLE ProctorSchedule(id int identity primary key ,FromDate date, ToDate date, PID varchar(25),BuildingNumber varchar(20),
foreign key(PID) references Proctor(EID),foreign key(BuildingNumber) references Building)

CREATE TABLE ProctorApprovesClothTakeOut(EID varchar(25) foreign key references Proctor(EID), clothReportId int references ClothTakeOut(reportId), handledDate date) 

CREATE TABLE EmergencyContact(SID varchar(20) foreign key references Student(SID), Fname varchar(20), Lname varchar(20), Place varchar(40), PhoneNumber varchar(20),
relation varchar(20), Gender varchar(1) check(Gender in ('M','F')))

CREATE TABLE ProctorControlsStock(EID varchar(25)  foreign key references Proctor(EID), ActionType varchar(100), actionDate date, buildingNumber varchar(20)
foreign key references Building(buildingNumber))