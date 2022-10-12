package com.challenge.disneyworld.models.dto;

import java.util.List;

public class GenreDTODetail extends GenreDTOBase{


    private List<ContentDTOBase> contents;

    public GenreDTODetail() {
    }

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
