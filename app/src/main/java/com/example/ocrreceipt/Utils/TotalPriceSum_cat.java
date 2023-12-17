package com.example.ocrreceipt.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TotalPriceSum_cat {

    public void sumTotalPriceByCategory(String directoryPath, long lowerLimit, long upperLimit) {
        File folder = new File(directoryPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    try {
                        // Parse JSON content from the file
                        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
                        JSONObject json = new JSONObject(content);

                        // Extract relevant information
                        long fileNumber = Long.parseLong(file.getName().replace(".json", ""));
                        String category = json.getString("category");
                        int totalPrice = json.getInt("totalPrice");

                        // Check if the file number is within the specified range
                        if (fileNumber > lowerLimit && fileNumber < upperLimit) {
                            // Update the total price for the category
                            updateTotalPrice(category, totalPrice);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            // Print the total prices for each category
            printTotalPrices();
        } else {
            System.out.println("No files found in the specified directory.");
        }
    }

    // Map to store total prices for each category
    private final java.util.Map<String, Integer> categoryTotalPrices = new java.util.HashMap<>();

    private void updateTotalPrice(String category, int totalPrice) {
        // Update the total price for the category
        categoryTotalPrices.merge(category, totalPrice, Integer::sum);
    }

    private void printTotalPrices() {
        // Print the total prices for each category
        for (java.util.Map.Entry<String, Integer> entry : categoryTotalPrices.entrySet()) {
            System.out.println("Category: " + entry.getKey() + ", Total Price: " + entry.getValue());
        }
    }
}