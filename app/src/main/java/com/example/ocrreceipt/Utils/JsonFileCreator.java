package com.example.ocrreceipt.Utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 작성완료 버튼 누르면 JSON 파일 생성하는 클래스
// 현재 상호명, 날짜, 가격, 카드사 만 되어있음. 할부와 비고는 추후 작성바람
// 파일명은 "날짜+시간"으로 되어있음
// 내역 수정에도 해당 클래스 사용할 예정

public class JsonFileCreator {

    public static void createJsonFile(String storeInfo, String date, String time, String totalPrice, String cardInfo, String category, String memo, Context context) {
        try {
            // date와 time을 합친 파일명 생성
            String fileName = date + time + ".json";

            // JSON 객체 생성
            JSONObject json = new JSONObject();
            json.put("storeInfo", storeInfo);
            json.put("date", date);
            json.put("time", time);
            json.put("totalPrice", totalPrice);
            json.put("cardInfo", cardInfo);
            json.put("category", category);
            json.put("memo", memo);

            // 내부 저장소 경로 가져오기
            File file = new File(context.getFilesDir(), fileName);

            // 파일에 JSON 객체 쓰기
            try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
                fileOutputStream.write(json.toString().getBytes());
            }

            System.out.println("JSON 파일이 생성되었습니다. 파일 경로: " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}