package com.challenge.disneyworld.models.dto;


public class StarDTODetail extends StarDTOBase{

    private long id;
    private short age;
    private float weight;
    private String history;

    public StarDTODetail() {
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StarDTODetail{");
        sb.append("id=").append(id);
        sb.append(", name= ").append(this.getName());
        sb.append(", age=").append(age);
        sb.append(", weight=").append(weight);
        sb.append(", image=").append(this.getImage());
        sb.append(", history='").append(history).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
