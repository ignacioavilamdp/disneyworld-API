package com.challenge.disneyworld.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * A detailed Data Transfer Object (DTO) of a
 * {@link com.challenge.disneyworld.models.domain.Genre}
 */
@Schema(name = "Genre-detail")
public class GenreDTODetail extends GenreDTOBase{

    @Schema(name = "movies")
    private List<ContentDTOBase> contents;

    public List<ContentDTOBase> getContents() {
        return contents;
    }
    public void setContents(List<ContentDTOBase> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenreDTODetail{");
        sb.append(super.toString());
        sb.append(", contents=").append(contents);
        sb.append('}');
        return sb.toString();
    }
}
