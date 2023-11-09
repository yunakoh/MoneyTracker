package com.example.ocrreceipt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.ocrreceipt.databinding.ActivityMainBinding;
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
        if(savedInstanceState == null){
            binding.bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }


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

    private void showBottomDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

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

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }




}