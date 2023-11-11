package com.example.ocrreceipt.ui.add;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.ocrreceipt.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyBottomSheetFragment extends BottomSheetDialogFragment {

    public MyBottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout cameraLayout = view.findViewById(R.id.layoutCamera);
        LinearLayout imageLayout = view.findViewById(R.id.layoutImage);
        LinearLayout writeLayout = view.findViewById(R.id.layoutWrite);


        cameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

                CameraFragment cameraFragment = new CameraFragment();

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

                transaction.replace(R.id.main_container, cameraFragment);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack

                transaction.commit();


            }
        });
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        writeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        //getDialog().show();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setGravity(Gravity.BOTTOM);

    }

}

