package com.project.spendmanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterLich extends RecyclerView.Adapter {
    //fields
    private Context context;
    private List<GiaoDich> listGiaoDich;
    private List<String> listNgayGiaoDich;
    private AdapterGiaoDich adapterGiaoDich;


    public AdapterLich(Context context, List<GiaoDich> listGiaoDich) {
        if(listGiaoDich.size()!=0) {
            listNgayGiaoDich =new ArrayList<>();
            this.context = context;
            this.listGiaoDich = listGiaoDich;
            listNgayGiaoDich.add(listGiaoDich.get(0).getNgayGD()); //them 1 phan tu vao danh sach ngay truoc
            for (GiaoDich giaodich:listGiaoDich) {
                if(listNgayGiaoDich.indexOf(giaodich.getNgayGD())==-1) {//khong ton tai ngay trong danh sach
                    listNgayGiaoDich.add(giaodich.getNgayGD());
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
        if(listGiaoDich.size()!=0) {
            String ngayHienTai= listNgayGiaoDich.get(position).toString();
            List<GiaoDich> giaoDichTrongNgay=new ArrayList<>();
            //khi ngày hiện tại được đổi thì duyệt for để lấy các giao dịch của ngày đó
            for (GiaoDich giaoDich: listGiaoDich) {
                if(giaoDich.getNgayGD().equals(ngayHienTai)) {
                    giaoDichTrongNgay.add(giaoDich);
                }
            }
            //khi có giao dịch của ngày đó rồi thi tạo adapter cho giao dịch
            adapterGiaoDich =new AdapterGiaoDich(context,giaoDichTrongNgay);
            
            if (holder instanceof MyViewHolder) {
                MyViewHolder myViewHolder = (MyViewHolder) holder; // Ép kiểu ViewHolder của bạn

                // Gắn dữ liệu vào TextView
                myViewHolder.tvDate.setText(ngayHienTai);
                //Gắn dữ liệu cho recyclerView của ngày hiện tại
                myViewHolder.rcThuChi.setAdapter(adapterGiaoDich);

            }
        }

    }

    @Override
    public int getItemCount() {

        return listNgayGiaoDich.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView tvDate;

         RecyclerView rcThuChi;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvNgay);
            rcThuChi=itemView.findViewById(R.id.rcExtenseIcome);


        }


    }
}
