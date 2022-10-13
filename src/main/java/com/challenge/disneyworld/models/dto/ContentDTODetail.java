package com.challenge.disneyworld.models.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public class ContentDTODetail extends ContentDTOBase{

    private String rating;
    private String genre;
    private List<String> stars;

    public ContentDTODetail() {
    }

    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public List<String> getStars() {
        return stars;
    }
    public void setStars(List<String> stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContentDTODetail{");
        sb.append(super.toString());
        sb.append(", rating='").append(rating).append('\'');
        sb.append(", genreName='").append(genre).append('\'');
        sb.append(", stars=").append(stars);
        sb.append('}');
        return sb.toString();
    }
}
