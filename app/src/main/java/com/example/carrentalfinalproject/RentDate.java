//package com.example.carrentalfinalproject;
//
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.icu.text.SimpleDateFormat;
//import android.icu.util.Calendar;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import java.util.Locale;
//
//public class RentDate extends AppCompatActivity {
//
//    private ImageView calendarStart, calendarEnd, startClock, endClock;
//    private TextView startDate, startTime, endDate, endTime, carName;
//    private String carImageUrl;
//    private Button senButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rent_date);
//
//        // Initialize UI components
//        calendarStart = findViewById(R.id.calendar_icon_1);
//        startClock = findViewById(R.id.clock_icon_1);
//        calendarEnd = findViewById(R.id.calendar_icon_2);
//        endClock = findViewById(R.id.clock_icon_2);
//
//        startDate = findViewById(R.id.pickupDate);
//        startTime = findViewById(R.id.pickupTime);
//        endDate = findViewById(R.id.returnDate);
//        endTime = findViewById(R.id.returnTime);
//
//        senButton = findViewById(R.id.btn_send);
//
//        // Event listeners for date and time pickers
//        calendarStart.setOnClickListener(v -> showDatePickerDialog(startDate));
//        startClock.setOnClickListener(v -> showTimePickerDialog(startTime));
//        calendarEnd.setOnClickListener(v -> showDatePickerDialog(endDate));
//        endClock.setOnClickListener(v -> showTimePickerDialog(endTime));
//
//        // Send button event listener
//        senButton.setOnClickListener(view -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(RentDate.this);
//            builder.setMessage("Do you want to rent the car?")
//                    .setCancelable(false)
//                    .setPositiveButton("OK", (dialog, which) -> {
//                        Toast.makeText(RentDate.this, "Rent process successful", Toast.LENGTH_SHORT).show();
//
//                        Intent intent = new Intent(RentDate.this, BookingCars.class);
//                        String carName = intent.getStringExtra("Car Name");
//                        String carImage = intent.getStringExtra("Car Image");
//                        String pickupDate = intent.getStringExtra("Pick Up Date");
//                        String returnDate = intent.getStringExtra("Return Date");
//
//
//
//                       startActivity(intent);
//                       finish();
//                    })
//                    .setNegativeButton("Cancel", (dialog, which) -> {
//                        dialog.dismiss();
//                        Toast.makeText(RentDate.this, "Rent process canceled", Toast.LENGTH_SHORT).show();
//                    });
//            AlertDialog alertDialog = builder.create();
//            alertDialog.show();
//        });
//
//        // Bottom navigation view event listener
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.navigation_home) {
//                startActivity(new Intent(RentDate.this, MainActivity.class));
//                finish();
//                return true;
//            } else if (item.getItemId() == R.id.navigation_account) {
//                startActivity(new Intent(RentDate.this, MyAccount.class));
//                finish();
//                return true;
//            } else if (item.getItemId() == R.id.searchNav) {
//                startActivity(new Intent(RentDate.this, Search.class));
//                finish();
//                return true;
//            } else if (item.getItemId() == R.id.carNav) {
//                return true;
//            }
//            return false;
//        });
//
//        // Handling window insets for edge-to-edge
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//
//    private void showDatePickerDialog(final TextView dateTextView) {
//        final Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                RentDate.this,
//                (view, year1, monthOfYear, dayOfMonth) -> {
//                    calendar.set(year1, monthOfYear, dayOfMonth);
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd", Locale.getDefault());
//                    String selectedDate = dateFormat.format(calendar.getTime());
//                    dateTextView.setText(selectedDate);
//                },
//                year, month, day);
//        datePickerDialog.show();
//    }
//
//    private void showTimePickerDialog(final TextView timeTextView) {
//        final Calendar calendar = Calendar.getInstance();
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(
//                RentDate.this,
//                (view, hourOfDay, minute1) -> {
//                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                    calendar.set(Calendar.MINUTE, minute1);
//                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
//                    String selectedTime = timeFormat.format(calendar.getTime());
//                    timeTextView.setText(selectedTime);
//                },
//                hour, minute, false); // Set to 12-hour format
//        timePickerDialog.show();
//    }
//}
