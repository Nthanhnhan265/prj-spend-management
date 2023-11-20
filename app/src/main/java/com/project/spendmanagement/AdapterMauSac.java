package com.project.spendmanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMauSac extends RecyclerView.Adapter {
    Context context;
    ArrayList<String> mauSac=new ArrayList<>(); //màu sắc chứa thông tin về mã màu ví dụ #fff

    //Khởi tạo:
    public AdapterMauSac(Context context, ArrayList<String>mauSac) {
        try {
            this.context=context;
            this.mauSac =mauSac;
        }catch (Exception ex) {
            //Toast.makeText(context, "Có lỗi xảy ra trong AdapterMauSac/constructor", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_mausac,parent,false);
        return new MyHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {

            String mauSacHienTai =mauSac.get(position);;
            int color = Color.parseColor(mauSacHienTai);
            Log.d("Color String", mauSacHienTai);
            Log.d("Position", String.valueOf(position));
            if (holder instanceof MyHolder) {
                MyHolder myHolder = (MyHolder) holder;
                myHolder.imageView.setBackgroundColor(color);
            }



        }catch (Exception ex) {
            //Toast.makeText(context, "Có lỗi xảy ra trong AdapterMauSac/onBindViewHolder", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        try {
            return mauSac.size();
        }catch (Exception ex) {
            //Toast.makeText(context, "Có lỗi xảy ra trong AdapterIcon/getItemCount", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
        return 0;
    }

    //Tạo class để lưu giữ thông tin cho item
    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ivMauSac);
        }
    }
}

