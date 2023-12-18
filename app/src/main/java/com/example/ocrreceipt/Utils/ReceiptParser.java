package com.example.ocrreceipt.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

// OCR 서버에서 받아온 분석 내용 중 상호명, 가격, 카드, 날짜 값을 따로
// date 에서 6글자일 경우 8글자로 맞추는 작업 필요
// time 에서 초 가 추가된 경우 삭제하는 작업 필요


public class ReceiptParser {

    public static Map<String, String> parseReceipt(String json_data_String) {
        Map<String, String> resultMap = new HashMap<>();

        try {
            // JSON 파싱
            JSONObject JSON_DATA = new JSONObject(json_data_String);

            // 정보 추출
            JSONObject resultObject = JSON_DATA.getJSONArray("images")
                    .getJSONObject(0)
                    .getJSONObject("receipt")
                    .getJSONObject("result");

            String store_Info = resultObject.getJSONObject("storeInfo")
                    .getJSONObject("name")
                    .getString("text");

            String date = resultObject.getJSONObject("paymentInfo")
                    .getJSONObject("date")
                    .getString("text");

            // 숫자가 아닌 글자 제거 로직 추가
            date = removeNonNumericCharacters(date);

            // date 값이 6글자(년도가 2글자)인 경우, "20"을 앞에 추가)
            if (date.length() == 6) {
                date = "20" + date;
            }

            String time = resultObject.getJSONObject("paymentInfo")
                    .getJSONObject("time")
                    .getString("text");

            time = removeNonNumericCharacters(time);

            // time 값이 6글자(초 단위 추가)인 경우 맨 뒤 두 글자 제거
            if (time.length() == 6) {
                time = time.substring(0, 4);
            }

            String total_Price = resultObject.getJSONObject("totalPrice")
                    .getJSONObject("price")
                    .getString("text");

            total_Price = removeNonNumericCharacters(total_Price);

            String card_Info = resultObject.getJSONObject("paymentInfo")
                    .getJSONObject("cardInfo")
                    .getJSONObject("company")
                    .getString("text");

            // 맵에 값을 추가
            resultMap.put("store_info", store_Info);
            resultMap.put("date", date);
            resultMap.put("time", time);
            resultMap.put("total_price", total_Price);
            resultMap.put("card_info", card_Info);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    // 숫자가 아닌 글자 제거 메서드

    private static String removeNonNumericCharacters(String input) {
        return input.replaceAll("[^0-9]", "");
    }
}