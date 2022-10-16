package com.challenge.disneyworld.exceptions;

public class InvalidRatingException extends RuntimeException{

    public InvalidRatingException(String message){
        super(message);
    }

}
