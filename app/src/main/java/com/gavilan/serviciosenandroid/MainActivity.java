package com.gavilan.serviciosenandroid;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnPlay, btnPausa;

    public static String rut = "147258369";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btnPlay);
        btnPausa = findViewById(R.id.btnPausa);


        Intent i = new Intent(MainActivity.this, MyFirebaseService.class);
        startService(i);



        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                    startService(intent);
            }
        });

        btnPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}