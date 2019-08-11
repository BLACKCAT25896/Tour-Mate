package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tourmate.databinding.ActivityShowMemoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowMemoryActivity extends AppCompatActivity {
    private ActivityShowMemoryBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private List<Memory> memoryList;
    private MemoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_memory);

        init();

        getMemory();

        controlFAB();

        binding.addMemoryFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowMemoryActivity.this,MemoryActivity.class));
            }
        });




    }

    private void controlFAB() {
        binding.memoryRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && binding.addMemoryFAB.getVisibility() == View.VISIBLE) {
                    binding.addMemoryFAB.hide();
                } else if (dy < 0 && binding.addMemoryFAB.getVisibility() != View.VISIBLE) {
                    binding.addMemoryFAB.show();
                }
            }
        });
    }

    private void getMemory() {
        DatabaseReference memoryRef = databaseReference.child("memories");
        memoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    memoryList.clear();


                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Memory memory = data.getValue(Memory.class);
                        String pushId = data.getKey();
                        memoryList.add(memory);
                        //pushList.add(pushId);

                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void init() {
        memoryList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        adapter = new MemoryAdapter(memoryList,this);
        binding.memoryRV.setLayoutManager(new LinearLayoutManager(this));
        binding.memoryRV.setAdapter(adapter);

    }
}
