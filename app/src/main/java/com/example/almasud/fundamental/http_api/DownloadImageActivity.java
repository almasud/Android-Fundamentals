package com.example.almasud.fundamental.http_api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.almasud.fundamental.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageActivity extends AppCompatActivity {
    private  static final String IMAGE_URL = "https://fyf.tac-cdn.net/images/products/large/BF216-11KM.jpg";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);

        imageView = findViewById(R.id.retrofitIV);
//        new DownloadTask().execute(IMAGE_URL);
        Picasso.get().load(IMAGE_URL).into(imageView);
    }

    private class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
        /**
         *
         * @param strings Receive an urls as a string from execute() method of AsyncTask.
         * @return The Bitmap of the downloaded images to onPostExecute(Bitmap bitmap)
         */
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL imageURL = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         *
         * @param values The values receive from  publishProgress() to post in UI or main thread.
         */

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        /**
         *
         * @param bitmap The download data received from doInBackground(String... strings)
         * to make it an image and finally post in UI or main thread.
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }

}
