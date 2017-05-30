package com.academiamoviles.seminario;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // abrir otra actividad
                Intent miIntent = new Intent(SplashActivity.this, MensajesActivity.class);
                startActivity(miIntent);
                finish();
            }
        }, 3 * 1000); // secs

    }
}
