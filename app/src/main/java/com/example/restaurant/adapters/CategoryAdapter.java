package com.example.restaurant.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.activities.CategoriesActivity;
import com.example.restaurant.activities.ShowDetailActivity;
import com.example.restaurant.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    LayoutInflater inflater;
    private ArrayList<Category> categories;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.category_list_view, parent,
                false);
        return new CategoryAdapter.CategoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.categoryName.setText(categories.get(position).getTitle());
        holder.categoryPic.setImageResource(categories.get(position).getPicture());

        holder.mainLayout.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), CategoriesActivity.class);
            intent.putExtra("category", categories.get(position).getTitle());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayoutCategories);
        }
    }
}
