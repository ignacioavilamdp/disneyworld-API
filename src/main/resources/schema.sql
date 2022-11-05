CREATE TABLE GENRE
(
   ID          INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   NAME        VARCHAR(45) NOT NULL UNIQUE,
   IMAGE       VARCHAR(45),
   CONSTRAINT  PK_GENRE PRIMARY KEY(ID)
);

CREATE TABLE STAR
(
   ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
   NAME        VARCHAR(45) NOT NULL UNIQUE,
   AGE         SMALLINT DEFAULT 0,
   WEIGHT      FLOAT DEFAULT 0,
   IMAGE       VARCHAR(45),
   HISTORY     VARCHAR(45),
   CONSTRAINT  PK_STAR PRIMARY KEY(ID)
);

CREATE TABLE CONTENT
(
   ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
   TITLE       VARCHAR(60) NOT NULL UNIQUE,
   CDATE       DATE,
   RATING      ENUM('R1','R2','R3','R4','R5') NOT NULL,
   IMAGE       VARCHAR(45),
   GENRE_ID    INT,
   CONSTRAINT  PK_CONTENT PRIMARY KEY(ID),
   CONSTRAINT  FK_GENRE FOREIGN KEY(GENRE_ID) REFERENCES GENRE(ID) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE STAR_CONTENT
(
   STAR_ID     BIGINT NOT NULL,
   CONTENT_ID  BIGINT NOT NULL,
   CONSTRAINT  PK_STAR_CONTENT PRIMARY KEY(STAR_ID, CONTENT_ID),
   CONSTRAINT  FK1_STAR    FOREIGN KEY(STAR_ID) REFERENCES STAR(ID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT  FK2_CONTENT FOREIGN KEY(CONTENT_ID) REFERENCES CONTENT(ID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE APPUSER(
    ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
    NAME        VARCHAR(255) NOT NULL UNIQUE,
    EMAIL       VARCHAR(60) NOT NULL UNIQUE,
    PASSWORD    VARCHAR(255) NOT NULL,
    ROLE        ENUM('ROLE_ADMIN','ROLE_USER') NOT NULL,
    CONSTRAINT  PK_USER PRIMARY KEY(ID)
);


