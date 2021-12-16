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

import java.util.Locale;

public class heartDiseasePredict extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText editTextAge, editTextCP, editTextTRestBPS, editTextChol, editTextFBS, editTextRestECG, editTextThalach, editTextExang, editTextOldpeak, editTextSlope, editTextCA, editTextThal;
    Button registerHeart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_disease_predict);

        mAuth = FirebaseAuth.getInstance();
        editTextAge = findViewById(R.id.editTextAge);
        editTextCP = findViewById(R.id.editTextCP);
        editTextTRestBPS = findViewById(R.id.editTextTRestBPS);
        editTextChol = findViewById(R.id.editTextChol);
        editTextFBS = findViewById(R.id.editTextFBS);
        editTextRestECG = findViewById(R.id.editTextRestECG);
        editTextThalach = findViewById(R.id.editTextThalach);
        editTextExang = findViewById(R.id.editTextExang);
        editTextOldpeak = findViewById(R.id.editTextOldpeak);
        editTextSlope = findViewById(R.id.editTextSlope);
        editTextCA = findViewById(R.id.editTextCA);
        editTextThal = findViewById(R.id.editTextThal);
        registerHeart = findViewById(R.id.buttonRegisterHeart);
        registerHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerHeart();
            }
        });


    }

    private void registerHeart() {
        String age = editTextAge.getText().toString().trim();
        String cp = editTextCP.getText().toString().trim();
        String trestbps = editTextTRestBPS.getText().toString().trim();
        String chol = editTextChol.getText().toString().trim();
        String fbs = editTextFBS.getText().toString().trim();
        String restecg = editTextRestECG.getText().toString().trim();
        String thalach = editTextThalach.getText().toString().trim();
        String exang = editTextExang.getText().toString().trim();
        String oldpeak = editTextOldpeak.getText().toString().trim();
        String slope = editTextSlope.getText().toString().trim();
        String ca = editTextCA.getText().toString().trim();
        String thal = editTextThal.getText().toString().trim();

        if (age.isEmpty()) {
            editTextAge.setError("You must input all values to proceed!");
            editTextAge.requestFocus();
            return;
        }

        if (cp.isEmpty()) {
            editTextCP.setError("You must input all values to proceed!");
            editTextCP.requestFocus();
            return;
        }

        if (trestbps.isEmpty()) {
            editTextTRestBPS.setError("You must input all values to proceed!");
            editTextTRestBPS.requestFocus();
            return;
        }

        if (chol.isEmpty()) {
            editTextChol.setError("You must input all values to proceed!");
            editTextChol.requestFocus();
            return;
        }

        if (fbs.isEmpty()) {
            editTextFBS.setError("You must input all values to proceed!");
            editTextFBS.requestFocus();
            return;
        }

        if (restecg.isEmpty()) {
            editTextRestECG.setError("You must input all values to proceed!");
            editTextRestECG.requestFocus();
            return;
        }

        if (thalach.isEmpty()) {
            editTextThalach.setError("You must input all values to proceed!");
            editTextThalach.requestFocus();
            return;
        }

        if (exang.isEmpty()) {
            editTextExang.setError("You must input all values to proceed!");
            editTextExang.requestFocus();
            return;
        }

        if (oldpeak.isEmpty()) {
            editTextOldpeak.setError("You must input all values to proceed!");
            editTextOldpeak.requestFocus();
            return;
        }

        if (slope.isEmpty()) {
            editTextSlope.setError("You must input all values to proceed!");
            editTextSlope.requestFocus();
            return;
        }

        if (ca.isEmpty()) {
            editTextCA.setError("You must input all values to proceed!");
            editTextCA.requestFocus();
            return;
        }

        if (thal.isEmpty()) {
            editTextThal.setError("You must input all values to proceed!");
            editTextThal.requestFocus();
            return;
        }

        long timestamp = System.currentTimeMillis()/1000;
        String ts = Long.toString(timestamp);
        HeartDisease heartDisease = new HeartDisease(age, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal, ts);
        FirebaseDatabase.getInstance().getReference("HeartDiseaseMedicalReport")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(heartDisease).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(heartDiseasePredict.this, "Data Successfully Received! \nPlease wait for your result to generate.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(heartDiseasePredict.this, MediPredict.class));
                }
                else{
                    Toast.makeText(heartDiseasePredict.this, "Failed to Receive Data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}