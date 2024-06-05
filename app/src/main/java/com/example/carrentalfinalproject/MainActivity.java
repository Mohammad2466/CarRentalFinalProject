package com.example.carrentalfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    private FirebaseUser user;

    private RequestQueue queue;
    private List<Car> items = new ArrayList<>();
    private RecyclerView recycler;
    private static final String BASE_URL = "http://192.168.0.108/json/view.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assuming EdgeToEdge.enable(this) is a custom utility method
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // Check if user is logged in, if not redirect to Login activity
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        } else {

            // Display user's email in the TextView
        }
        // Set logout button click listener to sign out and redirect to Login activity
       /* button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        });*/
        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });


        recycler = findViewById(R.id.recyclerViewCars);


        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();
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
}*/
