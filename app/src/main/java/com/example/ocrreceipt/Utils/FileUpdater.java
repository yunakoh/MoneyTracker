package com.example.myapplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUpdater {

    public static void replaceAndSave(String filePath, String newContent) throws IOException {
        Path path = Paths.get(filePath);

        // 기존 파일의 내용을 읽어옴
        List<String> lines = Files.readAllLines(path);

        // 새로운 내용으로 대체
        lines.clear();
        lines.add(newContent);

        // 파일에 변경된 내용 저장
        Files.write(path, lines);
    }
}