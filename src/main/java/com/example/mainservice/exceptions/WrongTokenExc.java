package com.example.mainservice.exceptions;

public class WrongTokenExc extends GlobalAppException{
    public WrongTokenExc() {
        super(409, "Wrong token");
    }
}
