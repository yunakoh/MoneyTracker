package com.example.ocrreceipt.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageEncoder {

    public static String encodeImage(Context context, Uri imageUri) throws IOException {
        InputStream inputStream = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            inputStream = contentResolver.openInputStream(imageUri);

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