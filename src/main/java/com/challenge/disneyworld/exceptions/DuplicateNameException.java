package com.challenge.disneyworld.exceptions;

public class DuplicateNameException extends RuntimeException{

    public static final int ERROR_CODE = 3;

    public DuplicateNameException(String message){
        super(message);
    }


}
