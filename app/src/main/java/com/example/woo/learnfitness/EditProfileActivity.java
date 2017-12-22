package com.example.woo.learnfitness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerExp;
    private ImageView imageViewProfile;
    private Button buttonSelect;
    private static final int REQUEST_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Edit Profile");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerExp = (Spinner)findViewById(R.id.spinnerExperience);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.spinnerExperience,android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);
        spinnerExp.setOnItemSelectedListener(this);
        spinnerExp.setAdapter(adapter);


        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        buttonSelect = (Button) findViewById(R.id.buttonSelect);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, REQUEST_PHOTO);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PHOTO && resultCode == RESULT_OK){
            if(data != null) {
                Uri uri = data.getData();
                imageViewProfile.setImageURI(uri);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
