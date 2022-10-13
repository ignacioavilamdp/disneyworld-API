package com.challenge.disneyworld.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NonExistentEntityException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> nonExistentStarExceptionHandler(HttpServletRequest req, NonExistentEntityException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), ex.ERROR_CODE, req.getRequestURI()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoNamePassedException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> noNamePassedExceptionHandler(HttpServletRequest req, NoNamePassedException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), ex.ERROR_CODE, req.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateNameException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> duplicateNameExceptionHandler(HttpServletRequest req, DuplicateNameException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), ex.ERROR_CODE, req.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TryingToModifyIdException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> tryingToModifyIdExceptionHandler(HttpServletRequest req, TryingToModifyIdException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), ex.ERROR_CODE, req.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRelationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> duplicateRelationExceptionHandler(HttpServletRequest req, DuplicateRelationException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), ex.ERROR_CODE, req.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonExistentRelationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> nonExistentRelationExceptionHandler(HttpServletRequest req, NonExistentRelationException ex){
        return new ResponseEntity<>(
                new ErrorMessage(ex.getMessage(), ex.ERROR_CODE, req.getRequestURI()),
                HttpStatus.NOT_FOUND);
    }

}
