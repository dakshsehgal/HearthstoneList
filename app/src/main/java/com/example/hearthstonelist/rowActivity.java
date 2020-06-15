package com.example.hearthstonelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class rowActivity extends AppCompatActivity {
    private TextView cardName;
    private TextView cardText;
    private TextView cardAttack;
    private TextView cardHealth;
    private ImageView cardImage;

    public static class getImage extends AsyncTask<String, Void, Void> {
        private ImageView image;
        private AppCompatActivity activity;
        private Bitmap bitmap;


        public getImage(ImageView image, AppCompatActivity activity) {
            this.image = image;
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(String... url) {
            try {

                this.bitmap = BitmapFactory.decodeStream((InputStream) new URL(url[0]).getContent());

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(bitmap);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row);




        cardName = findViewById(R.id.card_name);
        cardText = findViewById(R.id.card_text);
        cardAttack = findViewById(R.id.card_attack);
        cardHealth = findViewById(R.id.card_health);
        cardImage = findViewById(R.id.card_image);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String imgURL = "https://art.hearthstonejson.com/v1/render/latest/enUS/512x/" + id + ".png";

        cardName.setText(intent.getStringExtra("name"));
        cardText.setText(intent.getStringExtra("text"));
        String attackText = String.valueOf(intent.getIntExtra("attack", 0)) ;
        cardAttack.setText("Attack: " + attackText);
        String healthText = String.valueOf(intent.getIntExtra("health", 0));
        cardHealth.setText("Health: " + healthText);

        getImage imageGetter = new getImage(cardImage, this);
        imageGetter.execute(imgURL);



    }
}