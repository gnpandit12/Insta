package com.example.insta.model;

public class RequestBody {

    private final String username;
    private final String cursor;

    public RequestBody(String username, String cursor) {
        this.username = username;
        this.cursor = cursor;
    }


}
