package com.challenge.disneyworld.exceptions;

public class NonExistentEntityException extends RuntimeException{

    public static final int ERROR_CODE = 1;

    public NonExistentEntityException(String message){
        super(message);
    }


}
