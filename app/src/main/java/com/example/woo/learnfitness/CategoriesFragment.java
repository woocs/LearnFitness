package com.example.woo.learnfitness;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    private Button buttonEndurance, buttonStrength, buttonBalance, buttonFlexibility;

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        buttonEndurance = (Button) view.findViewById(R.id.buttonEndurance);
        buttonStrength = (Button) view.findViewById(R.id.buttonStrength);
        buttonBalance = (Button) view.findViewById(R.id.buttonBalance);
        buttonFlexibility = (Button) view.findViewById(R.id.buttonFlexibility);

        buttonEndurance.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), CategoriesActivity.class);
                intent.putExtra("categories", "Endurance");
                startActivity(intent);
            }
        });

        buttonStrength.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), CategoriesActivity.class);
                intent.putExtra("categories", "Strength");
                startActivity(intent);
            }
        });

        buttonBalance.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), CategoriesActivity.class);
                intent.putExtra("categories", "Balance");
                startActivity(intent);
            }
        });

        buttonFlexibility.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), CategoriesActivity.class);
                intent.putExtra("categories", "Flexibility");
                startActivity(intent);
            }
        });
        return view;
    }

}
