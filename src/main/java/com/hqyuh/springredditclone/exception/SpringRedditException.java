package com.hqyuh.springredditclone.exception;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
}
