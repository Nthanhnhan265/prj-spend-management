package com.project.spendmanagement;

import android.content.Context;
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

import java.util.List;

public class AdapterGiaoDich extends RecyclerView.Adapter {
    //fields
    private List<GiaoDich> data;
    private Context context;
    private OnItemClickListener listener;
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
        GiaoDich giaoDichHienTai=data.get(position);
        if(holder instanceof MyHolder) {

            MyHolder myHolder=(MyHolder) holder;
            ((MyHolder) holder).tvCategory.setText(giaoDichHienTai.getDanhMuc().getTenDanhMuc());
            ((MyHolder) holder).tvDesc.setText(giaoDichHienTai.getGhiChu());
            ((MyHolder) holder).tvValue.setText(giaoDichHienTai.getTextOfValue());
            ((MyHolder) holder).ivCategory.setImageResource(giaoDichHienTai.getDanhMuc().getIcon());
            // sự kiện click item ,chuyển sang chi tiết ite,
            ((MyHolder) holder).lnItemThuChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentChinhSua newFragment = new FragmentChinhSua();
                    // Lấy GiaoDich tại vị trí được nhấn trong RecyclerView
               //     GiaoDich giaoDich = data.get(position);
              //      newFragment.setGiaoDich(giaoDich);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, newFragment); // Thay thế R.id.fragment_container bằng ID của Container Fragment của bạn
                    transaction.addToBackStack(null); // (Tùy chọn) Cho phép người dùng quay lại Fragment trước đó
                    transaction.commit();

                }

            });
            ((MyHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onItemClick(holder.getAdapterPosition());
                    }
                }
            });

        }

    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public GiaoDich getItem(int position) {
        return data.get(position);
    }
    public GiaoDich getItemAtPosition(int position) {
        if (position >= 0 && position < data.size()) {
            return data.get(position);
        }
        return null;
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
