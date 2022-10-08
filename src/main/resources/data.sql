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
		WEIGTH, 
		HISTORY)
		
VALUES 
		('Ariel',
		20,
        56.7,
        'Ariel the little mermaid'),
		
		('Mickey Mouse',
		14,
        28.9,
        'Mickey the little mouse'),
        
        ('Aladdin',
		23,
        70.0,
        'Aladdin the middle east king'),
        
		('Minnie Mouse',
		6,
        17,
        'Minnie the little mouse'),
 
        ('Donald Duck',
		18,
        36,
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
        6);
		
--STAR_CONTENT

INSERT INTO STAR_CONTENT
		(STAR_ID ,
        CONTENT_ID)
VALUES 	(2, 1),
	    (5, 1),
		(2, 2);
