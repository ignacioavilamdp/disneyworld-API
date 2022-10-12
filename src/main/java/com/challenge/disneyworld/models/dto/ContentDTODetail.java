package com.challenge.disneyworld.models.dto;


import com.challenge.disneyworld.models.domain.Star;

import java.util.List;

public class ContentDTODetail extends ContentDTOBase{

    private String rating;
    private String genreName;
    private List<StarDTOBase> stars;

    public ContentDTODetail() {
    }

    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getGenreName() {
        return genreName;
    }
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
    public List<StarDTOBase> getStars() {
        return stars;
    }
    public void setStars(List<StarDTOBase> stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContentDTODetail{");
        sb.append(super.toString());
        sb.append(", rating='").append(rating).append('\'');
        sb.append(", genreName='").append(genreName).append('\'');
        sb.append(", stars=").append(stars);
        sb.append('}');
        return sb.toString();
    }
}
