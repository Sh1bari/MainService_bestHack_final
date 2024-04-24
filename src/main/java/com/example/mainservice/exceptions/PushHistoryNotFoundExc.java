package com.example.mainservice.exceptions;

import lombok.*;

public class PushHistoryNotFoundExc extends GlobalAppException{
    public PushHistoryNotFoundExc() {
        super(404, "Push history not found");
    }
}
