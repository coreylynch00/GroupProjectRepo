package com.example.medicheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Reviews extends AppCompatActivity implements View.OnClickListener{

    EditText editTextReview;
    Button buttonSubmitReview;
    ImageView logo;
    TextView textViewPrompt, textViewDisplayReviews;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        textViewDisplayReviews = findViewById(R.id.textViewDisplayReviews);
        textViewPrompt = findViewById(R.id.textViewPrompt);
        editTextReview = findViewById(R.id.editTextReview);
        ratingBar = findViewById(R.id.ratingBar);
        buttonSubmitReview = findViewById(R.id.buttonSubmitReview);
        logo = findViewById(R.id.imageViewLogo);
        buttonSubmitReview.setOnClickListener(this);
        logo.setOnClickListener(this);
        textViewDisplayReviews.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewLogo:
                startActivity(new Intent(Reviews.this, MainMenu.class));
                break;
            case R.id.buttonSubmitReview:
                submitReview();
                break;
            case R.id.textViewDisplayReviews:
                startActivity(new Intent(Reviews.this, ShowReviews.class));
                break;
        }
    }

    private void submitReview() {
        String review = editTextReview.getText().toString().trim();
        int rating = (int) ratingBar.getRating();

        if(review.isEmpty()){
            editTextReview.setError("You must enter a review to submit");
            editTextReview.requestFocus();
            return;
        }

        FirebaseDatabase.getInstance().getReference("Reviews")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(rating + "/5, " + review).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Reviews.this, "Review Submitted!", Toast.LENGTH_LONG).show();
                    editTextReview.setText("");
                    textViewPrompt.setText("Thank You For Your Review!");
                }
                else{
                    Toast.makeText(Reviews.this, "Failed to Submit Review!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}