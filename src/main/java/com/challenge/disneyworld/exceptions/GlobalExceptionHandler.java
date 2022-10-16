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
    public ResponseEntity<ErrorMessage> notFoundExceptionHandler(HttpServletRequest req, RuntimeException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), 1, req.getRequestURI()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
                    {MandatoryFieldNotPassedException.class,
                    TryingToModifyIdException.class,
                    DuplicateUniqueFieldException.class,
                    DuplicateRelationException.class,
                    DuplicateUserException.class,
                    InvalidRatingException.class,
                    InvalidRoleException.class,
                    InvalidOrderCriteriaException.class})
    @ResponseBody
    public ResponseEntity<ErrorMessage> badRequestExceptionHandler(HttpServletRequest req, RuntimeException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), 2, req.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }


}
