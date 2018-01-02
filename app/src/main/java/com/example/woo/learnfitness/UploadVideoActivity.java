package com.example.woo.learnfitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

public class UploadVideoActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private RadioGroup radioGroupCategories;
    private RadioButton radioButtonCategories;
    private VideoView videoView;
    private static final int REQUEST_VIDEO = 1;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    String Id, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextTitle = (EditText) findViewById(R.id.editTextVideoTitle);

        int permissionCheck = ContextCompat.checkSelfPermission(UploadVideoActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UploadVideoActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

    }

    public void selectVideo(View v){
        Intent intent = new Intent ();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, REQUEST_VIDEO);
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    selectVideo(null);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_VIDEO && resultCode == RESULT_OK){
            if(data != null) {
                Uri uri = data.getData();

                videoView = (VideoView) findViewById(R.id.videoView);
                path = getPath(uri);
                videoView.setVideoPath(path);
                videoView.start();

            }
        }
    }

    public void uploadVideo(View v){
        Video video = new Video();
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        Id = preferences.getString("userId", null);

        radioGroupCategories = (RadioGroup) findViewById(R.id.radioGroupVideoCategories);
        int selectedId = radioGroupCategories.getCheckedRadioButtonId();
        radioButtonCategories = (RadioButton) findViewById(selectedId);

        video.setId(Id);
        video.setVideo(path);
        video.setTitle(editTextTitle.getText().toString());
        video.setCategories(radioButtonCategories.getText().toString());
        video.setFavorite("not");
        video.setLike("0");

        try {
            makeServiceCall(this, getString(R.string.insert_video_url), video);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url, final Video video){
        RequestQueue queue = Volley.newRequestQueue(context);

        try{
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(response);
                        int success = jsonObject.getInt("success");
                        String message = jsonObject.getString("message");
                        if (success == 0) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. ", Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("video", path);
                    params.put("title", video.getTitle());
                    params.put("categories", video.getCategories());
                    params.put("favorite", video.getFavorite());
                    params.put("like", video.getLike());
                    params.put("user_id", Id);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
