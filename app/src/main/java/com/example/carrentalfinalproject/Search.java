package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Search extends BaseActivity{

    SearchView searchView;
    RecyclerView recycler;
    List<Car> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        recycler = findViewById(R.id.recyclerViewCars);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
               Intent intent = new Intent(Search.this, MainActivity.class);
               startActivity(intent);
               finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_account) {
                Intent intent = new Intent(Search.this, MyAccount.class);
                startActivity(intent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.searchNav){
                return true;
            }
            else if (item.getItemId() == R.id.carNav){
                Intent intent = new Intent(Search.this, Details.class);
                startActivity(intent);
                finish();
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
    private void filterText(String newText) {
        List<Car> filterList = new ArrayList<>();
        for (Car car : items) {
            if (car.getBrand().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(car);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        } else {
            // Update the RecyclerView adapter with the filtered list
            RecyclerViewAdapter filteredAdapter = new RecyclerViewAdapter(getApplicationContext(), filterList);
            recycler.setAdapter(filteredAdapter);
            recycler.setVisibility(View.VISIBLE);

        }
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search;
    }

}
