package com.example.mainservice.exceptions;

import lombok.*;

public class RoleNotFoundExc extends GlobalAppException{
    public RoleNotFoundExc() {
        super(404, "Role not found");
    }
}
