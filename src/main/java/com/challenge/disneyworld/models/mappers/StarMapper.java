package com.challenge.disneyworld.models.mappers;

import com.challenge.disneyworld.models.domain.Content;
import com.challenge.disneyworld.models.domain.Star;
import com.challenge.disneyworld.models.dto.StarDTOBase;
import com.challenge.disneyworld.models.dto.StarDTODetail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StarMapper {

    public static StarDTOBase domainToDTOBase(Star star){
        StarDTOBase dto = null;
        if (star != null) {
            dto = new StarDTOBase();
            dto.setId(star.getId());
            dto.setName(star.getName());
            dto.setImage(star.getImage());
        }
        return dto;
    }

    public static StarDTODetail domainToDTODetail(Star star){
        StarDTODetail dto = null;
        if (star != null) {
            dto = new StarDTODetail();
            dto.setId(star.getId());
            dto.setName(star.getName());
            dto.setAge(star.getAge());
            dto.setWeight(star.getWeight());
            dto.setImage(star.getImage());
            dto.setHistory(star.getHistory());
            List<String> contentsTitles = new ArrayList<>();
            for (Content content : star.getContents()){
                contentsTitles.add(content.getTitle());
            }
            dto.setContents(contentsTitles);
            /*
            dto.setContents(
                    star.getContents().
                            stream().
                            map(content -> ContentMapper.domainToDTOBase(content)).
                            collect(Collectors.toList())
             );
             */
        }
        return dto;
    }

    public static Star DTOToDomain(StarDTODetail dto){
        Star star = null;
        if (dto != null){
            star = new Star();
            star.setId(dto.getId());
            star.setName(dto.getName());
            star.setAge(dto.getAge());
            star.setWeight(dto.getWeight());
            star.setImage(dto.getImage());
            star.setHistory(dto.getHistory());
            //star.setContents();
        }
        return star;
    }

}
