package com.example.escomputer.myvoiceapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.sql.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
  private TextToSpeech mytts;
  private SpeechRecognizer myspeechrecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                myspeechrecognizer.startListening(intent);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

    myInitializeTTS();
    myInitializeRecognizer();
    }
    private void myInitializeTTS(){
        mytts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mytts.getEngines().size()==0){
                    Toast.makeText(MainActivity.this, "There's no engine on your device", Toast.LENGTH_SHORT).show();
                    //finish();
                }
                else{
                    mytts.setLanguage(Locale.US);
                mySpeak("HELLO THIS SHEROZE FIRST ");
                }
            }
        });

    }
    private void myInitializeRecognizer(){

        if(SpeechRecognizer.isRecognitionAvailable(this)){
            myspeechrecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            myspeechrecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int i) {

                }

                @Override
                public void onResults(Bundle bundle) {
                    List<String> results=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    myProcessResult(results.get(0));
                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });

        }

    }
    private void myProcessResult(String command){
        Toast.makeText(this, "my process k undr", Toast.LENGTH_SHORT).show();
        command=command.toLowerCase();
        if(command.indexOf("what")!=-1){
            if(command.indexOf("your name")!=-1){
                mySpeak("My name is Emma");
            }


        }
        if(command.indexOf("time")!=-1){
           // Date date=new Date();
           // String time = DateUtils.formatDateTime(this,date.getTime(),DateUtils.FORMAT_SHOW_TIME);
           // mySpeak("The time is "+ time);

        }
        else if(command.indexOf("open")!=-1){
            if(command.indexOf("browser")!=-1){
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                startActivity(intent);
            }
        }
    }
private void mySpeak(String message){
        if(Build.VERSION.SDK_INT>=21){
            mytts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);

        }
        else{
            mytts.speak(message,TextToSpeech.QUEUE_FLUSH,null);
        }

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mytts.shutdown();
    }
}
