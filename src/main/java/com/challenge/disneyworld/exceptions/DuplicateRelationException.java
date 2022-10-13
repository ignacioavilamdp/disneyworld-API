package com.challenge.disneyworld.exceptions;

public class DuplicateRelationException extends RuntimeException{

    public static final int ERROR_CODE = 5;

    public DuplicateRelationException(String message){
        super(message);
    }


}
