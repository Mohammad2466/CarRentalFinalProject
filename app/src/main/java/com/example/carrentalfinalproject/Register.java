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

public class Register extends AppCompatActivity {
    private TextInputEditText editFullName, editPhoneNum, editTextEmail, editTextPassword, editConfirmPassword;
    private Button buttonRegister;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private TextView tv_signIn;
    String textFullName, textEmail, textPhoneNumber, textPassword, textConfirmPassword;

    String emailPattern = "[a-zA-Z0-9-_-]+@[a-z]+\\.+[a-z]+";
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = auth.getCurrentUser();

        // Check if the user
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
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        editFullName = findViewById(R.id.userFullName);
        editPhoneNum = findViewById(R.id.phoneNumber);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editConfirmPassword = findViewById(R.id.confirmPassword);
        buttonRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressbar);
        tv_signIn = findViewById(R.id.tv_signIn);

        tv_signIn.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });

        buttonRegister.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            textFullName = String.valueOf(editFullName.getText());
            textPhoneNumber = String.valueOf(editPhoneNum.getText());
            textEmail = String.valueOf(editTextEmail.getText());
            textPassword = String.valueOf(editTextPassword.getText());
            textConfirmPassword = String.valueOf(editConfirmPassword.getText());

            if(!TextUtils.isEmpty(textFullName)){
                if (!TextUtils.isEmpty(textPhoneNumber)){
                    if (textPhoneNumber.length() == 10){
                        if (!TextUtils.isEmpty(textEmail)){
                            if (textEmail.matches(emailPattern)){
                                if (!TextUtils.isEmpty(textPassword)){
                                    if (!TextUtils.isEmpty(textConfirmPassword)){
                                        if (textConfirmPassword.equals(textPassword)){
                                            signUpUser();
                                        }else {
                                            editConfirmPassword.setError("Confirm Password and Password must be the same");
                                        }
                                    }else {
                                        editTextPassword.setError("Confirm Password cannot be empty");
                                    }

                                    }else {
                                        editTextPassword.setError("Password cannot be empty");
                                    }
                                }else {
                                    editTextEmail.setError("Enter a valid Email Address");
                            }
                            }else {
                                editTextEmail.setError("Email Address cannot be empty");
                        }
                        }else {
                            editPhoneNum.setError("Enter a valid phone number");
                    }
                    }else {
                        editPhoneNum.setError("Phone Number cannot be empty");
                }
                }else {
                    editFullName.setError("User Name cannot be empty");
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void signUpUser() {
        progressBar.setVisibility(View.VISIBLE);
        buttonRegister.setVisibility(View.INVISIBLE);

        auth.createUserWithEmailAndPassword(textEmail, textPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(Register.this, "Sign Up successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonRegister.setVisibility(View.VISIBLE);
                    }
                });
    }
}
