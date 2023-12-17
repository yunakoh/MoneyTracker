package com.example.ocrreceipt.Utils;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCreator {

    // 파일을 생성하는 메서드
    public static void createFile(String filePath, String content) throws IOException {
        Path path = FileSystems.getDefault().getPath(filePath);

        // 파일이 이미 존재하면 예외 발생
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            throw new IOException("파일이 이미 존재합니다.");
        }

        // 파일에 내용 쓰기
        Files.write(path, content.getBytes(), StandardOpenOption.WRITE);
    }


    public static void setFile(String content) {
        // 현재 날짜 및 시간 정보를 가져옴
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String dateTimeStamp = dateFormat.format(currentDate);

        // 파일명 생성
        String fileName = "file_" + dateTimeStamp + ".txt";

        // 파일 경로 생성
        String filePath = "C:\\Your\\Directory\\Path\\" + fileName;

        try {
            createFile(filePath, content);
            System.out.println("파일이 성공적으로 생성되었습니다.");
        } catch (IOException e) {
            System.err.println("파일 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}