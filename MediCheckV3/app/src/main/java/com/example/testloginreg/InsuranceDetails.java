package com.example.testloginreg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InsuranceDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        final TextView textViewName = (TextView) findViewById(R.id.textViewName);
        final TextView textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        final TextView textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        Button callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get String
                String num = textViewPhone.getText().toString().trim();
                //Parse String
                Uri uri = Uri.parse(num);
                //Create Intent
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                //Set intent data
                intent.setData(Uri.parse("tel:" + num));
                //Start Activity
                startActivity(intent);
            }
        });
    }
}