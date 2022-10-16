package com.challenge.disneyworld.exceptions;

public class NonExistentEntityException extends RuntimeException{

    public NonExistentEntityException(String message){
        super(message);
    }

}
