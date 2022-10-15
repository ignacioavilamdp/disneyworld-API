package com.challenge.disneyworld.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
                    {NonExistentEntityException.class,
                    NonExistentRelationException.class})
    @ResponseBody
    public ResponseEntity<ErrorMessage> nonExistentStarExceptionHandler(HttpServletRequest req, RuntimeException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), 1, req.getRequestURI()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
                    {NoNamePassedException.class,
                    TryingToModifyIdException.class,
                    DuplicateNameException.class,
                    DuplicateRelationException.class,
                    DuplicateUserException.class})
    @ResponseBody
    public ResponseEntity<ErrorMessage> noNamePassedExceptionHandler(HttpServletRequest req, RuntimeException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), 2, req.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }


}
