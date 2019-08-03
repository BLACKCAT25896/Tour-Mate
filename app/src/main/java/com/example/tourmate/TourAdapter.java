package com.example.tourmate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    private List<Trip> tripList;
    private Context context;

    public TourAdapter(List<Trip> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.nameTV.setText(trip.getTripName());
        holder.locationTV.setText(trip.getTripStartingLocation()+" To "+trip.getTripDestination());
        holder.budgetTV.setText(String.valueOf(trip.getTripBudget()));
        holder.dateTV.setText(trip.getTripStartDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.addExpense:

                                Intent intent = new Intent(context, ExpenseActivity.class);
                                context.startActivity(intent);
                                return true;
                            case R.id.addMemory:
                                Intent intent1 = new Intent(context, MemoryActivity.class);
                                context.startActivity(intent1);
                                return true;
                            case R.id.details:
                                    Intent intent3 = new Intent(context, DetailsActivity.class);
                                    context.startActivity(intent3);

                            default:
                        }

                        return false;
                    }
                });
                popupMenu.inflate(R.menu.memory_expense);
                popupMenu.show();

            }
        });

        holder.editIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.update_tour_layout,null);
                builder.setView(v);
                AlertDialog dialog = builder.create();

//        nameET = findViewById(R.id.nameET);
//        amountET = findViewById(R.id.amountET);
//        dateET = findViewById(R.id.dateET);
//        addExpenseBtn = findViewById(R.id.addExpenseBTN);
//
//
//        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                name = nameET.getText().toString();
//                amount = amountET.getText().toString();
//                date = dateET.getText().toString();
//
//
//
//
//            }
//        });

                dialog.show();

            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Clicked Delete Button", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV, locationTV, budgetTV, dateTV, spentDayTV;
        private ImageView editIV, deleteIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.tourNameTV);
            locationTV = itemView.findViewById(R.id.tourLocationTV);
            budgetTV = itemView.findViewById(R.id.budgetTV);
            dateTV = itemView.findViewById(R.id.tourDateTV);
            spentDayTV = itemView.findViewById(R.id.spentDayTV);
            editIV = itemView.findViewById(R.id.editLocationIV);
            deleteIV = itemView.findViewById(R.id.deleteIV);



        }
    }
}
