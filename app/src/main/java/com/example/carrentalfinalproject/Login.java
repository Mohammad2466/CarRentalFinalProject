package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

  // Deceleration
    private TextInputEditText edtEmail, edtPassword;
    private Button buttonLogin;
    private FirebaseAuth auth;
    private ProgressBar progrBar;
    private TextView tv_signUp, tv_forgetPass;
    private String textEmail, textPassword;
    private String emailPattern = "[a-zA-Z0-9.-_-]+@[a-z]+\\.+[a-z]+";

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialization
        auth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        progrBar = findViewById(R.id.progressbar);
        tv_signUp = findViewById(R.id.tv_signUp);
        tv_forgetPass = findViewById(R.id.tv_forget);

        // Action event for a sign-up text
        tv_signUp.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });

        // Action event for log-in button
        buttonLogin.setOnClickListener(view -> {
            progrBar.setVisibility(View.VISIBLE);
            textEmail = String.valueOf(edtEmail.getText());
            textPassword = String.valueOf(edtPassword.getText());

            if (!TextUtils.isEmpty(textEmail)) {
                if (textEmail.matches(emailPattern)){
                    if (!TextUtils.isEmpty(textPassword)){
                        logInUser();
                    }else {
                        edtPassword.setError("Password cannot be empty");
                        progrBar.setVisibility(View.INVISIBLE);
                        buttonLogin.setVisibility(View.VISIBLE);
                    }
                }else {
                    edtEmail.setError("Enter a valid Email Address");
                    progrBar.setVisibility(View.INVISIBLE);
                    buttonLogin.setVisibility(View.VISIBLE);
                }

            }else{
                edtEmail.setError("Email Address cannot be empty");
                progrBar.setVisibility(View.INVISIBLE);
                buttonLogin.setVisibility(View.VISIBLE);
            }

        });


        // Action event for forget password text
        tv_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }


    private void logInUser() {
        progrBar.setVisibility(View.VISIBLE);
        buttonLogin.setVisibility(View.INVISIBLE);

        auth.signInWithEmailAndPassword(textEmail, textPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Login.this, "Sign In successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progrBar.setVisibility(View.INVISIBLE);
                        buttonLogin.setVisibility(View.VISIBLE);
                    }
                });


    }

}