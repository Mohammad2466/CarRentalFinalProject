package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private Button restart, back;
    private EditText edt_email;
    ProgressBar forgetProgressBar;
    FirebaseAuth auth;

    String emailtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        restart = findViewById(R.id.btn_restartPassword);
        back = findViewById(R.id.btn_backOption);
        edt_email = findViewById(R.id.edt_forgetPassword);
        forgetProgressBar = findViewById(R.id.forgetPasswordProgressBar);

        auth = FirebaseAuth.getInstance();

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailtxt = String.valueOf(edt_email.getText());
                if (!TextUtils.isEmpty(emailtxt)){
                    resetPassword();
                }else {
                    edt_email.setError("Email address cannot be empty");
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void resetPassword() {
        forgetProgressBar.setVisibility(View.VISIBLE);
        restart.setVisibility(View.INVISIBLE);

        auth.sendPasswordResetEmail(emailtxt).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgetPassword.this, "Reset password link has been sent to your register email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgetPassword.this, Login.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPassword.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                forgetProgressBar.setVisibility(View.INVISIBLE);
                restart.setVisibility(View.VISIBLE);
            }
        });
    }
}