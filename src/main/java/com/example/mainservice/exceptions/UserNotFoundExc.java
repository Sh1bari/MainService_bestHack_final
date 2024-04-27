package com.example.mainservice.exceptions;

import lombok.*;

public class UserNotFoundExc extends GlobalAppException{
    public UserNotFoundExc() {
        super(404, "User not found");
    }
}
