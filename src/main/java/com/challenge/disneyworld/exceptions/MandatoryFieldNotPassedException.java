package com.challenge.disneyworld.exceptions;

public class MandatoryFieldNotPassedException extends RuntimeException{

    public MandatoryFieldNotPassedException(String message){
        super(message);
    }

}
