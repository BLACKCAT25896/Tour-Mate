package com.example.tourmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.ViewHolder> {

    private List<Memory> memoryList;
    private Context context;

    public MemoryAdapter(List<Memory> memoryList, Context context) {
        this.memoryList = memoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memory_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Memory memory = memoryList.get(position);
        holder.title.setText(memory.getTitle());
        holder.description.setText(memory.getDescription());
        if(memory.getImage()!=null && !memory.getImage().isEmpty()){
            Picasso.get()
                    .load(memory.getImage())
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .fit()
                    .centerCrop()
                    .into(holder.image);

        }else {
            holder.image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_image_black_24dp));
        }




    }

    @Override
    public int getItemCount() {
        return memoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.memoryIV);
            title = itemView.findViewById(R.id.titleTV);
            description = itemView.findViewById(R.id.descriptionTV);
        }
    }
}
