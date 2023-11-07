package com.project.spendmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter {
    //fields
    private Context context;
    private List<Transaction>list_transaction;
    private List<String>list_Dates;
    private TransactionAdapter adt_transaction;


    public DateAdapter(Context context, List<Transaction> list_transaction) {
        if(list_transaction.size()!=0) {
            list_Dates=new ArrayList<>();
            this.context = context;
            this.list_transaction = list_transaction;
            list_Dates.add(list_transaction.get(0).getDate()); //them 1 phan tu vao danh sach ngay truoc
            for (Transaction transaction:list_transaction) {
                if(list_Dates.indexOf(transaction.getDate())==-1) {//khong ton tai ngay trong danh sach
                    list_Dates.add(transaction.getDate());
                }
            }
        }


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_date,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list_transaction.size()!=0) {
            String currentDate=list_Dates.get(position).toString();
            List<Transaction> currentItems=new ArrayList<>();
            //khi ngày hiện tại được đổi thì duyệt for để lấy các giao dịch của ngày đó
            for (Transaction transaction: list_transaction) {
                if(transaction.getDate().equals(currentDate)) {
                    currentItems.add(transaction);
                }
            }
            //khi có giao dịch của ngày đó rồi thi tạo adapter cho giao dịch
            adt_transaction=new TransactionAdapter(currentItems);

            if (holder instanceof MyViewHolder) {
                MyViewHolder myViewHolder = (MyViewHolder) holder; // Ép kiểu ViewHolder của bạn

                // Gắn dữ liệu vào TextView
                myViewHolder.tvDate.setText(currentDate);
                //Gắn dữ liệu cho recyclerView của ngày hiện tại
                myViewHolder.rcExtenseIcome.setAdapter(adt_transaction);

            }
        }

    }

    @Override
    public int getItemCount() {

        return list_Dates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView tvDate;

         RecyclerView rcExtenseIcome;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvNgay);
            rcExtenseIcome=itemView.findViewById(R.id.rcExtenseIcome);
        }
    }
}
