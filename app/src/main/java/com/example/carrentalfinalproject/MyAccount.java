package com.example.carrentalfinalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccount extends BaseActivity {

    private static final int PICK_IMAGE = 1;
    private Uri selectedImageUri;

    ImageView imageView, editImageView;
    TextView userFullName ,email, phoneNumber,userName;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        EdgeToEdge.enable(this);


//        // Retrieve user information from SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
//        String actualName = sharedPreferences.getString("actualName", "");
//        String username = sharedPreferences.getString("userName", "");
//
//        // Set the user's actual name and username to TextViews
//        userFullName.setText(actualName);
//        userName.setText(username);


        imageView = findViewById(R.id.iv_profile);
        userFullName = findViewById(R.id.userFullName);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        logOut = findViewById(R.id.btn_logOut);
        editImageView = findViewById(R.id.iv_editImageView);

        // Set logout button click listener to sign out and redirect to Login activity
            logOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MyAccount.this, Login.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navBarStatus();

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditDialog();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_account;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void openEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
        builder.setView(dialogView);

        EditText editFullName = dialogView.findViewById(R.id.edit_full_name);
        EditText editUserName = dialogView.findViewById(R.id.edit_user_name);
        ImageView editProfileImage = dialogView.findViewById(R.id.edit_profile_image);
        Button saveButton = dialogView.findViewById(R.id.button_save);

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the user details
                userFullName.setText(editFullName.getText().toString());
                userName.setText(editUserName.getText().toString());

                if (selectedImageUri != null) {
                    imageView.setImageURI(selectedImageUri);
                }

                // Optionally, save the new details to your backend or local storage
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            // Optionally, display the selected image in the dialog
            ImageView editProfileImage = findViewById(R.id.edit_profile_image);
            editProfileImage.setImageURI(selectedImageUri);
        }
    }


}
