package com.example.medicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

public class InsuranceDetails extends AppCompatActivity {

    FirebaseUser ins;
    DatabaseReference reference;
    String insID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        ins = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Insurance");
        insID = ins.getUid();

        TextView textViewRegisterIns = findViewById(R.id.textViewRegisterIns);
        final TextView textViewName = findViewById(R.id.textViewName);
        final TextView textViewEmail = findViewById(R.id.textViewEmail);
        final TextView textViewPhone = findViewById(R.id.textViewPhone);

        ImageView logo = findViewById(R.id.imageViewLogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsuranceDetails.this, MainMenu.class));
            }
        });

        Button callButton = findViewById(R.id.callButton);
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
        textViewRegisterIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsuranceDetails.this, RegisterInsurance.class));
            }
        });
        reference.child(insID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Insurance ins = snapshot.getValue(Insurance.class);

                if(ins != null){
                    String name = ins.name;
                    String email = ins.email;
                    String number = ins.number;

                    textViewName.setText(name);
                    textViewEmail.setText(email);
                    textViewPhone.setText(number);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InsuranceDetails.this, "Error!", Toast.LENGTH_LONG).show();
            }
        });
    }
}