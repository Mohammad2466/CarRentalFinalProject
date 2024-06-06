package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.navigation_home) {
                        if (!(BaseActivity.this instanceof MainActivity)) {
                            startActivity(new Intent(BaseActivity.this, MainActivity.class));
                            finish();
                        }
                        return true;
                    } else if (item.getItemId() == R.id.navigation_account) {
                        if (!(BaseActivity.this instanceof MyAccount)) {
                            startActivity(new Intent(BaseActivity.this, MyAccount.class));
                            finish();
                        }
                        return true;
                    }
                    return false;
                }
            });
        } else {
            // Handle the case where bottomNavigationView is null
        }
    }

    protected abstract int getLayoutResourceId();
}
