package com.challenge.disneyworld.exceptions;

public class NonExistentRelationException extends RuntimeException{

    public NonExistentRelationException(String message){
        super(message);
    }

}
