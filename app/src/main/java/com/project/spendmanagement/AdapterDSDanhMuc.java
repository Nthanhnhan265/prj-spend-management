package com.project.spendmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterDSDanhMuc extends RecyclerView.Adapter {
    //khai báo
    private Context context;
    private List<DanhMuc> dsDanhMuc=new ArrayList<>();

    //Truyền vào danh sách danh mục gồm : tên và hình ảnh icon
    public AdapterDSDanhMuc(Context context, List<DanhMuc>dsDanhMuc) {
        try {
            this.dsDanhMuc=dsDanhMuc;
            this.context=context;
        }catch (Exception ex) {
            Toast.makeText(context, "Có lỗi xảy ra trong AdapterDSDanhMuc/constructor", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_danhmuc,parent,false);
        return new MyHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if(holder instanceof MyHolder) {
                //Lấy danh mục trong danh sách khi biết vị trí
                DanhMuc danhMucHienTai=dsDanhMuc.get(position);
                MyHolder myHolder=(MyHolder) holder; //ép kiểu holder;
                //gán tên danh mục thành danh mục hiện tại
                myHolder.tvTenDM.setText(danhMucHienTai.getTenDanhMuc());
                //gán icon imageView thành Resouce ID (int) của danh mục hiện tại
                myHolder.icon.setImageResource(danhMucHienTai.getIcon());
            }

        }catch (Exception ex) {
            Toast.makeText(context, "Có lỗi xảy ra trong AdapterDSDanhMuc/onBindViewHolder", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return dsDanhMuc.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTenDM;
        ImageView icon;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //ánh xạ
            icon=itemView.findViewById(R.id.ivIconDanhMuc);
            tvTenDM=itemView.findViewById(R.id.tvTenDanhMuc);
        }
    }
}
