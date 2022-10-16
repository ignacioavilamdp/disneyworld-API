package com.challenge.disneyworld.models.domain;

public enum Rating {
    R1("1"),
    R2("2"),
    R3("3"),
    R4("4"),
    R5("5");

    private final String rate;

    private Rating(String rate){
        this.rate = rate;
    }

    public String getRate(){
        return this.rate;
    }
}
