package com.example.almasud.fundamental.camera_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.almasud.fundamental.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraAppActivity extends AppCompatActivity {

    public static final int IMAGE_CAPTURE_REQUEST_CODE = 1;
    private String mImagePath;
    private ImageView mImageView;
    private static final String AUTHORITY = "com.example.almasud.fundamental.camera_app.provider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_app);
        mImageView = findViewById(R.id.cameraImagePreview);
    }

    public void dispatchCamera(View view) {
        // Runtime permission for android Marshmallow or upper version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
            }
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    // only for Nougat and later versions
                    Uri imageURI = FileProvider.getUriForFile(this, AUTHORITY,
                        createImageFile());
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
                } else {
                    File imageFile = createImageFile();
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                }
                startActivityForResult(cameraIntent, IMAGE_CAPTURE_REQUEST_CODE);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "Camera_" + timeStamp;
        File fileStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!fileStorageDir.exists()) {
            fileStorageDir.mkdir();
        }
        File imageFile = File.createTempFile(fileName, ".jpeg", fileStorageDir);
        mImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE) {
            int targetW = mImageView.getWidth();
            int targetH = mImageView.getHeight();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mImagePath, options);
            int photoW = options.outWidth;
            int photoH = options.outHeight;
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            options.inJustDecodeBounds = false;
            options.inSampleSize = scaleFactor;
            Bitmap bitmap = BitmapFactory.decodeFile(mImagePath, options);
            mImageView.setImageBitmap(bitmap);
        }
    }
}
