package com.challenge.disneyworld.exceptions;

public class TryingToModifyIdException extends RuntimeException{

    public static final int ERROR_CODE = 4;

    public TryingToModifyIdException(String message){
        super(message);
    }


}
