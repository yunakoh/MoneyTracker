package com.example.ocrreceipt.Utils;

import java.util.HashMap;
import java.util.Map;

public class PercentageCalculator {

    public Map<String, Double> calculatePercentage(Map<String, Integer> categoryTotalPrices) {
        Map<String, Double> categoryPercentages = new HashMap<>();

        // Calculate the total sum of prices
        double totalSum = categoryTotalPrices.values().stream().mapToDouble(Integer::doubleValue).sum();

        // Calculate percentage for each category
        for (Map.Entry<String, Integer> entry : categoryTotalPrices.entrySet()) {
            String category = entry.getKey();
            int totalPrice = entry.getValue();
            double percentage = (totalPrice / totalSum) * 100;

            // Put the result in the new map
            categoryPercentages.put(category, percentage);
        }

        return categoryPercentages;
    }
}