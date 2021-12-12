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

public class diabetesPredict extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    EditText editTextAge, editTextPregnancies, editTextGlucose, editTextBP, editTextSkinThickness, editTextInsulin, editTextBMI, editTextDPF;
    Button registerDiabetes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_predict);

        mAuth = FirebaseAuth.getInstance();
        editTextAge = findViewById(R.id.editTextAge);
        editTextPregnancies = findViewById(R.id.editTextPregnancies);
        editTextGlucose = findViewById(R.id.editTextGlucose);
        editTextBP = findViewById(R.id.editTextBP);
        editTextSkinThickness = findViewById(R.id.editTextSkinThickness);
        editTextInsulin = findViewById(R.id.editTextInsulin);
        editTextBMI = findViewById(R.id.editTextBMI);
        editTextDPF = findViewById(R.id.editTextDPF);
        registerDiabetes = findViewById(R.id.buttonRegisterDiabetes);
        registerDiabetes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegisterDiabetes:
                registerDiabetes();
                break;
        }
    }

    private void registerDiabetes() {
        String age = editTextAge.getText().toString().trim();
        String pregnancies = editTextPregnancies.getText().toString().trim();
        String glucose = editTextGlucose.getText().toString().trim();
        String bp = editTextBP.getText().toString().trim();
        String skinThickness = editTextSkinThickness.getText().toString().trim();
        String insulin = editTextInsulin.getText().toString().trim();
        String bmi = editTextBMI.getText().toString().trim();
        String dpf = editTextDPF.getText().toString().trim();

        if (age.isEmpty()){
            editTextAge.setError("You must input all values to proceed!");
            editTextAge.requestFocus();
            return;
        }

        if (pregnancies.isEmpty()){
            editTextPregnancies.setError("You must input all values to proceed!");
            editTextPregnancies.requestFocus();
            return;
        }

        if (glucose.isEmpty()){
            editTextGlucose.setError("You must input all values to proceed!");
            editTextGlucose.requestFocus();
            return;
        }

        if (bp.isEmpty()){
            editTextBP.setError("You must input all values to proceed!");
            editTextBP.requestFocus();
            return;
        }

        if (skinThickness.isEmpty()){
            editTextSkinThickness.setError("You must input all values to proceed!");
            editTextSkinThickness.requestFocus();
            return;
        }

        if (insulin.isEmpty()){
            editTextInsulin.setError("You must input all values to proceed!");
            editTextInsulin.requestFocus();
            return;
        }

        if (bmi.isEmpty()){
            editTextBMI.setError("You must input all values to proceed!");
            editTextBMI.requestFocus();
            return;
        }

        if (dpf.isEmpty()){
            editTextDPF.setError("You must input all values to proceed!");
            editTextDPF.requestFocus();
            return;
        }

        Diabetes diabetes = new Diabetes(age, pregnancies, glucose, bp, skinThickness, insulin, bmi, dpf);
        FirebaseDatabase.getInstance().getReference("DiabetesMedicalReport")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(diabetes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(diabetesPredict.this, "Data Successfully Received! \nPlease wait for your result to generate.", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(diabetesPredict.this, diabetesResult.class));
                }
                else{
                    Toast.makeText(diabetesPredict.this, "Failed to Receive Data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
