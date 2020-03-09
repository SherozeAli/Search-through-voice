package com.example.escomputer.myvoiceapp;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.escomputer.myvoiceapp.R;

import java.util.ArrayList;
import java.util.List;
public class ButtonActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText ed;
    TextView tv;
    private static final int REQUEST_CODE = 1234;
    Button speak;
       @Override
protected void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_button);
     speak = (Button) findViewById(R.id.btn);
    ed = (EditText) this.findViewById(R.id.tf);
    speak.setOnClickListener(this);
   // tv = (TextView) this.findViewById(R.id.textView1);
    // Disable button if no recognition service is present
    PackageManager pm = getPackageManager();
    List < ResolveInfo > activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
    if (activities.size() == 0)
    {
        speak.setEnabled(false);
        speak.setText("Recognizer not present");
    }
    ed.addTextChangedListener(new TextWatcher()
    {@
            Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        // TODO Auto-generated method stub
    }@
            Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        // TODO Auto-generated method stub
    }@
            Override
    public void afterTextChanged(Editable s)
    {
        // TODO Auto-generated method stub
        speak.setEnabled(false);
    }
    });
}
    /**
     * Handle the action of the button being clicked
     */
    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }
    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }
    /**
     * Handle the results from the voice recognition activity.
     */
    @
            Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList < String > matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty())
            {
                String Query = matches.get(0);
                ed.setText(Query);
                speak.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (view==speak){
            speakButtonClicked(view);
        }
    }
}