package com.example.medicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    //Declare variables
    Button logout, userDetails, gpDetails, insuranceDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Initialize variables and set onClickListener
        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(this);
        userDetails = findViewById(R.id.buttonViewDetails);
        userDetails.setOnClickListener(this);
        gpDetails = findViewById(R.id.buttonGPDeatils);
        gpDetails.setOnClickListener(this);
        insuranceDetails = findViewById(R.id.buttonInsuranceDeatils);
        insuranceDetails.setOnClickListener(this);

    }

    //onClick method
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //If logout button is clicked, log user out
            case R.id.buttonLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainMenu.this, MainActivity.class));
                break;
            //If user details button is clicked
            case R.id.buttonViewDetails:
                startActivity(new Intent(MainMenu.this, UserDetails.class));
                break;
            //If GP details button clicked
            case R.id.buttonGPDeatils:
                startActivity(new Intent(MainMenu.this, GPDetails.class));
                //If Insurance details button is clicked
                break;
            case R.id.buttonInsuranceDeatils:
                startActivity(new Intent(MainMenu.this, InsuranceDetails.class));
                break;
        }
    }
}