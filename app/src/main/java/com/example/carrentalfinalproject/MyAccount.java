package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccount extends BaseActivity {

    ImageView imageView;
    TextView userName;
    TextView email, phoneNumber;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        EdgeToEdge.enable(this);

        imageView = findViewById(R.id.imageView);
        userName = findViewById(R.id.userFullName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        logOut = findViewById(R.id.btn_logOut);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MyAccount.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
               Intent intent = new Intent(MyAccount.this, MainActivity.class);
               startActivity(intent);
               finish();
                return true;

            } else if (item.getItemId() == R.id.navigation_account) {
                return true;

            } else if (item.getItemId() == R.id.searchNav){
                Intent intent = new Intent(MyAccount.this, Search.class);
                startActivity(intent);
                finish();
                return true;

            }
            else if (item.getItemId() == R.id.carNav){
                Intent intent = new Intent(MyAccount.this, Details.class);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        Intent intent = getIntent();
        String nameStr = intent.getStringExtra("Name");
        String emailStr = intent.getStringExtra("email");
        int phoneNumberInt = intent.getIntExtra("Phone Number: %d", 0);

        userName.setText(nameStr);
        email.setText(emailStr);
        phoneNumber.setText(String.format("Phone Number ", phoneNumberInt));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_account;
    }
}
