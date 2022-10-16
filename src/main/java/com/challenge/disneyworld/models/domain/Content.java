package com.challenge.disneyworld.models.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONTENT")
public class Content {
    /*
        CREATE TABLE CONTENT
        (
           ID          BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
           TITLE       VARCHAR(60) NOT NULL UNIQUE,
           CDATE       DATE,
           RATING      ENUM('1','2','3','4','5') NOT NULL,
           IMAGE       VARCHAR(45),
           GENRE_ID    INT,
           CONSTRAINT  PK_CONTENT PRIMARY KEY(ID),
           CONSTRAINT  FK_GENRE FOREIGN KEY(GENRE_ID) REFERENCES GENRE(ID) ON DELETE CASCADE ON UPDATE CASCADE
        );
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;
    @Column(name = "TITLE", unique = true)
    private String title;
    @Column(name = "CDATE")
    private LocalDate date;
    @Column(name = "RATING")
    private String rating;
    @Column(name = "IMAGE")
    private String image;
    @ManyToOne
    @JoinColumn(name = "GENRE_ID")
    private Genre genre;
    @ManyToMany(mappedBy = "contents")
    private List<Star> stars = new ArrayList<>();


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    public List<Star> getStars() {
        return stars;
    }
    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Content{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", date=").append(date);
        sb.append(", rating='").append(rating).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append(", genre=").append(genre);
        sb.append('}');
        return sb.toString();
    }
}
