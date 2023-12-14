package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class ReceiptParser {

    public static String parseReceipt(String json_data_String) {
        StringBuilder resultString = new StringBuilder();

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

            String total_Price = resultObject.getJSONObject("totalPrice")
                    .getJSONObject("price")
                    .getString("text");

            String card_Info = resultObject.getJSONObject("paymentInfo")
                    .getJSONObject("cardInfo")
                    .getJSONObject("company")
                    .getString("text");

            // 추출한 정보 출력
            System.out.println("store info: " + store_Info);
            System.out.println("date: " + date);
            System.out.println("total price: " + total_Price);
            System.out.println("card info: " + card_Info);

            // 정보를 문자열로 합치기
            resultString.append("store info: ").append(store_Info).append("\n");
            resultString.append("date: ").append(date).append("\n");
            resultString.append("total price: ").append(total_Price).append("\n");
            resultString.append("card info: ").append(card_Info).append("\n");



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultString.toString();

    }
}