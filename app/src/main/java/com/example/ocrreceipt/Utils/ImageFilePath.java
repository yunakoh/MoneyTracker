package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.documentfile.provider.DocumentFile;

public class ImageFilePath {

    public static String getPath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            return getFilePathForQ(context, uri);
        } else {

            return getRealPathFromURI(context, uri);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static String getFilePathForQ(Context context, Uri uri) {
        DocumentFile documentFile = DocumentFile.fromSingleUri(context, uri);
        if (documentFile != null && documentFile.exists()) {
            return documentFile.getUri().getPath();
        }
        return null;
    }

    private static String getRealPathFromURI(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToNext();


            String imagePath = cursor.getString(column_index);
            cursor.close();
            return imagePath;

        }
        return null;
    }
}