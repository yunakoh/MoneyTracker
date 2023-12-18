package com.example.ocrreceipt.Utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageEncoder_ABS {

    public static String encodeImage(String imagePath) throws IOException {
        if (imagePath == null) {
            return null;
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(imagePath));

            if (inputStream != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }

                byte[] imageBytes = baos.toByteArray();
                return Base64.encodeToString(imageBytes, Base64.DEFAULT);
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}