package com.example.mainservice.exceptions;

import lombok.*;

public class DepartmentNotFoundExc extends GlobalAppException{
    public DepartmentNotFoundExc() {
        super(404, "Department not found");
    }
}
