USE LMS;
DROP TABLE IF EXISTS BOOKS_DATA;


CREATE TABLE BOOKS_DATA
(	
    ISBN10 VARCHAR(255),
    ISBN13 VARCHAR(255),
	TITLE TEXT,
    AUTHOR TEXT,
    COVER TEXT,
    PUBLISHER TEXT,
    PAGE VARCHAR(255)
);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/books.csv'
INTO TABLE BOOKS_DATA
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES ;


DROP TABLE IF EXISTS BORROWER_DATA;


CREATE TABLE BORROWER_DATA
(	
	borrower_id VARCHAR(200),
	SSN     VARCHAR(255),
    F_NAME   TEXT,
    L_NAME TEXT,
    EMAIL TEXT,
    ADDRESS TEXT,
    CITY TEXT,
    STATE TEXT,
    PHONE   VARCHAR(255)
   
);


LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/borrowers.csv'
INTO TABLE BORROWER_DATA
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n'
IGNORE 1 LINES ;




DROP TABLE IF EXISTS AUTHORS_DATA;


CREATE TABLE AUTHORS_DATA
(	
	/*AUTHOR_ID VARCHAR(255),*/
    NAME TEXT
);

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Authors.csv'
INTO TABLE AUTHORS_DATA
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\r\n'
IGNORE 0 LINES ;

CREATE TABLE BOOK_AUTHOR
(
     NAME TEXT,
     AUTHOR_ID INT,
     BOOKAUTHOR_ID INT,
     ISBN13 VARCHAR(13),
     ISBN10 VARCHAR(10)
     )