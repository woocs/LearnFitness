package com.example.woo.learnfitness;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Button buttonEditProfile, buttonMyVideo, buttonMyStory, buttonCalculateBMI;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        buttonEditProfile = (Button) view.findViewById(R.id.buttonEditProfile);
        buttonMyVideo = (Button) view.findViewById(R.id.buttonMyVideo);
        buttonMyStory = (Button) view.findViewById(R.id.buttonMyStory);
        buttonCalculateBMI = (Button) view.findViewById(R.id.buttonCalculateBMI);

        buttonEditProfile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        buttonMyVideo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), MyVideoActivity.class);
                startActivity(intent);
            }
        });

        buttonMyStory.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), MyStoryActivity.class);
                startActivity(intent);
            }
        });

        buttonCalculateBMI.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), CalculateBMIActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
