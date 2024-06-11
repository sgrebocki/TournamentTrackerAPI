package com.TournamentTracker.util.handler.exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String message){
        super(message);
    }
}
