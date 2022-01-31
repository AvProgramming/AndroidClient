package com.example.restaurant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.model.Desk;

import java.util.ArrayList;

public class DeskAdapter extends RecyclerView.Adapter<DeskAdapter.DeskViewHolder>{

    LayoutInflater inflater;
    private ArrayList<Desk> desks;

    public DeskAdapter(ArrayList<Desk> desks, Context context) {
        this.desks = desks;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DeskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.desks_list_view, parent,
                false);
        return new DeskViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull DeskViewHolder holder, int position) {
        Desk desk = desks.get(position);
        holder.deskNumber.setText("The number of table is: " + desk.getNumber());
        holder.deskCapacity.setText("Max capacity is: " + desk.getMaxCapacity());
    }

    @Override
    public int getItemCount() {
        return desks.size();
    }

    public static class DeskViewHolder extends RecyclerView.ViewHolder {
        private final TextView deskNumber;
        private final TextView deskCapacity;



        public DeskViewHolder(@NonNull View itemView) {
            super(itemView);
            deskCapacity = itemView.findViewById(R.id.deskCapacity);
            deskNumber = itemView.findViewById(R.id.deskNumber);
        }
    }
}
