--GENRE

INSERT INTO GENRE 
       (NAME) 
	   
VALUES ('Action'),
	   ('Adventure'),
	   ('Science fiction'),
	   ('Drama'),
	   ('Fantasy'),
	   ('Musical'),
	   ('Thriller'),
	   ('Terror'),
	   ('Romance'),
	   ('Documentary'),
	   ('Comedy');
	   

--STAR

INSERT INTO STAR 
		(NAME, 
		AGE, 
		WEIGHT,
		HISTORY)
		
VALUES 
		('Ariel',
		20,
        56.7,
        'Ariel the little mermaid'),
		
		('Mickey Mouse',
		14,
        30,
        'Mickey the little mouse'),
        
        ('Aladdin',
		20,
        70.0,
        'Aladdin the middle east king'),
        
		('Minnie Mouse',
		6,
        17,
        'Minnie the little mouse'),
 
        ('Donald Duck',
		18,
        30,
        'Donald the duck - quack');



--CONTENT

INSERT INTO CONTENT
		(TITLE,
        CDATE,
        RATING,
        GENRE_ID)
VALUES 	
		('Orphan''s Benefit',
		DATE '1934-08-11',
        '2',
        11),
		
	 	('Fantasia',
		DATE '1940-11-13',
        '5',
        6),

		('Three little birds',
		DATE '2022-08-11',
        '5',
        11),

	 	('Queen',
		DATE '1955-11-13',
        '2',
        6);
		
--STAR_CONTENT

INSERT INTO STAR_CONTENT
		(STAR_ID ,
        CONTENT_ID)
VALUES 	(2, 1),
	    (5, 1),
		(2, 2);


--USER
INSERT INTO USER
        (NAME,
        EMAIL,
        PASSWORD)
VALUES
        ('IGNACIO',
        'ignacio@gmail.com'
        '1234')