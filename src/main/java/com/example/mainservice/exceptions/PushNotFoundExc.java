package com.example.mainservice.exceptions;

import lombok.*;

public class PushNotFoundExc extends GlobalAppException{
    public PushNotFoundExc() {
        super(404, "Push not found");
    }
}
