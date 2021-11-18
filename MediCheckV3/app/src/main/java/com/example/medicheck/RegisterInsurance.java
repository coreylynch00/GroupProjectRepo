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

public class RegisterInsurance extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    EditText editTextNameIns, editTextEmailIns, editTextNumberIns;
    Button registerIns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_insurance);

        mAuth = FirebaseAuth.getInstance();
        editTextNameIns = findViewById(R.id.editTextNameIns);
        editTextEmailIns = findViewById(R.id.editTextEmailIns);
        editTextNumberIns = findViewById(R.id.editTextNumberIns);
        registerIns = findViewById(R.id.buttonRegisterIns);
        registerIns.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonRegisterIns:
                registerInsurance();
                break;
    }
}

    private void registerInsurance() {
        String name = editTextNameIns.getText().toString().trim();
        String email = editTextEmailIns.getText().toString().trim();
        String number = editTextNumberIns.getText().toString().trim();

        if (name.isEmpty()){
            editTextNameIns.setError("You must enter GP full name!");
            editTextNameIns.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmailIns.setError("You must enter GP email address!");
            editTextEmailIns.requestFocus();
            return;
        }

        if(number.isEmpty()){
            editTextNumberIns.setError("You must enter GP Phone Number!");
            editTextNumberIns.requestFocus();
            return;
        }

        Insurance ins = new Insurance(name, email, number);
        FirebaseDatabase.getInstance().getReference("Insurance")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(ins).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterInsurance.this, "Provider Successfully Registered", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterInsurance.this, InsuranceDetails.class));
                }
                else{
                    Toast.makeText(RegisterInsurance.this, "Failed to Register", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}