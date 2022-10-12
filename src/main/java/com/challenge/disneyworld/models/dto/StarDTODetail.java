package com.challenge.disneyworld.models.dto;


import java.util.List;

public class StarDTODetail extends StarDTOBase{

    private short age;
    private float weight;
    private String history;
    private List<ContentDTOBase> contents;

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
    public List<ContentDTOBase> getContents() {
        return contents;
    }
    public void setContents(List<ContentDTOBase> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StarDTODetail{");
        sb.append(super.toString());
        sb.append(", age=").append(age);
        sb.append(", weight=").append(weight);
        sb.append(", history='").append(history).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
