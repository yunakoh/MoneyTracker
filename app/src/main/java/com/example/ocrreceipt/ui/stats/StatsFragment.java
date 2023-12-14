package com.example.ocrreceipt.ui.stats;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ocrreceipt.DetailFragment;
import com.example.ocrreceipt.R;
import com.example.ocrreceipt.databinding.FragmentConfirmBinding;
import com.example.ocrreceipt.databinding.FragmentStatsBinding;

import java.util.HashMap;
import java.util.Map;

public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;

    private final Map<Integer, Class<? extends Fragment>> destinationMap = new HashMap<>();

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the mapping of button IDs to destination fragment classes
        destinationMap.put(binding.method.getId(), MethodFragment.class);
        destinationMap.put(binding.variance.getId(), LineChartFragment.class);
        destinationMap.put(binding.category.getId(), LineChartFragment.class);

        // Set click listeners for the buttons using view binding
        binding.method.setOnClickListener(this::onButtonClick);
        binding.variance.setOnClickListener(this::onButtonClick);
        binding.category.setOnClickListener(this::onButtonClick);
    }
    public void onButtonClick(View view) {
        Button clickedButton = (Button) view;

        // Toggle state for the first button
        if (clickedButton.getId() == binding.method.getId()) {
            clickedButton.setActivated(!clickedButton.isActivated());
        } else {
            // For other buttons, set activated state
            clickedButton.setActivated(true);

            // Deactivate the first button
            binding.method.setActivated(false);
        }

        // Deactivate other buttons
        binding.variance.setActivated(false);
        binding.category.setActivated(false);

        // Handle button click actions as needed

        // Example: Navigate to other fragments based on button activation
        if (clickedButton.isActivated()) {
            Class<? extends Fragment> destinationFragment = destinationMap.get(clickedButton.getId());

            if (destinationFragment != null) {
                try {
                    // Instantiate the destination fragment
                    Fragment fragmentInstance = (Fragment) destinationFragment.newInstance();

                    // Navigate to the destination fragment using FragmentTransaction
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.graph_container, fragmentInstance);
                    transaction.addToBackStack(null); // Optional: Add to back stack
                    transaction.commit();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}