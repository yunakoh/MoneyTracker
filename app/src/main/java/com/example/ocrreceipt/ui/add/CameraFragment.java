package com.example.ocrreceipt.ui.add;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.camera.core.*;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.FileProvider;
import com.example.ocrreceipt.R;

import com.example.ocrreceipt.databinding.FragmentCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CameraFragment extends Fragment implements LifecycleOwner {

    private FragmentCameraBinding binding;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private String currentPhotoPath;
    private ExecutorService cameraExecutor;

    private static final String TAG = "CameraFragment";

    public CameraFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted
            initializeCameraProvider();
        } else {
            // Permission not granted, request it
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }

        cameraExecutor = Executors.newSingleThreadExecutor();

        // 카메라 버튼 클릭시
        binding.btnCapture.setOnClickListener(v -> captureImage());
    }

    private void initializeCameraProvider() {
        Log.d(TAG, "Initializing camera provider");
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraUseCase(cameraProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void bindCameraUseCase(ProcessCameraProvider cameraProvider) {
        Log.d(TAG, "Binding camera use case");
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        imageCapture = new ImageCapture.Builder().build();

        Preview preview = new Preview.Builder().build();

        try {
            // cameraProvider
            Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview);

            PreviewView previewView = binding.previewView;
            preview.setSurfaceProvider(previewView.getSurfaceProvider());

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
            Uri photoUri = FileProvider.getUriForFile(requireContext(), requireContext().getApplicationContext().getPackageName() + ".fileprovider", photoFile);
            ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(photoFile).build();

            imageCapture.takePicture(outputFileOptions, cameraExecutor, new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                    // Image saved successfully, process captured image
                    processCapturedImage();

                    // ConfirmFragment 넘어감
                    navigateToNextFragment();
                }

                @Override
                public void onError(ImageCaptureException exception) {
                    // Handle error during image capture
                    exception.printStackTrace();
                    Log.e(TAG, "Error during image capture: " + exception.getMessage());
                    exception.printStackTrace();
                }
            });
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(null);
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void processCapturedImage() {
        // Handle the captured image here
        // The image path can be obtained from currentPhotoPath
        // For this example, we simply log the image path
        Log.d(TAG, "Captured Image Path: " + currentPhotoPath);

        try {
            // Your additional image processing code here
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        // Shutdown the cameraExecutor
        cameraExecutor.shutdown();
    }

    private void navigateToNextFragment() {
        ConfirmFragment confirmFragment = new ConfirmFragment();

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, confirmFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}