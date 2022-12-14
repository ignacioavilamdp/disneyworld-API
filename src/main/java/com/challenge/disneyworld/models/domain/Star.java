package com.challenge.disneyworld.models.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain class to represent a Star (movie or show character).
 * Also serves as an entity for database persistence purposes using an ORM provider.
 */
@Entity
@Table(name = "STAR")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @Column(name = "NAME", unique = true)
    private String name;
    @Column(name = "AGE")
    private short age;
    @Column(name = "WEIGHT")
    private float weight;
    @Column(name = "IMAGE")
    private String image;
    @Column(name = "HISTORY")
    private String history;
    @ManyToMany      // This is the owning side of the relation
    @JoinTable(name = "STAR_CONTENT",
            joinColumns = @JoinColumn(name = "STAR_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTENT_ID")
    )
    List<Content> contents = new ArrayList<>();


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public short getAge() {
        return age;
    }
    public void setAge(short age) {
        this.age = age;
    }
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getHistory() {
        return history;
    }
    public void setHistory(String history) {
        this.history = history;
    }
    public List<Content> getContents() {
        return contents;
    }
    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Star{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", weight=").append(weight);
        sb.append(", image='").append(image).append('\'');
        sb.append(", history='").append(history).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
