package com.example.medicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class heartDiseaseResult extends AppCompatActivity {

    FirebaseUser res;
    DatabaseReference reference;
    String resID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_result);

        res = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("HeartDiseaseResult");
        resID = res.getUid();

        final TextView textViewResult = findViewById(R.id.textViewResult);
        Button back = findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(heartDiseaseResult.this, MediPredict.class));
            }
        });

        reference.child(resID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    String result = (String) snapshot.getValue();
                    textViewResult.setText("Based on the data you have provided, you are " + result + " to have heart disease.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(heartDiseaseResult.this, "Error!", Toast.LENGTH_LONG).show();

            }
        });
    }
}