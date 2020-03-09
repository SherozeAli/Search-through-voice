package com.example.escomputer.myvoiceapp.MyProject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.escomputer.myvoiceapp.R;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class OnBaordActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addFragment(new Step.Builder().setTitle("Voice Search")
                .setContent("You can search anything you want like videos on Youtube, audio search on Web etc")
                .setBackgroundColor(Color.parseColor("#07136c")) // int background color
                .setDrawable(R.color.blue) // int top drawable
                .setSummary("Search your phone faster")
                .setDrawable(R.drawable.v1)
                .build());
        addFragment(new Step.Builder().setTitle("Location Search")
                .setContent("You can search any location on google map through voice.")
                .setBackgroundColor(Color.parseColor("#07136c")) // int background color
                .setDrawable(R.drawable.map3) // int top drawable
                .setSummary("Location search has now become easier than ever")
                .build());
        addFragment(new Step.Builder().setTitle("Text Recognition")
                .setContent("Text can be recognize through camera and it is furthur implemented to text to speech feature")
                .setBackgroundColor(Color.parseColor("#07136c")) // int background color
                .setDrawable(R.drawable.t7) // int top drawable
                .setSummary("Through camera Text recognition and TextToSpeech")
                .build());
        // Permission Step

    }

    @Override
    public void currentFragmentPosition(int i) {

    }
    @Override
    public void finishTutorial() {
        Intent i =new Intent(OnBaordActivity.this,GridViewActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void setCancelText(String text) {
        Toast.makeText(this, "cancel pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNextText(String text) {
        Toast.makeText(this, "next pressed", Toast.LENGTH_SHORT).show();
    }
}
