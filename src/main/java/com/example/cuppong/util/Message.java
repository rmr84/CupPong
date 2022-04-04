package com.example.cuppong.util;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    private String type;
    private HashMap<String, String> params = new HashMap<>();

    public Message(String input) {
        String[] split = input.split(",");
        type = split[0];
        for (int i = 1; i < split.length; i++) {
            String s = split[i];
            String key = s.split(":")[0];
            String value = s.split(":")[1];
            params.put(key, value);
        }
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Type: " + type + "\n" +
                "Params:\n" + params.toString();
    }

    public int getInt(String key) {
        return Integer.parseInt(params.get(key));
    }

    public boolean getBool(String key) {
        return Boolean.parseBoolean(params.get(key));
    }

    public String getString(String key) {
        return params.get(key);
    }
}