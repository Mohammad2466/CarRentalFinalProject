package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends BaseActivity{

    private List<Car> items = new ArrayList<>();
    private RecyclerView recycler;
    private SearchView searchView;
    private static final String BASE_URL = "http://192.168.0.111/json/view.php";
    private RequestQueue queue;

    ImageView filterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        searchView = findViewById(R.id.searchView);
        recycler = findViewById(R.id.recyclerViewSearch);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        searchView.clearFocus();
        filterImage = findViewById(R.id.iv_filter);

        queue = Volley.newRequestQueue(this);


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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         navBarStatus();

        loadItems();

        // Add click listener for filter image
        filterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterDialog();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search;
    }



    private void loadItems() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                int id = Integer.parseInt(object.getString("ID"));
                                String color = object.getString("color");
                                String category = object.getString("category");
                                String brand = object.getString("brand");
                                String model = object.getString("model");
                                int madeYear = Integer.parseInt(object.getString("madeYear"));
                                int maxSpeed = Integer.parseInt(object.getString("maxSpeed"));
                                int enginePower = Integer.parseInt(object.getString("enginePower"));
                                double costPerDay = Double.parseDouble(object.getString("costPerDay"));
                                int seats = Integer.parseInt(object.getString("seats"));
                                String gearType = object.getString("gearType");

                                String isAvailable = object.getString("isAvailable");
                                boolean isAva = isAvailable.equals("1");
                                String carDetails = object.getString("carDetails");
                                String image = object.getString("image");





                                Car car = new Car(id,color,category,brand,model,madeYear,maxSpeed,enginePower,costPerDay,seats,gearType,isAva,carDetails,image);
                                items.add(car);


                            }

                        }catch (Exception e){
                            Toast.makeText(Search.this, e.toString(),Toast.LENGTH_LONG).show();
                        }


                      /*  RecyclerViewAdapter adapter = new RecyclerViewAdapter(Search.this,
                                items);
                        recycler.setAdapter(adapter);
                        Toast.makeText(Search.this,"yessss",Toast.LENGTH_SHORT).show();*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Search.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

    }

    private void filterText(String newText) {
        List<Car> filterList = new ArrayList<>();
        for (Car car : items) {
            if (car.getBrand().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(car);
            }
        }
        if (filterList.isEmpty()) {
            recycler.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        } else {
            // Update the RecyclerView adapter with the filtered list
            RecyclerViewAdapter filteredAdapter = new RecyclerViewAdapter(getApplicationContext(), filterList);
            recycler.setAdapter(filteredAdapter);
            recycler.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    private void openFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filter, null);
        builder.setView(dialogView);

        // Initialize filter views
        Spinner categorySpinner = dialogView.findViewById(R.id.spinner_category);
        EditText priceEditText = dialogView.findViewById(R.id.edittext_price);
        Switch availabilitySwitch = dialogView.findViewById(R.id.switch_availability);
        Spinner gearTypeSpinner = dialogView.findViewById(R.id.spinner_gear_type);
        Button applyFiltersButton = dialogView.findViewById(R.id.button_apply_filters);

        // Setup Spinners (e.g., populate them with data)
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> gearTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.gear_type_array, android.R.layout.simple_spinner_item);
        gearTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gearTypeSpinner.setAdapter(gearTypeAdapter);

        AlertDialog dialog = builder.create();
        dialog.show();

        applyFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get filter values
                String selectedCategory = categorySpinner.getSelectedItem().toString();
                String priceText = priceEditText.getText().toString();
                boolean isAvailable = availabilitySwitch.isChecked();
                String selectedGearType = gearTypeSpinner.getSelectedItem().toString();

                double maxPrice = priceText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(priceText);

                // Apply filters
                applyFilters(selectedCategory, maxPrice, isAvailable, selectedGearType);
                dialog.dismiss();
            }
        });
    }

    private void applyFilters(String category, double maxPrice, boolean isAvailable, String gearType) {
        List<Car> filteredCars = new ArrayList<>();
        for (Car car : items) {
            if ((category.equals("All") || car.getCategory().equals(category)) &&
                    car.getCostPerDay() <= maxPrice &&
                    car.isAvailable() == isAvailable &&
                    (gearType.equals("All") || car.getGearType().equals(gearType))) {
                filteredCars.add(car);
            }
        }

        if (filteredCars.isEmpty()) {
            recycler.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
        } else {
            // Update the RecyclerView adapter with the filtered list
            RecyclerViewAdapter filteredAdapter = new RecyclerViewAdapter(this, filteredCars);
            recycler.setAdapter(filteredAdapter);
            recycler.setVisibility(View.VISIBLE);
        }
    }


}
