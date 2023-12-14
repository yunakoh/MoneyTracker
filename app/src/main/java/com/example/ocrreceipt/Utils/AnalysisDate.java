package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AnalysisDate {

    public static String processFilesInPeriod(String directoryPath, String startDate, String endDate) {
        int totalPriceSum = 0;

        try {
            // 디렉토리 내의 모든 파일 목록 가져오기
            List<Path> filePaths = Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            // 각 파일에 대해 처리
            for (Path filePath : filePaths) {
                List<String> lines = Files.readAllLines(filePath);
                String fileContent = String.join("", lines);

                // 기간 내에 속하는지 확인
                JSONObject JSON_DATA = new JSONObject(fileContent);
                String date = JSON_DATA.getJSONArray("images")
                        .getJSONObject(0)
                        .getJSONObject("receipt")
                        .getJSONObject("paymentInfo")
                        .getJSONObject("date")
                        .getString("text");

                if (isDateInRange(date, startDate, endDate)) {
                    // 기간 내에 속하면 "total price" 값을 더함
                    int totalPrice = JSON_DATA.getJSONArray("images")
                            .getJSONObject(0)
                            .getJSONObject("receipt")
                            .getJSONObject("totalPrice")
                            .getJSONObject("price")
                            .getInt("text");

                    totalPriceSum += totalPrice;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        // 결과를 String으로 변환하여 반환
        return Integer.toString(totalPriceSum);
    }

    private static boolean isDateInRange(String date, String startDate, String endDate) {
        // "yyyy-MM-dd" 형식의 날짜를 사용하여 날짜를 비교
        LocalDate startDateObj = LocalDate.parse(startDate);
        LocalDate endDateObj = LocalDate.parse(endDate);
        LocalDate dateObj = LocalDate.parse(date);

        return dateObj.isAfter(startDateObj) && dateObj.isBefore(endDateObj);
    }
}
