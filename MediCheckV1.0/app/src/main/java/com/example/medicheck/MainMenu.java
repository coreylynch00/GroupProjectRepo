package com.example.medicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    //Declare variables
    Button logout, userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Initialize variables and set onClickListener
        logout = (Button) findViewById(R.id.buttonLogout);
        logout.setOnClickListener(this);
        userDetails = (Button) findViewById(R.id.buttonViewDetails);
        userDetails.setOnClickListener(this);
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
        }
    }
}

