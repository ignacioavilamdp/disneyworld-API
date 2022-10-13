package com.challenge.disneyworld.exceptions;

public class ErrorMessage {
    private int errorCode;
    private String message;
    private String path;

    public ErrorMessage(String message, int code, String path) {
        this.message = message;
        this.errorCode = code;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
