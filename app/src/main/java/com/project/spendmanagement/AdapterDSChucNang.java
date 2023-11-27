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

public class AdapterDSChucNang extends RecyclerView.Adapter {
    //khai báo
    private Context context;
    private List<String> dsChucNang = new ArrayList<>();

    //Truyền vào danh sách gồm Context và danh sách danh mục
    public AdapterDSChucNang(Context context, List<String> ds) {
        try {
            this.dsChucNang = ds;
            this.context = context;
        } catch (Exception ex) {
            Toast.makeText(context, "Có lỗi xảy ra trong AdapterDSChucNang/constructor", Toast.LENGTH_SHORT).show();
            Log.e("err: ", ex.getMessage());
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_chucnang, parent, false);
        return new MyHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof MyHolder) {
                //Lấy danh mục trong danh sách khi biết vị trí
                String chucNang = dsChucNang.get(position);
                MyHolder myHolder = (MyHolder) holder; //ép kiểu holder;
                myHolder.tvTenChucNang.setText(chucNang);
                //tạo sự kiện ấn vào 1 chức năng trong danh sách và thực thi
                myHolder.lnChucNang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Chuyển qua màn hình xem báo cáo thu chi trong năm
                        if (chucNang == "Báo cáo thu chi trong năm") {
                            FragmentKhacBaoCaoThuChi fragmentKhacBaoCaoTC = new FragmentKhacBaoCaoThuChi();
                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragmentKhacBaoCaoTC).addToBackStack(null).commit();
                        }
                        //Chuyển qua màn hình xem báo cáo danh mục trong năm
                        else if (chucNang == "Báo cáo danh mục trong năm") {
                            FragmentKhacBaoCaoDMChi fragmentKhacBaoCaoDM = new FragmentKhacBaoCaoDMChi();
                            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.container, fragmentKhacBaoCaoDM).addToBackStack(null).commit();
                        }
                        //xóa tất cả dữ liệu có trong ứng dụng
                        else if (chucNang == "Xóa tất cả dữ liệu") {
                            Log.d("NÚT XÓA", "Đã ấn");
                            new AlertDialog.Builder(context)
                                    .setTitle("Xóa toàn bộ dữ liệu?")
                                    .setMessage("Bạn có chắc chắn")
                                    //bắt sự kiện nút đồng ý
                                    .setPositiveButton("Xoá toàn bộ", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                GiaoDich_Db giaoDichDb = new GiaoDich_Db(context);
                                                giaoDichDb.deleteDatabase(context);
                                                giaoDichDb.ChenDanhMuc();
                                                FragmentPageKhac.tvSoDu.setText("0");
                                            } catch (Exception ex) {
                                                Toast.makeText(context, "Có lỗi xảy ra khi xóa dữ liệu!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    //bắt sự kiện nút hủy
                                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();

                                        }
                                    }).show();
                        }

                    }
                });
            }

        } catch (Exception ex) {
            Toast.makeText(context, "Có lỗi xảy ra trong AdapterDSDanhMuc/onBindViewHolder", Toast.LENGTH_SHORT).show();
            Log.e("err: " + dsChucNang.get(position), ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return dsChucNang.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTenChucNang;
        LinearLayout lnChucNang;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //ánh xạ
            tvTenChucNang = itemView.findViewById(R.id.tvTenChucNang);
            lnChucNang = itemView.findViewById(R.id.lnChucNang);
        }
    }
}
