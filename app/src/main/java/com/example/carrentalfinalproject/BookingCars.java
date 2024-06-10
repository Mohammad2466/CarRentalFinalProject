package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class BookingCars extends BaseActivity {

    private ImageView carImageView;
    private TextView carNameTextView, pickupDateTextView, returnDateTextView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_cars);

        // Initialize UI components
        carImageView = findViewById(R.id.cardImageView);
        carNameTextView = findViewById(R.id.BrandModelNameTextView);
        pickupDateTextView = findViewById(R.id.pickupDate);
        returnDateTextView = findViewById(R.id.returnDate);
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        String carBrand = intent.getStringExtra("Car Name");
        String carImage = intent.getStringExtra("Car Image");
        String carPickupDate = intent.getStringExtra("Pick Up Date");
        String carReturnDate = intent.getStringExtra("Return Date");

        // Set UI components with received data
        carNameTextView.setText(carBrand);
        Glide.with(this).load(carImage).into(carImageView);
        pickupDateTextView.setText(carPickupDate);
        returnDateTextView.setText(carReturnDate);

        // Handle BottomNavigationView item selections
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                Intent homeIntent = new Intent(BookingCars.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
                return true;
            } else if (itemId == R.id.navigation_account) {
                Intent accountIntent = new Intent(BookingCars.this, MyAccount.class);
                startActivity(accountIntent);
                finish();
                return true;
            } else if (itemId == R.id.searchNav) {
                Intent searchIntent = new Intent(BookingCars.this, Search.class);
                startActivity(searchIntent);
                finish();
                return true;
            } else if (itemId == R.id.carNav) {
                return true;
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_booking_cars;
    }
}
