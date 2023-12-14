package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalysisCard {

    public static String processFiles(String directoryPath) {
        // 그룹별로 Total Price를 저장할 Map
        Map<String, Integer> groupTotalPriceMap = new HashMap<>();

        try {
            // 디렉토리 내의 모든 파일 목록 가져오기
            List<Path> filePaths = Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            // 현재 날짜를 기준으로 30일 이내인 파일들을 처리
            LocalDate currentDate = LocalDate.now();
            for (Path filePath : filePaths) {
                List<String> lines = Files.readAllLines(filePath);
                String fileContent = String.join("", lines);

                // "date" 값 가져오기
                JSONObject JSON_DATA = new JSONObject(fileContent);
                String date = JSON_DATA.getJSONArray("images")
                        .getJSONObject(0)
                        .getJSONObject("receipt")
                        .getJSONObject("paymentInfo")
                        .getJSONObject("date")
                        .getString("text");

                // "card info" 값 가져오기
                String cardInfo = JSON_DATA.getJSONArray("images")
                        .getJSONObject(0)
                        .getJSONObject("receipt")
                        .getJSONObject("paymentInfo")
                        .getJSONObject("cardInfo")
                        .getJSONObject("company")
                        .getString("text");

                // "date" 값이 30일 이내이면서 "card info" 값이 같은 그룹에 Total Price를 더함
                if (isDateWithin30Days(currentDate, date)) {
                    int totalPrice = JSON_DATA.getJSONArray("images")
                            .getJSONObject(0)
                            .getJSONObject("receipt")
                            .getJSONObject("totalPrice")
                            .getJSONObject("price")
                            .getInt("text");

                    String groupKey = cardInfo + "-" + date;
                    groupTotalPriceMap.merge(groupKey, totalPrice, Integer::sum);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        // 결과를 String으로 변환하여 반환
        return formatResult(groupTotalPriceMap);
    }

    private static boolean isDateWithin30Days(LocalDate currentDate, String date) {
        LocalDate fileDate = LocalDate.parse(date);
        return currentDate.minusDays(30).isBefore(fileDate) && currentDate.isAfter(fileDate);
    }

    private static String formatResult(Map<String, Integer> groupTotalPriceMap) {
        // 결과를 문자열로 정리하여 반환
        StringBuilder resultStringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : groupTotalPriceMap.entrySet()) {
            resultStringBuilder.append("Group: ").append(entry.getKey()).append(", Total Price: ").append(entry.getValue()).append("\n");
        }
        return resultStringBuilder.toString();
    }
}
