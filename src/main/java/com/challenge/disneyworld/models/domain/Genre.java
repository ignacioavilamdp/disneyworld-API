package com.challenge.disneyworld.models.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain class to represent a Genre (movie or show genre).
 * Also serves as an entity for database persistence purposes using an ORM provider.
 */
@Entity
@Table(name = "GENRE")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "IMAGE")
    private String image;
    @OneToMany(mappedBy = "genre",
               //orphanRemoval = true, // I think there should not be orphan removal
               cascade = CascadeType.ALL)
    private List<Content> contents = new ArrayList<>();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public List<Content> getContents() {
        return contents;
    }
    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Genre{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
