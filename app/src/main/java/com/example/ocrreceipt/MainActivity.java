package com.example.ocrreceipt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.ocrreceipt.databinding.ActivityMainBinding;
import com.example.ocrreceipt.ui.add.MyBottomSheetFragment;
import com.example.ocrreceipt.ui.home.HomeFragment;
import com.example.ocrreceipt.ui.stats.StatsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBottomNavigationView();

        //앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomSheetDialog();
            }
        });

    }

    public void setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();
                return true;
            /*} else if (itemId == R.id.navigation_add) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AddFragment()).commit();
                return true;*/
            } else if (itemId == R.id.navigation_stats) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new StatsFragment()).commit();
                return true;
            }
            return false;
        });
    }

    private void showBottomSheetDialog() {
        MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment();

        // 트랜잭션 시작
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // 백 스택에 추가
        transaction.addToBackStack("replacement");

        // Fragment를 표시
        bottomSheetFragment.show(transaction, bottomSheetFragment.getTag());

        //bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }




    /*
    private void showBottomDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_bottomsheet);

        LinearLayout cameraLayout = dialog.findViewById(R.id.layoutCamera);
        LinearLayout imageLayout = dialog.findViewById(R.id.layoutImage);
        LinearLayout writeLayout = dialog.findViewById(R.id.layoutWrite);


        //버튼 선택했을때 bottom sheet dialog 사라지도록? 프래그먼트 연결되도록
        cameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        writeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

     */



}