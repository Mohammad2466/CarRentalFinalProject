
package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(getLayoutResourceId());
       // navBarStatus();


    }

    protected abstract int getLayoutResourceId();

    protected void navBarStatus() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(getIntent().getIntExtra("value", 0));
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_account) {
                Intent intent = new Intent(getApplicationContext(), MyAccount.class);
                intent.putExtra("value", R.id.navigation_account);
                startActivity(intent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.searchNav) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                intent.putExtra("value", R.id.searchNav);
                startActivity(intent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.carNav) {
                // handle this layout
            }
            return false;
        });
    }

}
