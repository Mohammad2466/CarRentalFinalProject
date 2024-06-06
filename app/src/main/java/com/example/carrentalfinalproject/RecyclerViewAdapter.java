package com.example.carrentalfinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private Context context;
    private List<Car> items;


    public void setFilteredList(List<Car> filteredList){
        this.items = filteredList;
        notifyDataSetChanged();
    }

    public RecyclerViewAdapter(Context context, List<Car> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Car car = items.get(position);
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.cardImageView);
        Glide.with(context).load(car.getImage()).into(imageView);
        imageView.setMinimumWidth(300);
        TextView name = (TextView)cardView.findViewById(R.id.BrandModelNameTextView);
        name.setText(car.getBrand()+" "+car.getModel());
        TextView seats = (TextView)cardView.findViewById(R.id.seatsTextView);
        seats.setText("Seats "+car.getSeats());
        TextView price = (TextView)cardView.findViewById(R.id.priceTextView);
        price.setText("Cost per day/"+car.getCostPerDay()+"");
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}

