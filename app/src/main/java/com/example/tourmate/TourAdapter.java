package com.example.tourmate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String key;

    private List<Trip> tripList;
    private Context context;
    private OnItemClickListener mListener;

    public TourAdapter(List<Trip> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Trip trip = tripList.get(position);
        key = trip.getKey();
        holder.nameTV.setText(trip.getTripName());
        holder.locationTV.setText(trip.getTripStartingLocation() + " To " + trip.getTripDestination());
        holder.budgetTV.setText(String.valueOf(trip.getTripBudget()));
        holder.dateTV.setText(trip.getTripStartDate());
        holder.spentDayTV.setText(trip.getTripEndDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "" + key, Toast.LENGTH_SHORT).show();

                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.addExpense:

                                Intent intent = new Intent(context, ExpenseActivity.class);
                                intent.putExtra("tourName", trip.getTripName());
                                intent.putExtra("key", trip.getKey());
                                intent.putExtra("budget", trip.getTripBudget());
                                context.startActivity(intent);
                                return true;
                            case R.id.addMemory:
                                Intent intent1 = new Intent(context, ShowMemoryActivity.class);
                                intent1.putExtra("tourName", trip.getTripName());
                                intent1.putExtra("key", trip.getKey());
                                context.startActivity(intent1);
                                return true;
                            case R.id.details:
                                Intent intent3 = new Intent(context, DetailsActivity.class);
                                intent3.putExtra("tourName",trip.getTripName());
                                intent3.putExtra("tourStartingLocation", trip.getTripStartingLocation());
                                intent3.putExtra("tourDestination", trip.getTripDestination());
                                intent3.putExtra("tourStartDate", trip.getTripStartDate());
                                intent3.putExtra("tourEndDate", trip.getTripEndDate());
                                intent3.putExtra("tourBudget",trip.getTripBudget());
                                intent3.putExtra("key",trip.getKey());
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
                Intent intent = new Intent(context,UpdateTourActivity.class);
                intent.putExtra("tourName",trip.getTripName());
                intent.putExtra("tourStartingLocation", trip.getTripStartingLocation());
                intent.putExtra("tourDestination", trip.getTripDestination());
                intent.putExtra("tourStartDate", trip.getTripStartDate());
                intent.putExtra("tourEndDate", trip.getTripEndDate());
                intent.putExtra("tourBudget",trip.getTripBudget());
                intent.putExtra("key",trip.getKey());
                context.startActivity(intent);
            }
        });

        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you sure that delete this item?");
                alertDialog.setIcon(R.drawable.ic_delete_red_24dp);

                //Set button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();

                        final String userId = firebaseAuth.getCurrentUser().getUid();
                        final DatabaseReference userRef = databaseReference.child("users").child(userId).child("tours").child(key);

                        userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    userRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        Toast.makeText(context, "Item deleted !!!", Toast.LENGTH_SHORT);
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

                final AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);

                alertDialog.show();


                // Toast.makeText(context, "Clicked Delete Button" + position, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
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
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem showItem = menu.add(Menu.NONE, 1, 1, "Update");
            MenuItem deleteItem = menu.add(Menu.NONE, 2, 2, "Delete");
            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (item.getItemId()) {
                        case 1:
                            Toast.makeText(context, "updated.....", Toast.LENGTH_LONG).show();
                            mListener.onShowItemClick(position);
                            return true;
                        case 2:
                            Toast.makeText(context, "deleted.....", Toast.LENGTH_SHORT).show();
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onShowItemClick(int position);

        void onDeleteItemClick(int position);
    }
}