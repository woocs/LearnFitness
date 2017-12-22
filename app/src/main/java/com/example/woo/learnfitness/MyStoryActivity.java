package com.example.woo.learnfitness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MyStoryActivity extends AppCompatActivity {
    private ImageView imageViewBefore, imageViewAfter;
    private Button buttonSelectBefore, buttonSelectAfter;
    private static final int REQUEST_PHOTO = 1;
    private static final int PICK_PHOTO = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("My Story");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_story);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyStoryActivity.this,StoryActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewAfter = (ImageView) findViewById(R.id.imageViewAfter);
        imageViewBefore = (ImageView) findViewById(R.id.imageViewBefore);
        buttonSelectBefore = (Button) findViewById(R.id.buttonSelectBefore);
        buttonSelectBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, REQUEST_PHOTO);
            }
        });
        buttonSelectAfter = (Button) findViewById(R.id.buttonSelectAfter);
        buttonSelectAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, PICK_PHOTO);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PHOTO && resultCode == RESULT_OK){
            if(data != null) {
                Uri uri = data.getData();
                imageViewBefore.setImageURI(uri);
            }
        }
        if(requestCode == PICK_PHOTO && resultCode == RESULT_OK){
            if(data != null) {
                Uri uri = data.getData();
                imageViewAfter.setImageURI(uri);
            }
        }
    }

}
