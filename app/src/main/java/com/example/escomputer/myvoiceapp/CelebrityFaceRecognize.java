package com.example.escomputer.myvoiceapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisInDomainResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class CelebrityFaceRecognize extends AppCompatActivity {
private VisionServiceClient visionServiceClient=new VisionServiceRestClient("415e5a928eaa4342a35def5f96c80fbe");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity_face_recognize);
        Bitmap mbitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bill4);
        ImageView imageView=(ImageView) findViewById(R.id.imageview);
        Button btnprocess=(Button) findViewById(R.id.btn);

        imageView.setImageBitmap(mbitmap);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        mbitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        final ByteArrayInputStream inputStream=new ByteArrayInputStream(outputStream.toByteArray());

        btnprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask<InputStream,String,String> recognizeCelebTask =new AsyncTask<InputStream, String, String>()

                {
                   ProgressDialog pd=new ProgressDialog(CelebrityFaceRecognize.this);

                    @Override
                    protected String doInBackground(InputStream... inputStreams) {
                     try{

                          publishProgress("Detecting ...");
                          String model="celebrities";
                         AnalysisInDomainResult analysisInDomainResult=visionServiceClient.analyzeImageInDomain(inputStreams[0],model);
                         String strResult =new Gson().toJson(analysisInDomainResult);
                         return strResult;

                     }catch (Exception e){}
                     return null;
                    }

                    @Override
                    protected void onPreExecute() {
                        pd.show();
                    }

                    @Override
                    protected void onPostExecute(String s) {
                       pd.dismiss();
                       Gson gson=new Gson();

                       AnalysisInDomainResult result=gson.fromJson(s,AnalysisInDomainResult.class);
                        TextView textView=(TextView) findViewById(R.id.textview);
                        StringBuilder stringBuilder=new StringBuilder();
                        JsonArray detectedCeleb=result.result.get("celebrities").getAsJsonArray();
                        for(JsonElement element:detectedCeleb){
                            JsonObject celeb=element.getAsJsonObject();
                            stringBuilder.append("Name"+celeb.get("name").getAsString()+"\n");



                        }
                        textView.setText(stringBuilder);

                    }

                    @Override
                    protected void onProgressUpdate(String... values) {
                        pd.setMessage(values[0]);
                    }
                };
                recognizeCelebTask.execute(inputStream);
            }
        });


    }
}
