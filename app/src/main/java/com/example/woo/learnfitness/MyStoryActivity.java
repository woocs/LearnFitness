package com.example.woo.learnfitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyStoryActivity extends AppCompatActivity {
    private ImageView imageViewBefore, imageViewAfter;
    private Button buttonSelectBefore, buttonSelectAfter;
    private static final int REQUEST_PHOTO = 1;
    private static final int PICK_PHOTO = 100;
    String Id, imageb, imagea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("My Story");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_story);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabStory = (FloatingActionButton) findViewById(R.id.fabStory);
        fabStory.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent ();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, REQUEST_PHOTO);
            }
        });
        buttonSelectAfter = (Button) findViewById(R.id.buttonSelectAfter);
        buttonSelectAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, PICK_PHOTO);
            }
        });

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        Id = preferences.getString("userId", null);

    }

    public void uploadImage(View v) {
        Image image = new Image();

        image.setId(Id);
        image.setImgbefore(imageb);
        image.setImgafter(imagea);

        try {
            makeServiceCall(this, getString(R.string.update_image_url), image);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error uploading file " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PHOTO && resultCode == RESULT_OK){
            if(data != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    imageb = getStringImage(bitmap);
                    imageViewBefore = (ImageView) findViewById(R.id.imageViewBefore);
                    imageViewBefore.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(requestCode == PICK_PHOTO && resultCode == RESULT_OK){
            if(data != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    imagea = getStringImage(bitmap);
                    imageViewAfter = (ImageView) findViewById(R.id.imageViewAfter);
                    imageViewAfter.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void makeServiceCall(Context context, String url, final Image image) {
        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("imageb", imageb);
                            editor.putString("imagea", imagea);
                            editor.commit();

                            Toast.makeText(getApplicationContext(), "Response. " + response, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("img_id", Id);
                    params.put("imgbefore", imageb);
                    params.put("imgafter", imagea);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
