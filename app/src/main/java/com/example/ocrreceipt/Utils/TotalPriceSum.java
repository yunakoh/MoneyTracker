package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// 기간설정 후 해당 기간내의 파일 읽는 클래스
// 이 클래스에서는 기간에 따른 total sum (가격총합) 만 이용
// 이 클래스 사용하여 카테고리, 카드사별 만들것
public class TotalPriceSum {

    public static int sumTotalPriceInRange(String directoryPath, long lowerLimit, long upperLimit) {
        int sum = 0;

        // 내부 저장소 디렉토리에서 파일 목록 가져오기
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.canRead() && file.getName().endsWith(".json")) {
                    // 파일명에서 .json 확장자 제거
                    String fileName = file.getName().replace(".json", "");

                    // 파일명에서 숫자 부분 추출
                    if (fileName.length() == 12 && fileName.matches("\\d+")) {
                        long fileNumber = Long.parseLong(fileName);

                        // 범위 내에 있는 파일만 읽기 시도
                        if (fileNumber > lowerLimit && fileNumber < upperLimit) {
                            int totalPrice = readTotalPrice(file);
                            System.out.println(totalPrice);
                            sum += totalPrice;
                        }
                    }
                }
            }
        } else {
            System.out.println("디렉토리가 존재하지 않거나 읽을 수 없습니다.");
        }

        return sum;
    }

    public static int readTotalPrice(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }

            // 파일 내용을 JSON 객체로 파싱
            JSONObject json = new JSONObject(content.toString());

            // total price 값을 추출하여 int로 변환
            return Integer.parseInt(json.getString("totalPrice"));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }
}