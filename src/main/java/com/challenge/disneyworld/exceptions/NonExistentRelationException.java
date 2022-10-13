package com.challenge.disneyworld.exceptions;

public class NonExistentRelationException extends RuntimeException{

    public static final int ERROR_CODE = 6;

    public NonExistentRelationException(String message){
        super(message);
    }


}
