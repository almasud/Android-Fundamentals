package com.example.almasud.fundamental.http_api;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.almasud.fundamental.R;
import com.squareup.picasso.Picasso;

public class FlowerViewActivity extends AppCompatActivity {
    ImageView flowerIV;
    TextView nameTV, catTV, insTV, priceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_view);

        flowerIV = findViewById(R.id.flowerDetailIV);
        nameTV = findViewById(R.id.flowerNameTV);
        catTV = findViewById(R.id.flowerCatTV);
        insTV = findViewById(R.id.flowerInsTV);
        priceTV = findViewById(R.id.flowerPriceTV);

        FlowerResponse flower = (FlowerResponse) getIntent().getSerializableExtra("flower");
        Uri uri = Uri.parse(RetrofitGetActivity.BASE_URL + "photos/" + flower.getPhoto());
        Picasso.get().load(uri).into(flowerIV);
        nameTV.setText(flower.getName());
        catTV.setText(flower.getCategory());
        insTV.setText(flower.getInstructions());
        priceTV.setText(String.valueOf(flower.getPrice()));
    }
}
