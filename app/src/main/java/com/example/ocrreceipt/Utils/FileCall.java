package com.example.ocrreceipt.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;  // 추가된 import 문

public class FileCall {

    // 파일을 읽어서 내용을 반환하는 메서드
    public static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);  // 수정된 부분

        // 파일을 행 단위로 읽어 리스트에 저장
        List<String> lines = Files.readAllLines(path);

        // 리스트를 하나의 문자열로 결합하여 반환
        return String.join("\n", lines);
    }


}