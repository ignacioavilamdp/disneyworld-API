package com.challenge.disneyworld.models.dto;

import java.io.Serializable;

public class StarDTOBase implements Serializable {

    private String name;
    private String image;

    public StarDTOBase() {
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
        final StringBuilder sb = new StringBuilder("StarDTOBase{");
        sb.append("name='").append(name).append('\'');
        sb.append(", image='").append(image).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
