package com.example.woo.learnfitness;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CalculateBMIActivity extends AppCompatActivity {
    private EditText editTextWeight, editTextHeight;
    private ImageView imageViewResult;
    private TextView textViewResult, textViewResultCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Calculate BMI");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_bmi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        editTextHeight = (EditText) findViewById(R.id.editTextHeight);
        imageViewResult = (ImageView) findViewById(R.id.imageViewResult);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewResultCategories = (TextView) findViewById(R.id.textViewResultCategories);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CalculateBMIActivity.this);
                builder.setTitle("BMI Categories");
                builder.setMessage("Underweight : <18.5\n" +
                        "Normal weight : 18.5–25 \n" +
                        "Overweight : 25–30\n" +
                        "Obesity : > 30");
                builder.create().show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void Calculate(View view){
        double weight, height, m, result;

        weight = Double.parseDouble(editTextWeight.getText().toString());
        height = Double.parseDouble(editTextHeight.getText().toString());

        m = height/100;
        result = weight / (m*m);
        textViewResult.setText( String.format( "Result : %.2f", result ) );
        if(result<=18.5) {
            imageViewResult.setImageResource (R.drawable.underweight);
            textViewResultCategories.setText("underweight");
        }else if (result>18.5 && result<25 )
        {
            imageViewResult.setImageResource (R.drawable.normalweight);
            textViewResultCategories.setText("normal");
        }else if (result>=25 && result <30)
        {
            imageViewResult.setImageResource (R.drawable.overweight);
            textViewResultCategories.setText("overweight");
        }
        else{
            imageViewResult.setImageResource (R.drawable.obese);
            textViewResultCategories.setText("obesity");
        }
    }

}
