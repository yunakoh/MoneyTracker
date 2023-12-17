package com.example.ocrreceipt.ui.stats;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ocrreceipt.databinding.FragmentLinechartBinding;
import com.example.ocrreceipt.databinding.FragmentMethodBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class MethodFragment extends Fragment {
    private FragmentMethodBinding binding;

    public MethodFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentMethodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        PieChart pieChart = binding.pieChart;
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30f, "Category 1"));
        entries.add(new PieEntry(40f, "Category 2"));
        entries.add(new PieEntry(20f, "Category 3"));
        entries.add(new PieEntry(10f, "Category 4"));

        PieDataSet dataSet = new PieDataSet(entries, "My Pie Chart");
        dataSet.setColors(Color.rgb(255, 0, 0), Color.rgb(0, 255, 0), Color.rgb(0, 0, 255), Color.rgb(255, 255, 0));
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate(); // refresh the chart

        return root;

    }
}
