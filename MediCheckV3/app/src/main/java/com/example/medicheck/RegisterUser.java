package com.example.medicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    //Declare variables
    FirebaseAuth mAuth;
    EditText editTextName, editTextAge, editTextEmail, editTextPassword;
    Button register;
    RadioGroup genderRadioGroup, diabetesRadioGroup, heartRadioGroup;
    RadioButton genderBtn, diabetesBtn, heartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Initialize variables and set onClickListeners
        mAuth = FirebaseAuth.getInstance();
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        genderRadioGroup = (RadioGroup) findViewById(R.id.radioGroupGender);
        diabetesRadioGroup = (RadioGroup) findViewById(R.id.radioGroupDiabetes);
        heartRadioGroup = (RadioGroup) findViewById(R.id.radioGroupHeart);
        register = findViewById(R.id.buttonRegister);
        register.setOnClickListener(this);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message2");
        //myRef.setValue("hello world!");
    }

    @Override
    //onClick method
    public void onClick(View v) {
        switch (v.getId()){
            //If Register button is clicked, called registerUser() function
            case R.id.buttonRegister:
                registerUser();
                break;
        }
    }

    //registerUser() function which contains all the functionality to register a user
    private void registerUser() {
        //Get user input and cast it to strings
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        //Get user input for radio buttons
        int genderID = genderRadioGroup.getCheckedRadioButtonId();
        genderBtn = findViewById(genderID);
        String gender = genderBtn.getText().toString();
        int diabetesID = diabetesRadioGroup.getCheckedRadioButtonId();
        diabetesBtn = findViewById(diabetesID);
        String diabetes = diabetesBtn.getText().toString();
        int heartID = heartRadioGroup.getCheckedRadioButtonId();
        heartBtn = findViewById(heartID);
        String heart = heartBtn.getText().toString();


        //Check is user input is empty, if so, give error message
        if (name.isEmpty()){
            editTextName.setError("You must enter your full name!");
            editTextName.requestFocus();
            return;
        }

        //Check is user input is empty, if so, give error message
        if(age.isEmpty()){
            editTextAge.setError("You must enter your age!");
            editTextAge.requestFocus();
            return;
        }

        //Check is user input is empty, if so, give error message
        if(email.isEmpty()){
            editTextEmail.setError("You must enter an email address!");
            editTextEmail.requestFocus();
            return;
        }

        //Check email address is valid - format (@domain.com, etc.)
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Provide a valid email address!");
            editTextEmail.requestFocus();
            return;
        }

        //Check is user input is empty, if so, give error message
        if(password.isEmpty()){
            editTextPassword.setError("You must enter a password!");
            editTextPassword.requestFocus();
            return;
        }

        //Check password is greater than 6 chars
        if(password.length() < 6){
            editTextPassword.setError("Password must be more than 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        //Register user with email and password strings captured above
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If successful, create user object and pass it to Firebase
                        if(task.isSuccessful()){
                            User user = new User(name, age, email, gender, diabetes, heart);
                            //Passing user object to Firebase
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //If successfully registered, provide message
                                        Toast.makeText(RegisterUser.this, "User Successfully Registered", Toast.LENGTH_LONG).show();
                                        //Redirect to main activity to login
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                    }
                                    //If registration is unsuccessful, give error message
                                    else{
                                        Toast.makeText(RegisterUser.this, "Failed to Register", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to Register", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}