package com.example.carrentalfinalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private FirebaseAuth auth;

    private FirebaseUser user;

    private RequestQueue queue;
    private List<Car> items = new ArrayList<>();
    private RecyclerView recycler;

    private ImageView contactUs;

    //private SearchView searchView;

    // All url in one place call them from this activity
    //easier to make changes in one place
    public static final String BASE_URL = "http://172.20.10.2/rest/view.php";
    public static final String addUser_URL = "http://172.20.10.2/rest/addUser.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        contactUs = findViewById(R.id.iv_contactUs);


        // Check if user is logged in, if not redirect to Login activity
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            TextView welcome = findViewById(R.id.wlcTV);
            welcome.setText("Hello, "+user.getEmail());
        }


        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });


        navBarStatus();

        recycler = findViewById(R.id.recyclerViewCars);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Start the ContactUs activity
                Intent intent = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
    return R.layout.activity_main;

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
                            Toast.makeText(MainActivity.this, e.toString(),Toast.LENGTH_LONG).show();

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


   /* private void filterText(String newText) {
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
    }*/

}


