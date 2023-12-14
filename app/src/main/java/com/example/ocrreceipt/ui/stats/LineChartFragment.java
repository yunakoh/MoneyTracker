package com.example.ocrreceipt.ui.stats;

import static android.icu.number.NumberRangeFormatter.RangeIdentityFallback.RANGE;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ocrreceipt.DetailFragment;
import com.example.ocrreceipt.R;
import com.example.ocrreceipt.databinding.FragmentLinechartBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class LineChartFragment extends Fragment {

    private FragmentLinechartBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLinechartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LineChart lineChart = binding.lineChart;

        LineData lineData = generateLineData();

        lineChart.setData(lineData);

        // X축
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new MyXAxisValueFormatter()); // X축의 값을 포맷팅하기 위한 클래스

        // Y축
        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setDrawGridLines(false);

        // 그래프 위의 특정 포인트를 클릭할 때 값을 보여주도록
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // 클릭한 포인트의 값을 말풍선으로 보여줍니다.
                Toast.makeText(getContext(), "Value: " + e.getY(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
                // 아무 값도 선택되지 않았을 때
            }
        });

        // 불필요한 UI 요소들을 숨김
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDrawGridBackground(false);

        return root;
    }

    private LineData generateLineData() {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 10));
        entries.add(new Entry(1, 20));
        entries.add(new Entry(2, 15));
        entries.add(new Entry(3, 25));
        // 추가적으로 데이터를 넣으세요.

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // 그래프에 대한 라벨
        dataSet.setDrawCircles(false); // 포인트에 원을 그리지 않습니다.
        dataSet.setDrawValues(false); // 값의 텍스트를 그리지 않습니다.

        LineData lineData = new LineData(dataSet);

        return lineData;
    }

    // X축의 값을 포맷팅하기 위한 클래스
    private class MyXAxisValueFormatter extends ValueFormatter {
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            // X축의 라벨을 원하는 형식으로 포맷팅하세요.
            return String.valueOf((int) value);
        }
    }
}
