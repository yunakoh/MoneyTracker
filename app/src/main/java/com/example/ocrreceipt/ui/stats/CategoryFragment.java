package com.example.ocrreceipt.ui.stats;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocrreceipt.Utils.PercentageCalculator;
import com.example.ocrreceipt.Utils.TotalPriceSum_cat;
import com.example.ocrreceipt.databinding.FragmentCategoryBinding;
import com.example.ocrreceipt.databinding.FragmentMethodBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

public class CategoryFragment extends Fragment {
    private FragmentCategoryBinding binding;

    public CategoryFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        PieChart pieChart = binding.pieChart;
        ListView listView = binding.listView;

        TotalPriceSum_cat CatValue = new TotalPriceSum_cat();

        Map<String, Integer> categoryTotalPrices = CatValue.sumTotalPriceByCategory("/data/user/0/com.example.ocrreceipt/files", 0, 30);

        PercentageCalculator calculator = new PercentageCalculator();
        Map<String, Double> categoryPercentages = calculator.calculatePercentage(categoryTotalPrices);

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int i = 0;
        for (Map.Entry<String, Double> entry : categoryPercentages.entrySet()) {
            String category = entry.getKey();
            double percentage = entry.getValue();

            entries.add(new PieEntry((float) percentage, i));
            labels.add(category);

            i++;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Category Percentages");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(10f);
        pieChart.setUsePercentValues(true);

        pieChart.invalidate();



        return root;

    }



}
