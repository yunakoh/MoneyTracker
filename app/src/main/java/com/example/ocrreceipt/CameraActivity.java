package com.example.ocrreceipt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import com.example.ocrreceipt.Utils.ImageEncoder_ABS;
import com.example.ocrreceipt.Utils.OCRGeneralAPIDemo;
import com.example.ocrreceipt.Utils.ReceiptParser;
import com.example.ocrreceipt.ui.add.ConfirmFragment;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Initialize CameraX
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraUseCase(cameraProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraUseCase(ProcessCameraProvider cameraProvider) {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        imageCapture = new ImageCapture.Builder().build();

        try {
            // Get the camera
            Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture);

            // Capture image immediately
            captureImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void captureImage() {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (photoFile != null) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            // Start the camera activity for result
            startActivityForResult(takePictureIntent, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = imageFile.getAbsolutePath();
        System.out.println(currentPhotoPath);
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && resultCode == RESULT_OK) {
            processCapturedImage();
        }
    }

    private void processCapturedImage() {
        // Handle the captured image here, for example, show it in an ImageView
        // The image path can be obtained from currentPhotoPath
        // For this example, we simply log the image path
        System.out.println(currentPhotoPath);

        try {

            String BASE64Image = ImageEncoder_ABS.encodeImage(currentPhotoPath);
            System.out.println("this is : " + BASE64Image);

            // OCRGeneralAPIDemo의 data 값으로 전달 후 JSON 파일 받아오기
            OCRGeneralAPIDemo ocrTask = new OCRGeneralAPIDemo();
            String json_data_string = ocrTask.execute(BASE64Image).get();
            Log.d("ImageFragment", "JSON TEST: " + json_data_string);

            // JSON 파일 파싱 후 Map 으로 반환
            ReceiptParser receiptParser = new ReceiptParser();
            Map<String, String> receiptInfoMap = ReceiptParser.parseReceipt(json_data_string);
            //Log.d("ImageFragment", "RECEIPT INFO TEST: " + Receipit_INFO);



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // TODO: Handle the image path as needed
    }

    private void navigateToConfirmFragment(Map<String, String> receiptInfoMap) {
        // ConfirmFragment로 이동하는 FragmentTransaction 생성 및 데이터 전달
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ConfirmFragment fragment = ConfirmFragment.newInstance(receiptInfoMap);
        transaction.add(R.id.main_container, fragment);
        transaction.addToBackStack(null);  // Optional: Back stack에 추가
        transaction.commit();
    }

    // Other methods remain unchanged
}