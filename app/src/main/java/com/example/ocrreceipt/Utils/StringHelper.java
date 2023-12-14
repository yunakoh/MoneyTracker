package com.example.myapplication;

public class StringHelper {

    public static String removePrefix(String input, String prefix) {
        if (input != null && input.startsWith(prefix)) {
            return input.substring(prefix.length());
        } else {
            return input; // Prefix가 없으면 그대로 반환
        }
    }
}