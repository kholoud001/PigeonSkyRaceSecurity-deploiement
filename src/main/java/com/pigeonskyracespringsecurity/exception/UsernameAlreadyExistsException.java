package com.pigeonskyracespringsecurity.exception;

public class UsernameAlreadyExistsException extends Exception{

    public UsernameAlreadyExistsException(String username){
        super("The username '" + username + "' already exists.");
    }

   
}
