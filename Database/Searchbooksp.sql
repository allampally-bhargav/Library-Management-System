USE LMS;
DROP PROCEDURE IF EXISTS SearchBook;

DELIMITER //

create PROCEDURE SearchBook
(
 IN aname varchar(100)
)
BEGIN

select book_author.isbn10 as isbn10,book_author.isbn13 as isbn13,books.title as title,book_author.name as author,books.total_copies as no_of_copies from book_author 
inner join books 
where book_author.isbn10=books.isbn10 and book_author.isbn13=books.isbn13 and 
( books.title like concat('%',aname,'%') or book_author.name like concat ('%',aname,'%') or book_author.isbn10 like concat ('%',aname,'%') or book_author.isbn13 like concat ('%',aname,'%') );


END //
DELIMITER ;





