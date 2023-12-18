package com.example.ocrreceipt.ui.add;

import static com.example.ocrreceipt.Utils.JsonFileCreator.createJsonFile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ocrreceipt.DetailFragment;
import com.example.ocrreceipt.R;
import com.example.ocrreceipt.databinding.FragmentCameraBinding;
import com.example.ocrreceipt.databinding.FragmentConfirmBinding;

import java.util.HashMap;
import java.util.Map;

public class ConfirmFragment extends Fragment {

    private FragmentConfirmBinding binding;

    //ImageFragment 에서 데이터값을 받아오는 부분 구현

    private static final String ARG_RECEIPT_INFO = "arg_receipt_info";

    public static ConfirmFragment newInstance(Map<String, String> receiptInfoMap) {
        ConfirmFragment fragment = new ConfirmFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECEIPT_INFO, new HashMap<>(receiptInfoMap));
        fragment.setArguments(args);
        return fragment;
    }


    // Add this method to retrieve the data in ConfirmFragment
    private Map<String, String> getReceiptInfo() {
        if (getArguments() != null) {
            return (Map<String, String>) getArguments().getSerializable(ARG_RECEIPT_INFO);
        }
        return new HashMap<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConfirmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Map<String, String> receiptInfoMap = getReceiptInfo();

        Button ok = binding.addOk;

        // Map 값을 fragment_confirm.xml 의 EDITTEXT 에 전달
        binding.StoreInfo.setText(receiptInfoMap.get("store_info"));
        binding.Date.setText(receiptInfoMap.get("date"));
        binding.TotalPrice.setText(receiptInfoMap.get("total_price"));
        binding.CardInfo.setText(receiptInfoMap.get("card_info"));

        Log.d("ConfirmFragment", "StoreInfo text: " + receiptInfoMap.get("store_info"));
        Log.d("ConfirmFragment", "date text: " + receiptInfoMap.get("date"));
        Log.d("ConfirmFragment", "TotalPrice text: " + receiptInfoMap.get("total_price"));
        Log.d("ConfirmFragment", "CardInfo text: " + receiptInfoMap.get("card_info"));



        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DetailFragment detailFragment = new DetailFragment();
                Log.d("ConfirmFragment", "you can write here what to do when you click " + receiptInfoMap.get("store_info"));

                // EDITTEXT 값 가져오기
                String storeInfoText = binding.StoreInfo.getText().toString();
                String dateText = binding.Date.getText().toString();
                String totalPriceText = binding.TotalPrice.getText().toString();
                String cardInfoText = binding.CardInfo.getText().toString();
                String categoryText = binding.Category.getText().toString();
                String memoText = binding.Memo.getText().toString();

                //기존 Map 에서 time 가져오기
                String timeValue = receiptInfoMap.get("time");

                // DetailFragment 로 보내줄 map 생성
                Map<String, String> detailInfoMap = new HashMap<>();
                detailInfoMap.put("store_info", storeInfoText);
                detailInfoMap.put("date", dateText);
                detailInfoMap.put("total_price", totalPriceText);
                detailInfoMap.put("card_info", cardInfoText);
                detailInfoMap.put("category", categoryText);
                detailInfoMap.put("memo", memoText);
                detailInfoMap.put("time", timeValue);

                Bundle args = new Bundle();
                args.putSerializable("detail_info_map", new HashMap<>(detailInfoMap));
                detailFragment.setArguments(args);

                // JSON 파일 생성
                createJsonFile(storeInfoText, dateText, timeValue, totalPriceText, cardInfoText, categoryText, memoText, requireContext());




                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, detailFragment);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();

            }
        });


        return root;
    }


}
