package com.example.escomputer.myvoiceapp.MyProject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.escomputer.myvoiceapp.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Locale;

public class CameraRecognition extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textview;
    final int RequestCameraPermissionID = 1001;
    CameraSource cameraSource;

    TextToSpeech toSpeech;
    EditText editText;
    String text;
    int result;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case RequestCameraPermissionID:{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                 return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_recognition);
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textview = (TextView) findViewById(R.id.textview);
        toSpeech=new TextToSpeech(CameraRecognition.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    result=toSpeech.setLanguage(Locale.UK);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w("CameraRecognition", "Detector dependies are not yet available");

        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                           ActivityCompat.requestPermissions(CameraRecognition.this,
                                   new String[]{Manifest.permission.CAMERA},
                            RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                 }catch (Exception e){
                     Toast.makeText(CameraRecognition.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();}
         }

         @Override
         public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
             try{


             }catch (Exception e){
                 Toast.makeText(CameraRecognition.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();}
         }

         @Override
         public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
             try{
  cameraSource.stop();

             }catch (Exception e){
                 Toast.makeText(CameraRecognition.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();}
         }
     });
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                      final SparseArray<TextBlock> items= detections.getDetectedItems();
                      if(items.size()!=0){
                          textview.post(new Runnable() {
                              @Override
                              public void run() {
                           StringBuilder stringBuilder=new StringBuilder();
                           for(int i=0; i <items.size(); ++i){
                               TextBlock item=items.valueAt(i);
                               stringBuilder.append(item.getValue());
                               stringBuilder.append("\n");

                           }
                           textview.setText(stringBuilder.toString());
                                  if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                                  {
                                      Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
                                  }
                                  else
                                  {
                                      text=textview.getText().toString();
                                      toSpeech.speak( text,TextToSpeech.QUEUE_FLUSH,null);
                                      //textview.setText("");
                                  }
                              }
                          });
                      }
                }
            });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toSpeech!=null)
        {
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }
}
