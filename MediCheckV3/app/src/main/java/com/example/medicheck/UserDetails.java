package com.example.medicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class UserDetails extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference reference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        //Initialize variables
        //Get the instance of the current user who is logged in
        user = FirebaseAuth.getInstance().getCurrentUser();
        //Getting reference of user collections
        reference = FirebaseDatabase.getInstance().getReference("Users");
        //Get the unique ID of logged in user
        userID = user.getUid();

        final TextView textViewName = findViewById(R.id.textViewName);
        final TextView textViewDob = findViewById(R.id.textViewDob);
        final TextView textViewEmail = findViewById(R.id.textViewEmail);
        final TextView textViewMessage  = findViewById(R.id.textViewMessage);
        final TextView textViewGender = findViewById(R.id.textViewGender);
        final TextView textViewDiabetes = findViewById(R.id.textViewDiabetes);
        final TextView textViewHeart = findViewById(R.id.textViewHeart);

        ImageView logo = findViewById(R.id.imageViewLogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDetails.this, MainMenu.class));
            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user != null){
                    String name = user.name;
                    String dob = user.dob;
                    String email = user.email;
                    String gender = user.gender;
                    String diabetes = user.diabetes;
                    String heart = user.heart;

                    textViewMessage.setText("Welcome, " + name + ", here are your details on record:");
                    textViewName.setText(name);
                    textViewDob.setText(dob);
                    textViewEmail.setText(email);
                    textViewGender.setText(gender);
                    textViewDiabetes.setText(diabetes);
                    textViewHeart.setText(heart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDetails.this, "Error!", Toast.LENGTH_LONG).show();
            }
        });
    }
}