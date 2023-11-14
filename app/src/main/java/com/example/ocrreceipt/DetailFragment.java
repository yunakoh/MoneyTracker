package com.example.ocrreceipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ocrreceipt.databinding.FragmentDetailBinding;

//지출 상세 정보를 보여주는 프래그먼트 (홈, 지출 메뉴에서 사용됨)
public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button done = binding.done;

        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //원래 있던 프래그먼트로 돌아가기
                getActivity().getSupportFragmentManager().restoreBackStack("replacement");

            }
        });


        return root;
    }

}
