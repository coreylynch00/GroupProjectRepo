package com.example.testloginreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GPDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpdetails);

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