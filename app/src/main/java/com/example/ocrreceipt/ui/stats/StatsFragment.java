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
import android.widget.RadioButton;

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
        //destinationMap.put(binding.variance.getId(), LineChartFragment.class);
        destinationMap.put(binding.category.getId(), CategoryFragment.class);

        // Set click listeners for the buttons using view binding
        binding.method.setOnClickListener(this::onRadioButtonClick);
        //binding.variance.setOnClickListener(this::onRadioButtonClick);
        binding.category.setOnClickListener(this::onRadioButtonClick);

        loadFragment(MethodFragment.class);
    }
    private void onRadioButtonClick(View view) {
        RadioButton clickedRadioButton = (RadioButton) view;

        // Clear the activated state for all RadioButtons
        binding.method.setChecked(false);
       //binding.variance.setChecked(false);
        binding.category.setChecked(false);

        // Set the activated state for the clicked RadioButton
        clickedRadioButton.setChecked(true);

        // Load the corresponding fragment
        Class<? extends Fragment> destinationFragment = destinationMap.get(clickedRadioButton.getId());
        if (destinationFragment != null) {
            loadFragment(destinationFragment);
        }
    }

    private void loadFragment(Class<? extends Fragment> fragmentClass) {
        try {
            // Instantiate the destination fragment
            Fragment fragmentInstance = fragmentClass.newInstance();

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