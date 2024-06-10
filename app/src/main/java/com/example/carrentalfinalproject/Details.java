package com.example.carrentalfinalproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;
import java.util.Objects;

public class Details extends BaseActivity {

    private ImageView calendarStart, calendarEnd, startClock, endClock, backClick;
    private TextView startDate, startTime, endDate, endTime, carName;
    private String carImageUrl;
    private Button senButton;

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

        // Initialize UI components
        calendarStart = findViewById(R.id.calendar_icon_1);
        startClock = findViewById(R.id.clock_icon_1);
        calendarEnd = findViewById(R.id.calendar_icon_2);
        endClock = findViewById(R.id.clock_icon_2);

        startDate = findViewById(R.id.pickupDate);
        startTime = findViewById(R.id.pickupTime);
        endDate = findViewById(R.id.returnDate);
        endTime = findViewById(R.id.returnTime);

        senButton = findViewById(R.id.btn_send);

        // Event listeners for date and time pickers
        calendarStart.setOnClickListener(v -> showDatePickerDialog(startDate));
        startClock.setOnClickListener(v -> showTimePickerDialog(startTime));
        calendarEnd.setOnClickListener(v -> showDatePickerDialog(endDate));
        endClock.setOnClickListener(v -> showTimePickerDialog(endTime));
        backClick = findViewById(R.id.iv_back);

        senButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
            builder.setMessage("Do you want to rent the car?")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> {
                        Toast.makeText(Details.this, "Rent process successful", Toast.LENGTH_SHORT).show();

                        Intent intent1 = new Intent(Details.this, BookingCars.class);
                        intent1.putExtra("Car Name", carBrand);
                        intent1.putExtra("Car Image", carImage);
                        intent1.putExtra("Pick Up Date", startDate.getText().toString() + " " + startTime.getText().toString());
                        intent1.putExtra("Return Date", endDate.getText().toString() + " " + endTime.getText().toString());

                        startActivity(intent1);
                        finish();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                        Toast.makeText(Details.this, "Rent process canceled", Toast.LENGTH_SHORT).show();
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });



        carNameTextView.setText(carBrand);
        Glide.with(this).load(carImage).into(carImageView);


        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Details.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });


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

    private void showDatePickerDialog(final TextView dateTextView) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Details.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    calendar.set(year1, monthOfYear, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd", Locale.getDefault());
                    String selectedDate = dateFormat.format(calendar.getTime());
                    dateTextView.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(final TextView timeTextView) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                Details.this,
                (view, hourOfDay, minute1) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute1);
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    String selectedTime = timeFormat.format(calendar.getTime());
                    timeTextView.setText(selectedTime);
                },
                hour, minute, false); // Set to 12-hour format
        timePickerDialog.show();
    }
}
