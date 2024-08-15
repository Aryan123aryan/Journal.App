package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {


    //Widgets
    EditText password_create, username_create,email_create;
    Button createBtn;

    //FireBase Auth

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //FireBase Connections
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createBtn = findViewById(R.id.acc_signup_btn);
        password_create = findViewById(R.id.password_create);
        username_create = findViewById(R.id.username_create_ET);
        email_create = findViewById(R.id.email_create);

        //FireBase Auth

        firebaseAuth = FirebaseAuth.getInstance();

        //Listening for changes in authentication state and responds accordingly when the state changes

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                //Check if the user is logged in or not
                if (currentUser!=null){
                    //User Already Logged in

                }else {
                    //The user Signed out
                }
            }
        };

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(email_create.getText().toString())
                    && !TextUtils.isEmpty(username_create.getText().toString())
                        && !TextUtils.isEmpty(password_create.getText().toString())){

                    String email = email_create.getText().toString().trim();
                    String username = username_create.getText().toString().trim();
                    String pass = password_create.getText().toString().trim();

                    createUserEmailAccount(email,pass,username);




                }else {
                    Toast.makeText(SignUpActivity.this, "No Empty Fields are Allowed", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void createUserEmailAccount(String email, String pass, String username){

        if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(username)){

            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        //The user is successfully signed
                        Toast.makeText(SignUpActivity.this, "Account is Created Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUpActivity.this, JournalListActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                    else {
                        // Handle sign-up failure
                        String errorMessage = task.getException().getMessage();
                        if (errorMessage.contains("already exists")) {
                            // Account already exists
                            Toast.makeText(SignUpActivity.this, "Account already exists. Please log in.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Other errors
                            Toast.makeText(SignUpActivity.this, "Sign-up failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}