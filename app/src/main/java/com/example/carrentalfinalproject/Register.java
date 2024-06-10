package com.example.carrentalfinalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
    private TextInputEditText editFullName,editUserName, editPhoneNum, editTextEmail,editCardNumber, editTextPassword, editConfirmPassword;
    private Button buttonRegister;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private TextView tv_signIn;
    private String textEmail;
    private String textPassword;
    private int id;
    private SharedPreferences sharedPreferences;
    public static final String myPreferences = "MyPref";

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        initLayoutCompo();

        sharedPreferences =  getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        buttonRegister.setOnClickListener(view -> {checkValidationInput();});

        tv_signIn.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });


    }


    private void signUpUser() {

        progressBar.setVisibility(View.VISIBLE);
        buttonRegister.setVisibility(View.INVISIBLE);

        auth.createUserWithEmailAndPassword(this.textEmail, textPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser firebaseUser = authResult.getUser();
                        if (firebaseUser != null) {
                            sharedPreferences.edit().putString("uid",firebaseUser.getUid()).apply();
/*
                            Toast.makeText(getApplicationContext(),"signed up successfully",Toast.LENGTH_SHORT).show();
*/

                            addUser();




                        }
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


    private void addUser(){
        String url = MainActivity.addUser_URL;
        RequestQueue queue = Volley.newRequestQueue(Register.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    String msg = jsonObject.getString("message");
                    Toast.makeText(Register.this,msg
                            , Toast.LENGTH_SHORT).show();
                    if(!jsonObject.getBoolean("error")){
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(Register.this,"SomeThing went wrong , try later!!"
                                , Toast.LENGTH_SHORT).show();
                        Objects.requireNonNull(auth.getCurrentUser()).delete();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    buttonRegister.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    Toast.makeText(Register.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Register.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(auth.getCurrentUser()).delete();
                progressBar.setVisibility(View.INVISIBLE);
                buttonRegister.setVisibility(View.VISIBLE);
            }

        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();



                String name = sharedPreferences.getString("FullName","unKnown");
                String userName = sharedPreferences.getString("UserName","unKnown");
                String email = textEmail;
                String phoneNumber = sharedPreferences.getString("PhoneNumber","unKnown");
                String cardinfo = sharedPreferences.getString("Card","unKnown");

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("uid", auth.getUid());
                params.put("name", name);
                params.put("userName", userName);
                params.put("email", email);
                params.put("phoneNumber", phoneNumber);
                params.put("cardInfo", cardinfo);


                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void initLayoutCompo() {
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("users");

        editFullName = findViewById(R.id.userFullName);
        editUserName = findViewById(R.id.userName);
        editPhoneNum = findViewById(R.id.phoneNumber);
        editTextEmail = findViewById(R.id.email);
        editCardNumber = findViewById(R.id.creditCardInfo);
        editTextPassword = findViewById(R.id.password);
        editConfirmPassword = findViewById(R.id.confirmPassword);
        buttonRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressbar);
        tv_signIn = findViewById(R.id.tv_signIn);
    }
    private void checkValidationInput() {
        progressBar.setVisibility(View.VISIBLE);
        String textFullName = Objects.requireNonNull(editFullName.getText()).toString();
        String textUserName = Objects.requireNonNull(editUserName.getText()).toString();
        String textPhoneNumber = Objects.requireNonNull(editPhoneNum.getText()).toString();
        textEmail = (Objects.requireNonNull(editTextEmail.getText()).toString());
        String creditCardNumber = Objects.requireNonNull(editCardNumber.getText()).toString();
        textPassword = Objects.requireNonNull(editTextPassword.getText()).toString();
        String textConfirmPassword = Objects.requireNonNull(editConfirmPassword.getText()).toString();


        if (TextUtils.isEmpty(textFullName)) {
            editFullName.setError("Full Name cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(textUserName)) {
            editUserName.setError("User Name cannot be empty");
            return;
        }
        if (TextUtils.isEmpty(creditCardNumber)) {
            editCardNumber.setError("Card number  cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(textPhoneNumber)) {
            editPhoneNum.setError("Phone Number cannot be empty");
            return;
        }

        if (textPhoneNumber.length() != 10) {
            editPhoneNum.setError("Enter a valid phone number");
            return;
        }



        if (TextUtils.isEmpty(textEmail)) {
            editTextEmail.setError("Email Address cannot be empty");
            return;
        }

        String emailPattern = "[a-zA-Z0-9-_-]+@[a-z]+\\.+[a-z]+";
        if (!textEmail.matches(emailPattern)) {
            editTextEmail.setError("Enter a valid Email Address");
            return;
        }

        if (TextUtils.isEmpty(textPassword)) {
            editTextPassword.setError("Password cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(textConfirmPassword)) {
            editTextPassword.setError("Confirm Password cannot be empty");
            return;
        }

        if (!textConfirmPassword.equals(textPassword)) {
            editConfirmPassword.setError("Confirm Password and Password must be the same");
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FullName",textFullName);
        editor.putString("UserName",textUserName);
        editor.putString("PhoneNumber",textPhoneNumber);
        editor.putString("Card",creditCardNumber);
        editor.apply();
        signUpUser();

    }

}
