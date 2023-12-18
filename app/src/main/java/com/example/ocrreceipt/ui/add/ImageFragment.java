package com.example.ocrreceipt.ui.add;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ocrreceipt.R;
import com.example.ocrreceipt.Utils.OCRGeneralAPIDemo;
import com.example.ocrreceipt.Utils.ImageEncoder;
import com.example.ocrreceipt.Utils.ReceiptParser;
import com.example.ocrreceipt.ui.home.HomeFragment;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ImageFragment extends Fragment{

    private ActivityResultLauncher<Intent> launcher;
    private Uri selectedImageUri;

    private void setImage(Uri uri) {
        ImageView imageView = requireView().findViewById(R.id.imageView);

        // Uri를 Bitmap으로 변환하여 ImageView에 설정
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
            Log.d("ImageFragment", "Selected Image Uri: " + uri.toString());
            // 선택 이미지 URI 반환
            selectedImageUri = uri;
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


        //선택 완료 버튼 클릭시 동작
        Button btnSelectComplete = view.findViewById(R.id.select_btn);
        btnSelectComplete.setOnClickListener(v -> {

            if (selectedImageUri != null) {

                Log.d("ImageFragment", "Selected Image Uri test: " + selectedImageUri.toString());
                try {
                    //선택한 이미지 URI 통해 BASE64로 인코딩
                    String base64Image = ImageEncoder.encodeImage(requireContext(), selectedImageUri);
                    //Log.d("ImageFragment", "BASE64 TEST: " + base64Image);

                    // OCRGeneralAPIDemo의 data 값으로 전달 후 JSON 파일 받아오기
                    OCRGeneralAPIDemo ocrTask = new OCRGeneralAPIDemo();
                    String json_data_string = ocrTask.execute(base64Image).get();
                    Log.d("ImageFragment", "JSON TEST: " + json_data_string);

                    // JSON 파일 파싱 후 Map 으로 반환
                    ReceiptParser receiptParser = new ReceiptParser();
                    Map<String, String> receiptInfoMap = ReceiptParser.parseReceipt(json_data_string);
                    //Log.d("ImageFragment", "RECEIPT INFO TEST: " + Receipit_INFO);




                    // ConfirmFragment에 값을 전달하여 인스턴스 생성
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.main_container, ConfirmFragment.newInstance(receiptInfoMap))
                            .commit();

                    //getParentFragmentManager().beginTransaction().replace(R.id.main_container, new ConfirmFragment()).commit();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            } else {
                // 이미지가 선택되지 않았을 때의 처리
                Log.e("ImageFragment", "No image selected");
            }

        });





    }


}
