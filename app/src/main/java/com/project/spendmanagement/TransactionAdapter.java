package com.project.spendmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter {
    //fields
    private List<Transaction> data;

    public TransactionAdapter(List<Transaction> data) {
        this.data=data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_extense,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Transaction currentObj=data.get(position);
        if(holder instanceof MyHolder) {
            MyHolder myHolder=(MyHolder) holder;
            ((MyHolder) holder).tvCategory.setText(currentObj.getCategory().getTen_category());
            ((MyHolder) holder).tvDesc.setText(currentObj.getDescription());
            ((MyHolder) holder).tvValue.setText(currentObj.getTextOfValue());
            ((MyHolder) holder).ivCategory.setImageResource(currentObj.getCategory().getIcon());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;
        TextView tvDesc;
        TextView tvValue;
        ImageView ivCategory;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
             ivCategory=itemView.findViewById(R.id.ivCategory);
             tvCategory=itemView.findViewById(R.id.tvCategory);
             tvDesc=itemView.findViewById(R.id.tvDesc);
             tvValue=itemView.findViewById(R.id.tvValue);
        }
    }
}
