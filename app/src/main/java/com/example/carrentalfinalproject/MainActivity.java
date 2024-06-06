package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{

    private FirebaseAuth auth;

    private FirebaseUser user;

    private RequestQueue queue;
    private List<Car> items = new ArrayList<>();
    private RecyclerView recycler;
    private RecyclerViewAdapter itemAdapter;
    SearchView searchView;
    private static final String BASE_URL = "http://192.168.0.108/json/view.php";
    private static final String BASE_URL1 = "http://192.168.1.105/rest/view.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assuming EdgeToEdge.enable(this) is a custom utility method
        EdgeToEdge.enable(this);

        queue = Volley.newRequestQueue(this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        itemAdapter = new RecyclerViewAdapter(MainActivity.this, items);


        // Set the searchView after setContentView
        searchView = findViewById(R.id.searchView);
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

        // Check if user is logged in, if not redirect to Login activity
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            // Display user's email in the TextView
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                // Handle home navigation
                return true;
            } else if (item.getItemId() == R.id.navigation_account) {
                Intent intent = new Intent(MainActivity.this, MyAccount.class);
                startActivity(intent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.searchNav){
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
                finish();
                return true;
            }
            else if (item.getItemId() == R.id.carNav){
                // handle this layout
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        recycler = findViewById(R.id.recyclerViewCars);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();
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
            RecyclerViewAdapter filteredAdapter = new RecyclerViewAdapter(MainActivity.this, filterList);
            recycler.setAdapter(filteredAdapter);
        }
    }

    private void loadItems() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL1,
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
                                double enginePower = Double.parseDouble(object.getString("enginePower"));
                                double costPerDay = Double.parseDouble(object.getString("costPerDay"));
                                int seats = Integer.parseInt(object.getString("seats"));
                                String gearType = object.getString("gearType");

                                String isAvailable = object.getString("isAvailable");
                                boolean isAva = isAvailable.equals(1);
                                String carDetails = object.getString("carDetails");
                                String image = object.getString("image");



                                Car car = new Car(id,color,category,brand,model,madeYear,maxSpeed,enginePower,costPerDay,seats,gearType,isAva,carDetails,image);
                                items.add(car);
                                TextView tx = findViewById(R.id.Aaaa);
                                tx.setText("hiiiii1");

                            }

                        }catch (Exception e){
                            TextView tx = findViewById(R.id.Aaaa);
                            tx.setText(e.getMessage());
                        }

                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this,
                                items);
                        recycler.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);
        /* Volley.newRequestQueue(MainActivity.this).add(stringRequest)*/;

    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }
}

   /* public void viewItemsbtOnClick(View view) {

      //  ListView lst = findViewById(R.id.itemsList);

        String cat = "all";
        String url = "http://192.168.0.111/json/view.php?view=" + cat;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> cars = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        cars.add( obj.getString("model"));
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                String[] arr = new String[cars.size()];
                arr = cars.toArray(arr);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        MainActivity.this, android.R.layout.simple_list_item_1,
                        arr);
              //  lst.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        queue.add(request);

    }

    public void viewCarsbtOnClick(View view) {
        Intent intent = new Intent(MainActivity.this,viewCars.class);
        startActivity(intent);
    }

    */
