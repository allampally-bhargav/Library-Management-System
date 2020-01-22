USE LMS;
DROP PROCEDURE IF EXISTS InsertIntoBorrower;

DELIMITER //
CREATE PROCEDURE InsertIntoBorrower
(
   valueSSN VARCHAR(15),
   valueFName TEXT,
   valueLName TEXT,
   valueEmail TEXT,
   valueAddress TEXT,
   valueCity TEXT,
   valueState TEXT,
   valuePhone VARCHAR(20)  
)
BEGIN

  INSERT INTO BORROWER 
  ( SSN,
   FNAME,
   LNAME,
   BNAME,
   EMAIL,
   ADDRESS,
   CITY,
   STATE,
   FULL_ADDRESS,
   PHONE,
   CREATED_DATE
  )
  VALUES
  (
     valueSSN,
     valueFName,
     valueLName,
     (SELECT CONCAT(valueFname ,' ',valueLName)),
     valueEmail,
     valueAddress,
     valueCity,
     valueState,
      (SELECT CONCAT(valueAddress ,' ', valueCity ,' ',valueState)),
      valuePhone,
      NOW()
     
  );
  
  SELECT @@IDENTITY;
END //
DELIMITER ;

