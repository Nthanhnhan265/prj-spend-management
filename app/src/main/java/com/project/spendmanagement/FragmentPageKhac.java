package com.project.spendmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class FragmentPageKhac extends Fragment {
    //Khai báo
    RecyclerView rcChucNang;
    List<String> chucNang=new ArrayList<>();
    AdapterDSChucNang apdapterChucNang;
    static TextView tvSoDu;
    ImageView hienSoDu;
    boolean anNoiDung = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_page_khac, container, false);
         setControl(view);
         setEvent();
        return view;
    }

    private void setControl(View view) {
        tvSoDu = view.findViewById(R.id.tvSoDu);
        rcChucNang=view.findViewById(R.id.rcChucNang);
        chucNang.clear();
        chucNang.add("Báo cáo thu chi trong năm");
        chucNang.add("Báo cáo danh mục trong năm");
        chucNang.add("Xóa tất cả dữ liệu");
        apdapterChucNang=new AdapterDSChucNang(requireContext(),chucNang);
        hienSoDu=view.findViewById(R.id.hienSoDu);
        tvSoDu.setText("****");
    }
    private void setEvent() {
        GiaoDich_Db giaoDich=new GiaoDich_Db(requireContext());
        rcChucNang.setAdapter(apdapterChucNang);
        NumberFormat num=NumberFormat.getInstance();
        double soDu=giaoDich.LaySoDu();
        num.setGroupingUsed(true);
        //tvSoDu.setText(num.format(soDu));
        hienSoDu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anNoiDung) {
                    // Đặt giá trị thực tế
                    NumberFormat num = NumberFormat.getInstance();
                    double soDu = giaoDich.LaySoDu();
                    num.setGroupingUsed(true);
                    tvSoDu.setText(num.format(soDu));
                } else {
                    // cap nhat lai ****
                    tvSoDu.setText("****");
                }
                anNoiDung = !anNoiDung;
            }
        });

    }
}