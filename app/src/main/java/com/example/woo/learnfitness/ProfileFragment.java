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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TextView textViewUserName, textViewAge, textViewGender, textViewExperience;
    private ImageView imageViewProfile;
    private Button buttonEditProfile, buttonMyVideo, buttonMyStory, buttonCalculateBMI, buttonSignIn;
    private ProgressDialog progressDialog;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        progressDialog = new ProgressDialog(getActivity());
        textViewUserName = (TextView) view.findViewById(R.id.textViewUserName);
        textViewAge = (TextView) view.findViewById(R.id.textViewAge);
        textViewGender = (TextView) view.findViewById(R.id.textViewGender);
        textViewExperience = (TextView) view.findViewById(R.id.textViewExperience);

        imageViewProfile = (ImageView) view.findViewById(R.id.imageViewProfile);

        buttonEditProfile = (Button) view.findViewById(R.id.buttonEditProfile);
        buttonMyVideo = (Button) view.findViewById(R.id.buttonMyVideo);
        buttonMyStory = (Button) view.findViewById(R.id.buttonMyStory);
        buttonSignIn=(Button)view.findViewById(R.id.buttonSingnIn);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String Id = preferences.getString("userId", null);
        String name = preferences.getString("name", null);
        String age = preferences.getString("age", null);
        String gender = preferences.getString("gender", null);
        String exp = preferences.getString("exp", null);
        String encodedImg = preferences.getString("img", null);
        textViewUserName.setText(name);
        textViewAge.setText(age);
        textViewGender.setText(gender);
        textViewExperience.setText(exp);

        if(encodedImg == null) {
            imageViewProfile.setImageResource(R.drawable.ic_account_box_black_24dp);
        }else{
            byte[] b = Base64.decode(encodedImg, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imageViewProfile.setImageBitmap(bitmap);
        }

        if(textViewUserName.getText().toString() == ""){


            buttonEditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                }
            });
            buttonMyVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                }
            });
            buttonMyStory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                }
            });

            buttonSignIn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            buttonEditProfile.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    startActivity(intent);
                }
            });

            buttonMyVideo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MyVideoActivity.class);
                    startActivity(intent);
                }
            });

            buttonMyStory.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MyStoryActivity.class);
                    startActivity(intent);
                }
            });

            buttonSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Already have an account", Toast.LENGTH_LONG).show();
                }
            });

        }

    }
}
