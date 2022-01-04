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

public class bCancerPredict extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editTextRadius, editTextTexture, editTextPerimeter, editTextArea, editTextSmoothness;
    Button registerBCancer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcancer_predict);

        mAuth = FirebaseAuth.getInstance();
        editTextRadius = findViewById(R.id.editTextRadius);
        editTextTexture = findViewById(R.id.editTextTexture);
        editTextPerimeter = findViewById(R.id.editTextPerimeter);
        editTextArea = findViewById(R.id.editTextArea);
        editTextSmoothness = findViewById(R.id.editTextSmoothness);
        registerBCancer = findViewById(R.id.buttonRegisterBCancer);
        registerBCancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerBCancer();
            }
        });
    }

    private void registerBCancer() {
        String radius = editTextRadius.getText().toString().trim();
        String texture = editTextTexture.getText().toString().trim();
        String perimeter = editTextPerimeter.getText().toString().trim();
        String area = editTextArea.getText().toString().trim();
        String smoothness = editTextSmoothness.getText().toString().trim();

        if (radius.isEmpty()){
            editTextRadius.setError("You must input all values to proceed!");
            editTextRadius.requestFocus();
            return;
        }

        if (texture.isEmpty()){
            editTextTexture.setError("You must input all values to proceed!");
            editTextTexture.requestFocus();
            return;
        }

        if (perimeter.isEmpty()){
            editTextPerimeter.setError("You must input all values to proceed!");
            editTextPerimeter.requestFocus();
            return;
        }

        if (area.isEmpty()){
            editTextArea.setError("You must input all values to proceed!");
            editTextArea.requestFocus();
            return;
        }

        if (smoothness.isEmpty()){
            editTextSmoothness.setError("You must input all values to proceed!");
            editTextSmoothness.requestFocus();
            return;
        }


        long timestamp = System.currentTimeMillis()/1000;
        String ts = Long.toString(timestamp);
        BCancer bcancer = new BCancer(radius, texture, perimeter, area, smoothness, ts);
        FirebaseDatabase.getInstance().getReference("BCancerMedicalReport")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(bcancer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(bCancerPredict.this, "Data Successfully Received! \nPlease wait for your result to generate.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(bCancerPredict.this, MediPredict.class));
                }
                else{
                    Toast.makeText(bCancerPredict.this, "Failed to Receive Data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}