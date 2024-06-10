package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class Details extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(getLayoutResourceId());




        Intent intent = getIntent();
        String carBrand = intent.getStringExtra("Car Brand");
        String carImage = intent.getStringExtra("Car Image");
        String seats ="Seats : "+ intent.getStringExtra("Car Seats");
        String carCost ="Cost a day : "+ intent.getStringExtra("Car Cost");
        String category ="Category : "+ intent.getStringExtra("Car Category");
        String model = "Model name : "+intent.getStringExtra("Car Model");
        String modelYear ="Model year : "+ intent.getStringExtra("Car Model Year");
        String maxSpeed = "Max speed : "+intent.getStringExtra("Car Max Speed");
        String enginePower ="Engine power : "+ intent.getStringExtra("Car Engine Power");
        String gearType = "Gear Type : "+intent.getStringExtra("Car Gear Type");
        String available = (Objects.equals(intent.getStringExtra("Car Availability"), "true") ? "Available":"Not Available");
        String info = intent.getStringExtra("Car Info");

        String[] arr = {model,modelYear,category,seats,maxSpeed,enginePower,gearType,available};


        TextView Carinfo = findViewById(R.id.CarInfo);
        Carinfo.setText(info);
        TextView Price = findViewById(R.id.priceTextView);
        Price.setText(carCost);

        ListView lv = findViewById(R.id.detailsListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(adapter);


        // Initialize views
        TextView carNameTextView = findViewById(R.id.BrandModelNameTextView);
        ImageView carImageView = findViewById(R.id.cardImageView);

//        TextView carSeatsTextView = findViewById(R.id.seatsTextView);
//        TextView carModelYear = findViewById(R.id.carModelYear);
//        TextView carCategory = findViewById(R.id.carCategory);
//        TextView carMaxSpeed = findViewById(R.id.carMaxSpeed);
//        TextView carEnginePower = findViewById(R.id.carEnginePower);
//        TextView carGearType = findViewById(R.id.carGearType);
//        TextView carAvailable = findViewById(R.id.carAvailbility);
//        TextView carInfo = findViewById(R.id.carInfo);

        carNameTextView.setText(carBrand);
        Glide.with(this).load(carImage).into(carImageView);




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setListViewHeightBasedOnChildren(lv);
    }

    // Method to adjust ListView height
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listAdapter.getCount()*150;


        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_details;
    }
}
