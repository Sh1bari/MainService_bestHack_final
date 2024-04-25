package com.example.mainservice.utils;

import lombok.*;

import java.time.format.DateTimeFormatter;

public class Formatter {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
}
