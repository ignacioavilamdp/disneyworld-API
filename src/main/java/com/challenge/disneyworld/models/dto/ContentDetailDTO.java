package com.challenge.disneyworld.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * A detailed Data Transfer Object (DTO) of a
 * {@link com.challenge.disneyworld.models.domain.Content}
 */
@Schema(name = "Movie-detail")
public class ContentDetailDTO extends ContentBaseDTO {

    private String rating;
    private String genre;
    @Schema(name = "characters")
    private List<String> stars;

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
