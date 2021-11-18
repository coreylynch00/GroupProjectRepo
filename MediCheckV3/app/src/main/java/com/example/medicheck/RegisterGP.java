package com.example.medicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterGP extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    EditText editTextNameGP, editTextEmailGP, editTextNumberGP;
    Button registerGP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_gp);

        mAuth = FirebaseAuth.getInstance();
        editTextNameGP = findViewById(R.id.editTextNameGP);
        editTextEmailGP = findViewById(R.id.editTextEmailGP);
        editTextNumberGP = findViewById(R.id.editTextNumberGP);
        registerGP = findViewById(R.id.buttonRegisterGP);
        registerGP.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegisterGP:
                registerGP();
                break;
        }
    }

    private void registerGP() {
        String name = editTextNameGP.getText().toString().trim();
        String email = editTextEmailGP.getText().toString().trim();
        String number = editTextNumberGP.getText().toString().trim();

        if (name.isEmpty()){
            editTextNameGP.setError("You must enter GP full name!");
            editTextNameGP.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmailGP.setError("You must enter GP email address!");
            editTextEmailGP.requestFocus();
            return;
        }

        if(number.isEmpty()){
            editTextNumberGP.setError("You must enter GP Phone Number!");
            editTextNumberGP.requestFocus();
            return;
        }

        GP gp = new GP(name, email, number);
        FirebaseDatabase.getInstance().getReference("GP")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(gp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterGP.this, "GP Successfully Registered", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterGP.this, GPDetails.class));
                }
                else{
                    Toast.makeText(RegisterGP.this, "Failed to Register", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}