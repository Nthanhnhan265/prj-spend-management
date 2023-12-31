package com.project.spendmanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

public class AdapterGiaoDich extends RecyclerView.Adapter {
    //fields
    public static int maDuocChon;
    public static GiaoDich giaoDichDuocChon;
    private List<GiaoDich> data;
    private Context context; 
    private OnItemClickListener clickListener; // Thêm trường này
    public AdapterGiaoDich(Context ct,List<GiaoDich> data) {
        this.data=data;
        context=ct; 
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_extense,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GiaoDich giaoDichHienTai = data.get(position);
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            DanhMuc danhMuc = giaoDichHienTai.getDanhMuc();
            if (danhMuc != null) {
                NumberFormat num=NumberFormat.getInstance();
                num.setGroupingUsed(true);
                myHolder.tvCategory.setText(danhMuc.getTenDanhMuc());
                myHolder.tvDesc.setText("(" + giaoDichHienTai.getGhiChu() + ")");
                myHolder.tvValue.setText(num.format(giaoDichHienTai.getGiaTri()));
                myHolder.ivCategory.setImageResource(danhMuc.getIcon());
                myHolder.lnItemThuChi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        maDuocChon = giaoDichHienTai.getMaGD();
                        giaoDichDuocChon=giaoDichHienTai;
                        String loaiDanhMuc=giaoDichDuocChon.getDanhMuc().getLoaiDM();
                        if(loaiDanhMuc.equals("Thu")) {
                            Log.d("Loai ", "Thu ok:"+ loaiDanhMuc);
                            FragmentChinhSuaThu newFragment = new FragmentChinhSuaThu();
                            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } else {
                            FragmentChinhSuaChi newFragment = new FragmentChinhSuaChi();
                            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                    }
                });
            } else {
                // Xử lý trường hợp danhMuc là null nếu cần
//                Toast.makeText(context, "Danh mục là null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
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
        LinearLayout lnItemThuChi;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
             ivCategory=itemView.findViewById(R.id.ivCategory);
             tvCategory=itemView.findViewById(R.id.tvCategory);
             tvDesc=itemView.findViewById(R.id.tvDesc);
             tvValue=itemView.findViewById(R.id.tvValue);
             lnItemThuChi=itemView.findViewById(R.id.itemThuChi);
             
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }
}
