--Excute the next line separately.
USE DMS;

--Excute each and every line statement separately.
CREATE VIEW StudentReport AS (
SELECT R.ReportId, R.ReporterID ,R.ReportType, SMR.reportedDate, Description 
FROM Report AS R, StudentMakesReport AS SMR
WHERE R.ReportId = SMR.ReportId and R.ReportId = SMR.ReportId 
UNION 
SELECT C.ReportId,SMR.ReporterId,C.ReportType, SMR.reportedDate, C.ClothName As Description 
FROM ClothTakeOut AS C, StudentTakesClothOut AS SMR 
WHERE C.ReportId = SMR.ClothRequestId and C.ReportId = SMR.ClothRequestId 
);

CREATE VIEW HandledReport AS (
SELECT R.ReportId, R.ReportType, handledDate FROM Report AS R, ProctorHandlesReport AS PHR
WHERE R.ReportId = PHR.ReportId 
UNION 
SELECT R.ReportId, R.ReportType, handledDate 
FROM ClothTakeOut AS R, ProctorHandlesReport AS PHR
WHERE R.ReportId = PHR.ReportId
);

CREATE VIEW ClothStudent AS (
SELECT  C.ReportId, C.ReportType, C.reportCount, C.Amount, SMR.ReportedDate, handledDate, ClothName AS ClothName, S.BuildingNumber, S.RoomNumber, SMR.ReporterId AS ReporterId
FROM clothTakeOut AS C LEFT JOIN StudentTakesClothOut AS SMR ON C.ReportId = SMR.ClothRequestId
JOIN Student AS S ON S.SID = SMR.ReporterId LEFT JOIN ProctorHandlesReport AS PHR ON C.ReportId = PHR.ReportId
);

CREATE VIEW AllReports AS (
SELECT R.ReportId, R.ReportType,SMR.ReportedDate, handledDate, Description, R.BuildingNumber, R.RoomNumber, SMR.SID AS ReporterId
FROM Report AS R LEFT JOIN  StudentMakesReport AS SMR ON R.ReportId = SMR.ReportId 
LEFT JOIN ProctorHandlesReport AS PHR ON R.ReportId = PHR.ReportId
UNION 
SELECT  C.ReportId, C.ReportType,SMR.ReportedDate, handledDate, ClothName AS ClothName, S.BuildingNumber, S.RoomNumber, SMR.ReporterId AS ReporterId
FROM clothTakeOut AS C LEFT JOIN StudentTakesClothOut AS SMR ON C.ReportId = SMR.ClothRequestId
JOIN Student AS S ON S.SID = SMR.ReporterId LEFT JOIN ProctorApprovesClothTakeOut AS PHR ON C.ReportId = PHR.clothReportId
);

CREATE VIEW DormAndStudentNo AS (
SELECT D.BuildingNumber, D.RoomNumber, maxCapacity,lockers,dormType, COUNT(SID) AS NumberOfStudents 
FROM DORM AS D LEFT JOIN Student AS S ON D.BuildingNumber = S.BuildingNumber AND D.RoomNumber = S.RoomNumber
GROUP BY D.BuildingNumber,D.RoomNumber, maxCapacity, lockers, dormType
);

CREATE VIEW AvailableDorm AS(
SELECT BuildingNumber, RoomNumber, lockers, dormType, maxCapacity,NumberOfStudents FROM DormAndStudentNo 
WHERE NumberOfStudents < maxCapacity AND lockers>0
);

CREATE VIEW DormRequests AS (
SELECT * FROM AllReports WHERE ReportType='RequestForNewDorm' AND handledDate is null
);

CREATE VIEW ProctorBuilding AS (
SELECT EID, Fname, Lname, Gender, password, PS.BuildingNumber FROM Proctor LEFT JOIN ProctorSchedule PS ON EID = PID
)