package com.example.ocrreceipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ocrreceipt.databinding.FragmentDetailBinding;
import com.example.ocrreceipt.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.Map;

//지출 상세 정보를 보여주는 프래그먼트 (홈, 지출 메뉴에서 사용됨)
public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ConfirmFragment 에서의 map 값 받아오기
        Map<String, String> detailInfoMap = getDetailInfoMap();

        // TEXTVIEW 에 map 값 표현
        binding.StoreInfo.setText(detailInfoMap.get("store_info"));
        binding.Date.setText(detailInfoMap.get("date"));
        binding.TotalPrice.setText(detailInfoMap.get("total_price"));
        binding.CardInfo.setText(detailInfoMap.get("card_info"));
        binding.Category.setText(detailInfoMap.get("category"));
        binding.Memo.setText(detailInfoMap.get("memo"));


        Button done = binding.done;

        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //원래 있던 프래그먼트로 돌아가기
                //etActivity().getSupportFragmentManager().restoreBackStack("replacement");
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, homeFragment);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();

            }
        });


        return root;
    }

    private Map<String, String> getDetailInfoMap() {
        if (getArguments() != null) {
            return (Map<String, String>) getArguments().getSerializable("detail_info_map");
        }
        return new HashMap<>();
    }

}
