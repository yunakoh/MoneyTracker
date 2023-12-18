package com.example.ocrreceipt.Utils;

// 절대경로 앞부분 필요없는 부분 지우는 클래스
public class StringHelper {

    public static String removePrefix(String input, String prefix) {
        if (input != null && input.startsWith(prefix)) {
            return input.substring(prefix.length());
        } else {
            return input; // Prefix가 없으면 그대로 반환
        }
    }
}