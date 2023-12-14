package com.example.ocrreceipt.ui.stats;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ocrreceipt.DetailFragment;
import com.example.ocrreceipt.R;
import com.example.ocrreceipt.databinding.FragmentConfirmBinding;
import com.example.ocrreceipt.databinding.FragmentStatsBinding;

public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;

    public StatsFragment() {
        // Required empty public constructor
    }

/*
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    } */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button variance = binding.variance;
        Button method = binding.method;

        variance.setBackgroundColor(Color.BLUE);
        LineChartFragment lineChartFragment = new LineChartFragment();

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.graph_container, lineChartFragment);
        transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
        transaction.commit();

        variance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                variance.setBackgroundColor(Color.BLUE);
                LineChartFragment lineChartFragment = new LineChartFragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.graph_container, lineChartFragment);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();

            }
        });

        method.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                method.setBackgroundColor(Color.BLUE);

                MethodFragment methodFragment = new MethodFragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.graph_container, methodFragment);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();

            }
        });


        return root;
    }
}