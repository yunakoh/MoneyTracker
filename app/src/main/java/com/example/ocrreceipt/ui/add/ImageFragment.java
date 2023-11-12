package com.example.ocrreceipt.ui.add;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ocrreceipt.R;
import com.example.ocrreceipt.ui.home.HomeFragment;

import java.io.IOException;

public class ImageFragment extends Fragment{

    private ActivityResultLauncher<Intent> launcher;

    private void setImage(Uri uri) {
        ImageView imageView = requireView().findViewById(R.id.imageView);

        // Uri를 Bitmap으로 변환하여 ImageView에 설정
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            setImage(selectedImageUri);
                        }
                    }
                });

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcher.launch(intent);

        //***취소버튼 눌렀을때 갤러리로 돌아가도록 손봐야함.
        Button btnCancel = view.findViewById(R.id.cancel_btn);
        btnCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });



        Button btnSelectComplete = view.findViewById(R.id.select_btn);
        btnSelectComplete.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction().replace(R.id.main_container, new ConfirmFragment()).commit();

        });





    }


}
