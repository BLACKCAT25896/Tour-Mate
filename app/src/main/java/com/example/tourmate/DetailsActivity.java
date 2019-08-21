package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tourmate.databinding.ActivityDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<Expense> expenseList;
    private List<Memory> memoryList;
    private TourAdapter tourAdapter;
    private ExpenseAdapter expenseAdapter;
    private MemoryAdapter memoryAdapter;
    private String tourName, tourStartingLocation, tourEndLocation, tourStartDate, tourEndDate, budget, key;
    private double tourBudget, budgetAmount;
    private String name, startLocation, destination, startDate, endDate, amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_details);

        init();

        setupRecyclerView();


        getDetails();

    }



    private void getDetails() {

        tourName = getIntent().getStringExtra("tourName");
        tourStartingLocation = getIntent().getStringExtra("tourStartingLocation");
        tourEndLocation = getIntent().getStringExtra("tourDestination");
        tourStartDate = getIntent().getStringExtra("tourStartDate");
        tourEndDate = getIntent().getStringExtra("tourEndDate");
        tourBudget = getIntent().getDoubleExtra("tourBudget", 1);
        budget = String.valueOf(tourBudget);
        key = getIntent().getStringExtra("key");

        binding.tourNameTV.setText(tourName);
        binding.tourLocationTV.setText(tourStartingLocation +" To " + tourEndLocation);
        binding.tourDateTV.setText(tourStartDate);
        binding.spentDayTV.setText(tourEndDate);
        binding.budgetTV.setText(budget);


        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference expRef = databaseReference.child("users").child(userId).child("tours").child(key).child("expenses");
        expRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    expenseList.clear();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Expense expense = data.getValue(Expense.class);
                        expenseList.add(expense);
                        expenseAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        DatabaseReference memoryRef = databaseReference.child("users").child(userId).child("tours").child(key).child("memories");
        memoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    memoryList.clear();


                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Memory memory = data.getValue(Memory.class);
                        // String pushId = data.getKey();
                        memoryList.add(memory);
                        //pushList.add(pushId);

                        memoryAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void setupRecyclerView() {
        expenseAdapter = new ExpenseAdapter(expenseList,this);
        memoryAdapter = new MemoryAdapter(memoryList,this);
        binding.expenseRecyclerViewDetails.setLayoutManager(new LinearLayoutManager(this));
        binding.memoryRecyclerViewDetails.setLayoutManager(new LinearLayoutManager(this));
        binding.expenseRecyclerViewDetails.setAdapter(expenseAdapter);
        binding.memoryRecyclerViewDetails.setAdapter(memoryAdapter);

    }

    private void init() {
        expenseList = new ArrayList<>();
        memoryList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void back(View view) {
        startActivity(new Intent(DetailsActivity.this,MainActivity.class));
    }
}
