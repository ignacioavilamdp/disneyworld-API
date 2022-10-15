package com.challenge.disneyworld.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Character-detail")
public class StarDTODetail extends StarDTOBase{

    private short age;
    private float weight;
    private String history;
    //@Schema(name = "movies")
    private List<String> contents;

    public StarDTODetail() {
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
    public String getHistory() {
        return history;
    }
    public void setHistory(String history) {
        this.history = history;
    }
    public List<String> getContents() {
        return contents;
    }
    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StarDTODetail{");
        sb.append(super.toString());
        sb.append(", age=").append(age).append('\'');
        sb.append(", weight=").append(weight).append('\'');
        sb.append(", history='").append(history).append('\'');
        sb.append(", contents='").append(contents).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
