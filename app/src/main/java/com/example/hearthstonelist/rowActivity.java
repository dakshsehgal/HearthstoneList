package com.example.hearthstonelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class rowActivity extends AppCompatActivity {
    private TextView cardName;
    private TextView cardText;
    private TextView cardAttack;
    private TextView cardHealth;
    private ImageView cardImage;
    private Button button;
    private Card card;




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

        button = findViewById(R.id.collect_button);
        cardName = findViewById(R.id.card_name);
        cardText = findViewById(R.id.card_text);
        cardAttack = findViewById(R.id.card_attack);
        cardHealth = findViewById(R.id.card_health);
        cardImage = findViewById(R.id.card_image);



        Intent intent = getIntent();
        this.card = (Card) intent.getSerializableExtra("card");

        if (getPreferences(Context.MODE_PRIVATE).getBoolean(card.getName(), false)) {
            button.setText("Collected");
        } else {
            button.setText("Add to collection");
        }

        String id = card.getCardID();
        String imgURL = "https://art.hearthstonejson.com/v1/render/latest/enUS/512x/" + id + ".png";



        cardName.setText(card.getName());
        cardText.setText(card.getCardText());

        cardAttack.setText("Attack: " + card.getAttack());

        cardHealth.setText("Health: " + card.getHealth());




        getImage imageGetter = new getImage(cardImage, this);
        imageGetter.execute(imgURL);

    }

    public void toggleCollected(View v) {
        if (button.getText().equals("Collected")) {

            getPreferences(Context.MODE_PRIVATE).edit().putBoolean(card.getName(), false).apply();

            button.setText("Add to collection");
        } else {

            getPreferences(Context.MODE_PRIVATE).edit().putBoolean(card.getName(), true).apply();

            button.setText("Collected");

        }



    }
}