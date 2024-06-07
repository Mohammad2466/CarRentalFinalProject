package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String carName = intent.getStringExtra("Car Name");
        String carImage = intent.getStringExtra("Car Image");
        int seats = intent.getIntExtra("Car Seats", 0);
        double carCost = intent.getDoubleExtra("Car Cost", 0.0);
        String category = intent.getStringExtra("Car Category");
        String model = intent.getStringExtra("Car Model");
        int modelYear = intent.getIntExtra("Car Model Year", 0);
        int maxSpeed = intent.getIntExtra("Car Max Speed", 0);
        int enginePower = intent.getIntExtra("Car Engine Power", 0);
        String gearType = intent.getStringExtra("Car Gear Type");
        boolean available = intent.getBooleanExtra("Car is Available", true);
        String info = intent.getStringExtra("Car Information");

        // Initialize views
        TextView carNameTextView = findViewById(R.id.BrandModelNameTextView);
        ImageView carImageView = findViewById(R.id.cardImageView);
        TextView carCostTextView = findViewById(R.id.priceTextView);
        TextView carSeatsTextView = findViewById(R.id.seatsTextView);
        TextView carModelYear = findViewById(R.id.carModelYear);
        TextView carCategory = findViewById(R.id.carCategory);
        TextView carModel = findViewById(R.id.carModel);
        TextView carMaxSpeed = findViewById(R.id.carMaxSpeed);
        TextView carEnginePower = findViewById(R.id.carEnginePower);
        TextView carGearType = findViewById(R.id.carGearType);
        TextView carAvailable = findViewById(R.id.carAvailbility);
        TextView carInfo = findViewById(R.id.carInfo);

        carNameTextView.setText(carName);
        Glide.with(this).load(carImage).into(carImageView);
        carCostTextView.setText(String.format("Cost per day: %.2f", carCost));
        carSeatsTextView.setText(String.format("Seats: %d", seats));
        carModelYear.setText(String.format("Model Year: %d", modelYear));
        carCategory.setText(category);
        carModel.setText(model);
        carMaxSpeed.setText(String.format("Max Speed: %d km/h", maxSpeed));
        carEnginePower.setText(String.format("Engine Power: %d HP", enginePower));
        carGearType.setText(gearType);
        carAvailable.setText("Is Available: " +String.format(available ? "available" : "not available"));
        carInfo.setText(info);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent1;
            if (item.getItemId() == R.id.navigation_home) {
                intent1 = new Intent(Details.this, MainActivity.class);
            } else if (item.getItemId() == R.id.navigation_account) {
                intent1 = new Intent(Details.this, MyAccount.class);
            } else if (item.getItemId() == R.id.searchNav) {
                intent1 = new Intent(Details.this, Search.class);
            } else if (item.getItemId() == R.id.carNav) {
                intent1 = new Intent(Details.this, Details.class);
            } else {
                return false;
            }
            startActivity(intent1);
            finish();
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
