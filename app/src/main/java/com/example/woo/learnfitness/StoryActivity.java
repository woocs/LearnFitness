package com.example.woo.learnfitness;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class StoryActivity extends AppCompatActivity {
    private EditText txtContent;
    private Button btnSave;
    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        Id = preferences.getString("userId", null);
        String story = preferences.getString("story", null);

        txtContent = (EditText) findViewById(R.id.txtContent);
        txtContent.setText(story);
        btnSave = (Button) findViewById(R.id.btnSave);

        if(txtContent.getText().toString().equalsIgnoreCase("")){
            btnSave.setText("save");
        }else{
            btnSave.setText("edit");
        }


    }

    public void saveStory(View v) {
        Story story = new Story();

        story.setId(Id);
        story.setStory(txtContent.getText().toString());

        btnSave = (Button) findViewById(R.id.btnSave);

        if(btnSave.getText().toString().equalsIgnoreCase("save")) {
            try {
                makeServiceCall(this, getString(R.string.insert_story_url), story);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            try {
                makeServiceCall2(this, getString(R.string.update_story_url), story);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void makeServiceCall(Context context, String url, final Story story){
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
                            SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("story", story.getStory());
                            editor.commit();

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
                    params.put("story_id", story.getId());
                    params.put("story", story.getStory());
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

    public void makeServiceCall2(Context context, String url, final Story story){
        RequestQueue queue = Volley.newRequestQueue(context);

        try{
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("story", story.getStory());
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Response. " + response, Toast.LENGTH_LONG).show();
                    finish();
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
                    params.put("story_id" + "", story.getId());
                    params.put("story", story.getStory());
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
