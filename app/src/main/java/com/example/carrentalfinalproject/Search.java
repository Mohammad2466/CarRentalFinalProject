package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        searchView = findViewById(R.id.searchView);
        recycler = findViewById(R.id.recyclerViewSearch);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        searchView.clearFocus();

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
}
