package com.example.mainservice.exceptions;

import lombok.*;

public class ProductNotFoundExc extends GlobalAppException{
    public ProductNotFoundExc() {
        super(404, "Product not found");
    }
}
