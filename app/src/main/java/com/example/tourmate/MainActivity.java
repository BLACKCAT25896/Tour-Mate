package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private ActivityMainBinding binding;
    private TourAdapter tourAdapter;
    private List<Trip> tripList;
    private List<String> pushList;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setTitle("Trips");

        init();
        getTourData();

       binding.tourRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               if (dy > 0 && binding.addtourFAB.getVisibility() == View.VISIBLE) {
                   binding.addtourFAB.hide();
               } else if (dy < 0 && binding.addtourFAB.getVisibility() != View.VISIBLE) {
                   binding.addtourFAB.show();
               }
           }
       });



    }

    private void getTourData() {
//        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference tourRef = databaseReference.child("tours");
        tourRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    tripList.clear();
                    pushList.clear();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Trip trip = data.getValue(Trip.class);
                        String pushId = data.getKey();
                        tripList.add(trip);
                        pushList.add(pushId);

                        tourAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void init() {


        tripList = new ArrayList<>();
        pushList = new ArrayList<>();
        tourAdapter = new TourAdapter(tripList,this);
        binding.tourRV.setLayoutManager(new LinearLayoutManager(this));
        binding.tourRV.setAdapter(tourAdapter);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    public void addExpense(View view) {
        startActivity(new Intent(MainActivity.this, AddTourActivity.class));
    }


}
