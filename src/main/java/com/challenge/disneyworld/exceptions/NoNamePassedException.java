package com.challenge.disneyworld.exceptions;

public class NoNamePassedException extends RuntimeException{

    public static final int ERROR_CODE = 2;

    public NoNamePassedException(String message){
        super(message);
    }


}
