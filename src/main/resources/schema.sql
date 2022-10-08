CREATE TABLE GENRE
(
   ID          INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   NAME        VARCHAR(45) NOT NULL UNIQUE,
   IMAGE       VARCHAR(45) DEFAULT 'genre image',
   CONSTRAINT  PK_GENRE PRIMARY KEY(ID)
);

CREATE TABLE STAR
(
   ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
   NAME        VARCHAR(45) NOT NULL UNIQUE,
   AGE         SMALLINT NOT NULL,
   WEIGTH      FLOAT NOT NULL,
   IMAGE       VARCHAR(45) DEFAULT 'character image',
   HISTORY     VARCHAR(45),
   CONSTRAINT  PK_STAR PRIMARY KEY(ID)
);

CREATE TABLE CONTENT
(
   ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
   TITLE       VARCHAR(60) NOT NULL UNIQUE,
   CDATE       DATE NOT NULL,
   RATING      ENUM('1','2','3','4','5') NOT NULL,
   IMAGE       VARCHAR(45) DEFAULT 'content image',
   GENRE_ID    INT NOT NULL,
   CONSTRAINT  PK_CONTENT PRIMARY KEY(ID),
   CONSTRAINT  FK_GENRE FOREIGN KEY(GENRE_ID) REFERENCES GENRE(ID) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE STAR_CONTENT
(
   ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
   STAR_ID     BIGINT NOT NULL,
   CONTENT_ID  BIGINT NOT NULL,
   CONSTRAINT  PK_STAR_CONTENT PRIMARY KEY(ID),
   CONSTRAINT  FK1_STAR    FOREIGN KEY(STAR_ID) REFERENCES STAR(ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT  FK2_CONTENT FOREIGN KEY(CONTENT_ID) REFERENCES CONTENT(ID) ON DELETE CASCADE ON UPDATE CASCADE
);


