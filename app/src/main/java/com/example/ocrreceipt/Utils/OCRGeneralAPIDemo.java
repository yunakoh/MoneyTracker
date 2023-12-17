package com.example.ocrreceipt.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;




public class OCRGeneralAPIDemo extends AsyncTask<String, Void, String> {

        String apiURL = "https://maud43zqbh.apigw.ntruss.com/custom/v1/26156/820c0f8b2009f61a2c9f94ddb83182114a9b5d18060f1ee896999db0d739999a/document/receipt";
        String secretKey = "dExFempOSFBOZXdVemlpVm9BVGhKa1FCVHJsWVRDcmU=";
    @Override
    protected String doInBackground(String... params) {
        if (params.length == 0 || params[0] == null) {
            return "No input data provided.";
        }

        try {
            String inputData = params[0];

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            //image should be public, otherwise, should use data
            //FileInputStream inputStream = new FileInputStream("/storage/self/primary/Download/971120_20170705143932_354_0001.jpg");
            //byte[] buffer = new byte[inputStream.available()];
            //inputStream.read(buffer);
            //inputStream.close();
            image.put("data", inputData);
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(postParams);
            System.out.println(response);

            return response.toString();

        } catch (Exception e) {
            return e.toString();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // 처리 결과를 이용한 작업을 수행 (UI 업데이트 등)
        System.out.println(result);
    }

}
