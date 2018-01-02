package com.example.woo.learnfitness;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadVideoActivity extends AppCompatActivity {
    private EditText editTextName, editTextAge;
    private RadioGroup radioGroupGender;
    private RadioButton radioGenderButton;
    private VideoView videoView;
    private static final int REQUEST_VIDEO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void selectVideo(View v){
        Intent intent = new Intent ();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, REQUEST_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_VIDEO && resultCode == RESULT_OK){
            if(data != null) {
                Uri uri = data.getData();

                videoView = (VideoView) findViewById(R.id.videoView);
                videoView.setVideoURI(uri);
                videoView.start();
            }
        }
    }
}
