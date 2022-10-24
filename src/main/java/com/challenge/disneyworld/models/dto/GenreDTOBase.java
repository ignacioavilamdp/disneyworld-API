package com.challenge.disneyworld.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A base Data Transfer Object (DTO) of a
 * {@link com.challenge.disneyworld.models.domain.Genre}
 */
@Schema(name = "Genre")
public class GenreDTOBase {

    private int id;
    private String name;
    private String image;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenreDTOBase{");
        sb.append("id='").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
