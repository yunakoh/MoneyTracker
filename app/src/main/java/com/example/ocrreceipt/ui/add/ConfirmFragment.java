package com.example.ocrreceipt.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ocrreceipt.DetailFragment;
import com.example.ocrreceipt.R;
import com.example.ocrreceipt.databinding.FragmentCameraBinding;
import com.example.ocrreceipt.databinding.FragmentConfirmBinding;

public class ConfirmFragment extends Fragment {

    private FragmentConfirmBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentConfirmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button ok = binding.addOk;

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DetailFragment detailFragment = new DetailFragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, detailFragment);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();

            }
        });


        return root;
    }


}
