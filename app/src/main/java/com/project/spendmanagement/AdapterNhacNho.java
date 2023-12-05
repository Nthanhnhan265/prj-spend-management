package com.project.spendmanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterNhacNho extends RecyclerView.Adapter {
    //khai báo
    private Context context;
    private List<NhacNho> dsNhacNho = new ArrayList<>();

    //Truyền vào danh sách gồm Context và danh sách danh mục
    public AdapterNhacNho(Context context, List<NhacNho> ds) {
        try {
            this.dsNhacNho = ds;
            this.context = context;
        } catch (Exception ex) {
            Toast.makeText(context, "Có lỗi xảy ra trong AdapterNhacNho/constructor", Toast.LENGTH_SHORT).show();
            Log.e("err: ", ex.getMessage());
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_nhacnho, parent, false);
        return new MyHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof MyHolder) {
                NhacNho nhacNhoHienTai=dsNhacNho.get(position);
                String[] thoiGian=nhacNhoHienTai.getThoiGian().split(" ");
                MyHolder myHolder=(MyHolder) holder;
                myHolder.tvTieuDe.setText(nhacNhoHienTai.getTieuDe());
                myHolder.tvNoiDung.setText(nhacNhoHienTai.getNoiDung());
                myHolder.tvNgay.setText(thoiGian[0]);
                myHolder.tvGio.setText(thoiGian[1]);
                //ấn vào nhắc nhở để chỉnh sửa, xóa
                myHolder.lnNhacNho.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentKhacThemSuaXoaNhacNho fragment=new FragmentKhacThemSuaXoaNhacNho("Chỉnh sửa",nhacNhoHienTai);
                        FragmentTransaction fragmentTransaction=((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container,fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }

        } catch (Exception ex) {
            Toast.makeText(context, "Có lỗi xảy ra trong AdapterNhacNho/onBindViewHolder", Toast.LENGTH_SHORT).show();
            Log.e("err: " + dsNhacNho.get(position), ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return dsNhacNho.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        //khai bao
        TextView tvTieuDe;
        TextView tvNoiDung;
        TextView tvNgay;
        TextView tvGio;
        LinearLayout lnNhacNho ;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //ánh xạ
            lnNhacNho=itemView.findViewById(R.id.lnNhacNho);
           tvTieuDe=itemView.findViewById(R.id.tvTieuDe);
            tvNoiDung=itemView.findViewById(R.id.tvNoiDung);
            tvNgay=itemView.findViewById(R.id.tvNgay);
            tvGio=itemView.findViewById(R.id.tvGio);
        }
    }
}
