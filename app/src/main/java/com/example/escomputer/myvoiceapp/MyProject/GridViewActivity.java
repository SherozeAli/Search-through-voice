package com.example.escomputer.myvoiceapp.MyProject;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.escomputer.myvoiceapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class GridViewActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView text_img, web_img, youtube_img, map_img,playstore_img;

    public static final int YOUTUBE_REQUEST_CODE = 10;
    public static final int MAP_REQUEST_CODE = 11;
    public static final int WEB_REQUEST_CODE = 12;
    public static final int PLAYSTORE_REQUEST_CODE = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        text_img = (ImageView) findViewById(R.id.second_img);
        text_img.setOnClickListener(this);
        youtube_img = (ImageView) findViewById(R.id.youtube_img);
        youtube_img.setOnClickListener(this);
        map_img = (ImageView) findViewById(R.id.map_img);
        map_img.setOnClickListener(this);
        web_img = (ImageView) findViewById(R.id.web_img);
        web_img.setOnClickListener(this);
        playstore_img = (ImageView) findViewById(R.id.playstore_img);
        playstore_img.setOnClickListener(this);
    }

    public void getSpeechInput(int code) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, code);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case YOUTUBE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Intent intent = new Intent(Intent.ACTION_SEARCH);
                    intent.setPackage("com.google.android.youtube");
                    intent.putExtra("query", result.get(0));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            case MAP_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showMap(result.get(0).toString());
                }

                break;
            case WEB_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchWeb(result.get(0).toString());
                }

                break;
            case PLAYSTORE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String market_uri = "https://play.google.com/store/apps/details?id="+result.get(0).toString();
                     Intent intent = new Intent(Intent.ACTION_VIEW);intent.setData(Uri.parse(market_uri));
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                     startActivity(intent);
                }

                break;


        }
    }

    @Override
    public void onClick(View view) {
        if (view == text_img) {
            Intent i = new Intent(GridViewActivity.this, CameraRecognition.class);
            startActivity(i);

        }
        if (view == youtube_img) {
            getSpeechInput(YOUTUBE_REQUEST_CODE);


        }
        if (view == map_img) {

            getSpeechInput(MAP_REQUEST_CODE);

        } if (view == web_img) {

            getSpeechInput(WEB_REQUEST_CODE);

        }if (view == playstore_img) {
            Toast.makeText(this, "playstore", Toast.LENGTH_SHORT).show();

            getSpeechInput(PLAYSTORE_REQUEST_CODE);

        }


    }
    public void searchWeb(String query)
    {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }
    private void showMap(final String place) throws ActivityNotFoundException {
        try {
            Uri gmmIntentUri = Uri.parse("geo:" + "?z=10&q="
                    + place);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } catch (ActivityNotFoundException ex) {
        }
    }
}
