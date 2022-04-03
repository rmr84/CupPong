package com.example.cuppong.util;

import java.util.HashMap;

public class Message {
    private String type;
    private HashMap<String, String> params = new HashMap<>();

    public Message(String input) {
        String[] split = input.split(",");
        type = split[0];
        for (String s : split) {
            String key = s.split(":")[0];
            String value = s.split(":")[1];
            params.put(key, value);
        }
    }

    public String getType() {
        return type;
    }

    public String getParam(String key) {
        return params.get(key);
    }
}
