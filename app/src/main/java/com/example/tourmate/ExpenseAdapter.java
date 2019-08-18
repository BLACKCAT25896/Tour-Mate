package com.example.tourmate;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private List<Expense> expenseList;
    private Context context;
    private String key, tourKey;

    public ExpenseAdapter(List<Expense> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        key = expense.getKey();
        tourKey = expense.getTourKey();
        holder.expenseNameTV.setText(expense.getExpenseName());
        holder.expenseAmountTV.setText(String.valueOf(expense.getExpenseAmount()));
        holder.expenseDateTV.setText(expense.getExpenseDate());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Edit Testing", Toast.LENGTH_SHORT).show();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
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

                        final DatabaseReference userRef = databaseReference.child("users").child(userId).child("tours").child(tourKey).child("expenses").child(key);

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
                Toast.makeText(context, "Delete Expense", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView expenseNameTV, expenseAmountTV, expenseDateTV;
        private ImageView edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseNameTV = itemView.findViewById(R.id.expenseNameTV);
            expenseAmountTV = itemView.findViewById(R.id.expenseAmountTV);
            expenseDateTV = itemView.findViewById(R.id.expenseDateTV);
            edit = itemView.findViewById(R.id.editIV);
            delete = itemView.findViewById(R.id.deleteIV);
        }
    }
}
