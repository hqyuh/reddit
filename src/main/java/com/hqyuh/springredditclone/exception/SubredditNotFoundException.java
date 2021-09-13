package com.hqyuh.springredditclone.exception;

public class SubredditNotFoundException extends RuntimeException{
    public SubredditNotFoundException(String message){
        super(message);
    }
}
