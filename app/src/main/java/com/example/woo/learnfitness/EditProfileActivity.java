package com.example.woo.learnfitness;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editTextName, editTextAge;
    RadioGroup radioGroupGender;
    RadioButton radioGenderButton;
    private Spinner spinnerExp;
    private ImageView imageViewProfile;
    private static final int REQUEST_PHOTO = 1;
    String Id,image;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Edit Profile");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pDialog = new ProgressDialog(this);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        spinnerExp = (Spinner)findViewById(R.id.spinnerExperience);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.spinnerExperience,android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);
        spinnerExp.setOnItemSelectedListener(this);
        spinnerExp.setAdapter(adapter);
        String e = spinnerExp.getSelectedItem().toString();
        editTextName.setText(e);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        Id = preferences.getString("userId", null);
        String name = preferences.getString("name", null);
        String age = preferences.getString("age", null);
        String gender = preferences.getString("gender", null);
        String exp = preferences.getString("exp", null);
        String encodedImg = preferences.getString("img", null);

        editTextName.setText(name);
        editTextAge.setText(age);
        if(gender.equalsIgnoreCase("male")){
            RadioButton radioButtonM = (RadioButton) findViewById(R.id.radioButtonMale);
            radioButtonM.setChecked(true);
        }else{
            RadioButton radioButtonF = (RadioButton) findViewById(R.id.radioButtonFemale);
            radioButtonF.setChecked(true);
        }
        if(exp.equalsIgnoreCase("beginner")) {
            spinnerExp.setSelection(1);
        }else if(exp.equalsIgnoreCase("intermediate")){
            spinnerExp.setSelection(2);
        }else if(exp.equalsIgnoreCase("advanced")){
            spinnerExp.setSelection(3);
        }else{
            spinnerExp.setSelection(0);
        }

        if(encodedImg == null) {
            imageViewProfile.setImageResource(R.drawable.ic_account_box_black_24dp);
        }else{
            byte[] b = Base64.decode(encodedImg, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imageViewProfile.setImageBitmap(bitmap);
            image = getStringImage(bitmap);
        }

    }

    public void selectImage(View v){
        Intent intent = new Intent ();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, REQUEST_PHOTO);
    }

    public void updateUser(View v) {
        User user = new User();
        String experience = spinnerExp.getSelectedItem().toString();

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        radioGenderButton = (RadioButton) findViewById(selectedId);
        user.setId(Id);
        user.setName(editTextName.getText().toString());
        user.setAge(editTextAge.getText().toString());
        user.setGender(radioGenderButton.getText().toString());
        user.setExperience(experience);
        user.setImageprofile(image);

        try {
            makeServiceCall(this, getString(R.string.update_user_url), user);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String getStringImage(Bitmap bmp){
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

                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    image = getStringImage(bitmap);
                    imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
                    imageViewProfile.setImageBitmap(bitmap);
                }catch(IOException e){
                    e.printStackTrace();
                }

                imageViewProfile.setImageURI(uri);
            }
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

                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userId", user.getId());
                    editor.putString("name", user.getName());
                    editor.putString("age", user.getAge());
                    editor.putString("gender", user.getGender());
                    editor.putString("exp", user.getExperience());
                    editor.putString("img", user.getImageprofile());

                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Response. " + response, Toast.LENGTH_LONG).show();
                    finish();
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
                    params.put("user_id" + "", user.getId());
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
