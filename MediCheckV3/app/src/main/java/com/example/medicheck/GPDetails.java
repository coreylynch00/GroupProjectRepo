package com.example.medicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    FirebaseUser gp;
    DatabaseReference reference;
    String gpID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpdetails);

        gp = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("GP");
        gpID = gp.getUid();

        TextView textViewRegisterGP = findViewById(R.id.textViewRegisterGP);
        final TextView textViewName =  findViewById(R.id.textViewName);
        final TextView textViewEmail =  findViewById(R.id.textViewEmail);
        final TextView textViewPhone =  findViewById(R.id.textViewPhone);

        ImageView logo = findViewById(R.id.imageViewLogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GPDetails.this, MainMenu.class));
            }
        });

        Button callButton =  findViewById(R.id.callButton);
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

        textViewRegisterGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GPDetails.this, RegisterGP.class));
            }
        });

        reference.child(gpID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GP gp = snapshot.getValue(GP.class);

                if(gp != null){
                    String name = gp.name;
                    String email = gp.email;
                    String number = gp.number;

                    textViewName.setText(name);
                    textViewEmail.setText(email);
                    textViewPhone.setText(number);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GPDetails.this, "Error!", Toast.LENGTH_LONG).show();
            }
        });
    }
}