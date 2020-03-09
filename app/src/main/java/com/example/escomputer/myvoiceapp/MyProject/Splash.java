package com.example.escomputer.myvoiceapp.MyProject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.escomputer.myvoiceapp.MainActivity;
import com.example.escomputer.myvoiceapp.R;

public class Splash extends AppCompatActivity {
    private static int temp=3700;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {


                        Intent i = new Intent(Splash.this, OnBaordActivity.class);
                        startActivity(i);
                        finish();

                }catch (Exception e){
                    Toast.makeText(Splash.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        },temp);

    }
}
