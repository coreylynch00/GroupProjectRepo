package com.example.medicheckv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        final TextView textViewName = (TextView) findViewById(R.id.textViewName);
        final TextView textViewAge = (TextView) findViewById(R.id.textViewAge);
        final TextView textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        final TextView textViewMessage  = (TextView) findViewById(R.id.textViewMessage);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user != null){
                    String name = user.name;
                    String age = user.age;
                    String email = user.email;

                    textViewMessage.setText("Welcome, " + name + " , here are your details on record:");
                    textViewName.setText(name);
                    textViewAge.setText(age);
                    textViewEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDetails.this, "Error!", Toast.LENGTH_LONG).show();
            }
        });
    }
}