package com.swiz.bcs.exception;

public class AuthorNotFoundException extends RuntimeException{
        public AuthorNotFoundException(String message){
            super(message);
        }
    }

