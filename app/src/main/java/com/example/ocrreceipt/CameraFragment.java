package com.example.ocrreceipt;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CameraFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Preview preview;
    private ImageCapture imageCapture;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CameraFragment", "Error binding camera provider", e);
            }
        }, ContextCompat.getMainExecutor(getActivity()));

        Button captureButton = view.findViewById(R.id.captureButton);
        captureButton.setOnClickListener(v -> {
            Log.d("CameraFragment", "TESTING");

            if (checkCameraPermission()) {
                takePicture();
            } else {
                requestCameraPermission();
            }
        });

        return view;
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.CAMERA},
                REQUEST_CAMERA_PERMISSION);
    }

    private void bindPreview(ProcessCameraProvider cameraProvider) {
        PreviewView previewView = getView().findViewById(R.id.previewView);
        preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();
    }

    private void takePicture() {
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(createFile()).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(getActivity()), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Uri savedUri = outputFileResults.getSavedUri();
                if (savedUri != null) {
                    getActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(), "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e("CameraFragment", "Error taking picture", exception);
            }
        });
    }

    private File createFile() {
        // TODO: 파일을 저장할 디렉터리 및 파일 이름 생성
        // 예를 들면, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) 등을 사용합니다.
        // 파일 경로를 반환하는 코드를 작성해주세요.
        return null;
    }
}