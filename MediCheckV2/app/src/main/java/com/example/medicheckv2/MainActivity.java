package com.example.medicheckv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Declare Variables
    TextView register, forgotPassword;
    EditText editTextEmail, editTextPassword;
    Button login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize variables & set onClickListeners
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);
        register = (TextView) findViewById(R.id.textViewRegister);
        register.setOnClickListener(this);
        forgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        forgotPassword.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    //onClick method
    public void onClick(View v) {
        //Switch statement to determine what action to take depending on what is clicked
        switch (v.getId()){
            //If register is clicked, redirect to register activity
            case R.id.textViewRegister:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            //If login is clicked, call the userLogin function
            case R.id.buttonLogin:
                userLogin();
                break;
            //If forgot password is clicked, redirect to forgot password activity
            case R.id.textViewForgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    //userLogin function - contains actions to perform the user login
    private void userLogin() {
        //Cast user inputs to strings
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //Check is email is provided, if not, give error
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        //Check is password is provided, if not, give error
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        //Check password length is greater than 6 chars
        if(password.length() < 6){
            editTextPassword.setError("Password must be over 6 characters!");
        }

        //Sign in with Firebase using the strings above
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Check is login is successful, if so continue user to main menu
                if(task.isSuccessful()){
                    //Redirect to main menu
                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                }
                //If login fails, show error message
                else{
                    Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}