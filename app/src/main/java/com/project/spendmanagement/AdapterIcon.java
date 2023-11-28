package com.project.spendmanagement;

import android.content.Context;
import android.graphics.Color;
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
import java.util.Objects;

public class AdapterIcon extends RecyclerView.Adapter {
    //Khai báo
    int viTri=0;
    Context context;
    ArrayList<String> iconSrc=new ArrayList<>();
    public static int iconDuocChon;
    boolean flag=true;
    //Khởi tạo:
    public AdapterIcon(Context context, ArrayList<String>icons) {
        try {
            this.context=context;
            this.iconSrc =icons;
        }catch (Exception ex) {
            //Toast.makeText(context, "Có lỗi xảy ra trong AdapterIcon/constructor", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_icon,parent,false);
        return new MyHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            String iconHienTai = iconSrc.get(position); //
            //lấy id qua string
            int resId = holder.itemView.getContext().getResources().getIdentifier(iconHienTai, "drawable", holder.itemView.getContext().getPackageName());
            if (holder instanceof MyHolder) {
                MyHolder myHolder = (MyHolder) holder;
                myHolder.imageView.setImageResource(resId);

                if(flag==true && Objects.equals(resId, iconDuocChon)) {
                    myHolder.imageView.setBackground(context.getDrawable(R.drawable.custom_icon_selected));
                }

                myHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viTri=myHolder.getAdapterPosition();
                        notifyDataSetChanged();
                        iconDuocChon=holder.itemView.getContext().getResources().getIdentifier(iconHienTai, "drawable", holder.itemView.getContext().getPackageName());;
                        flag=false;
                    }
                });
                if(!flag) {
                    if(viTri==position) {
                        myHolder.imageView.setBackground(context.getDrawable(R.drawable.custom_icon_selected));
                    }
                    else
                    {
                        myHolder.imageView.setBackground(context.getDrawable(R.drawable.custom_icon_notselect));
                    }
                }
            }

        }catch (Exception ex) {
            //Toast.makeText(context, "Có lỗi xảy ra trong AdapterIcon/onBindViewHolder", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        try {
           return iconSrc.size();
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
            imageView=itemView.findViewById(R.id.ivIconDanhMuc);
        }
    }
    //phương thức lấy icon được chọn
    public int getIconDuocChon() {
        try {
            if(iconDuocChon!=0) {
                int resId =iconDuocChon;
                return resId;
            }
        }catch (Exception ex) {
            Log.e("err AdapterIcon/getIconDuocChon: ",ex.getMessage());
        }

        return -1;
    }



}
