package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.tourmate.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActivityMainBinding binding;
    private TourAdapter tourAdapter;
    private List<Trip> tripList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();



        drawer();

        getTourData();



    }

    private void getTourData() {
        tripList.add(new Trip("sajek","Dhaka","Sajek"," 4 july 2019","6 july 2019",20000));
        tripList.add(new Trip("sajek","Dhaka","Sajek"," 4 july 2019","6 july 2019",20000));
        tripList.add(new Trip("sajek","Dhaka","Sajek"," 4 july 2019","6 july 2019",20000));
        tripList.add(new Trip("sajek","Dhaka","Sajek"," 4 july 2019","6 july 2019",20000));
        tripList.add(new Trip("sajek","Dhaka","Sajek"," 4 july 2019","6 july 2019",20000));
    }

    private void drawer() {
       // setSupportActionBar(binding.toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawer, R.string.Open, R.string.Close);
        binding.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    private void init() {
        tripList = new ArrayList<>();
        tourAdapter = new TourAdapter(tripList,this);
        binding.tourRV.setLayoutManager(new LinearLayoutManager(this));
        binding.tourRV.setAdapter(tourAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addExpense(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.activity_trip_add,null);
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
}
