package com.example.mainservice.exceptions;

import lombok.*;

public class RegionNotFoundExc extends GlobalAppException{
    public RegionNotFoundExc() {
        super(404, "Region not found");
    }
}
