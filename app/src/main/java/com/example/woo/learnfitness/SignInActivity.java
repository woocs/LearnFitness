package com.example.woo.learnfitness;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class SignInActivity extends AppCompatActivity {
    EditText editTextName, editTextAge;
    RadioGroup radioGroupGender;
    RadioButton radioGenderButton;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        pDialog = new ProgressDialog(this);

    }

    public void saveUser(View v) {
        User user = new User();

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        radioGenderButton = (RadioButton) findViewById(selectedId);

        user.setId(editTextName.getText().toString());
        user.setName(editTextName.getText().toString());
        user.setAge(editTextAge.getText().toString());
        user.setGender(radioGenderButton.getText().toString());
        user.setExperience("Beginner");
        user.setImageprofile(String.valueOf((R.drawable.ic_account_box_black_24dp)));

        try {
            makeServiceCall(this, getString(R.string.insert_user_url), user);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void makeServiceCall(Context context, String url, final User user){
        RequestQueue queue = Volley.newRequestQueue(context);
        if (!pDialog.isShowing())
            pDialog.setMessage("Loading...");
        pDialog.show();
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
                            passData();

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
                }
            },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. ", Toast.LENGTH_LONG).show();
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", user.getId());
                    params.put("name", user.getName());
                    params.put("age", user.getAge());
                    params.put("gender", user.getGender());
                    params.put("experience", user.getExperience());
                    params.put("imgprofile", user.getImageprofile());
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

    public void passData(){

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", editTextName.getText().toString());
        editor.putString("name", editTextName.getText().toString());
        editor.putString("age", editTextAge.getText().toString());
        editor.putString("gender", radioGenderButton.getText().toString());
        editor.putString("exp", "Beginner");

        editor.commit();

    }

}
