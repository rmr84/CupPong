package com.example.cuppong.util;

/**
 * Type used when sending messages back and forth from client & server
 */

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    private String type;
    private HashMap<String, String> params = new HashMap<>();
    private boolean empty = false;

    /**
     * Constructor.
     *
     * @param input The input string to parse for this {@link #Message} instance.
     */
    public Message(String input) {
        if (input.trim().length() < 1) {
            empty = true;
            return;
        }
        String[] split = input.split(",");
        type = split[0];
        for (int i = 1; i < split.length; i++) {
            String s = split[i];
            String key = s.split(":")[0];
            String value = s.split(":")[1];
            params.put(key, value);
        }
    }

    /**
     * Gets the current type of the message.
     *
     * @return
     */

    public String getType() {
        return type;
    }

    /**
     * Gets the int value for a certain key.
     *
     * @param key The key that contains an int value.
     * @return
     */

    public int getInt(String key) {
        return Integer.parseInt(params.get(key));
    }

    /**
     * Gets the boolean value for a certain key.
     *
     * @param key The key that contains a boolean value.
     * @return
     */

    public boolean getBool(String key) {
        return Boolean.parseBoolean(params.get(key));
    }

    /**
     * Gets the string value for a certain key.
     *
     * @param key The key that contains a string value.
     * @return
     */

    public String getString(String key) {
        return params.get(key);
    }

    /**
     * Checks to see if the message was empty
     *
     * @return
     */

    public boolean isEmpty() {
        return empty;
    }

    @Override
    public String toString() {
        return "Type: " + type + "\n" +
                "Params:\n" + params.toString();
    }
}