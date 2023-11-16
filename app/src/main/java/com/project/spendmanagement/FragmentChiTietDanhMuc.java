package com.project.spendmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentChiTietDanhMuc extends Fragment {
    //Khai báo các control
    RecyclerView recyclerViewIcon;
    RecyclerView recyclerViewMauSac;
    AppCompatButton btnLuuDanhMuc;
    ImageView ivThoat;
    ArrayList<String>mauSac=new ArrayList<>();
    ArrayList<String>icon=new ArrayList<>();
    AdapterIcon adapterIcon;
    AdapterMauSac adapterMauSac;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitietdanhmuc, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {
        try {
            recyclerViewMauSac.setAdapter(adapterMauSac);
            recyclerViewIcon.setAdapter(adapterIcon);
        } catch (Exception ex) {
            Toast.makeText(requireContext(), "Có lỗi xảy ra trong FragmentChiTietDanhMuc/setControl", Toast.LENGTH_SHORT).show();
        }
    }

    //Phương thức setControl để ánh xạ dữ liệu
    private void setControl(View view) {
        try {
            recyclerViewIcon = view.findViewById(R.id.rvDanhSachIcon);
            recyclerViewMauSac = view.findViewById(R.id.rvDanhSachMau);
            btnLuuDanhMuc = view.findViewById(R.id.btnLuuDanhMuc);
            ivThoat = view.findViewById(R.id.ivThoat);
            btnLuuDanhMuc = view.findViewById(R.id.btnLuuDanhMuc);
            Construct();
            adapterIcon=new AdapterIcon(requireContext(),icon);
            adapterMauSac=new AdapterMauSac(requireContext(),mauSac);
        } catch (Exception ex) {
            Toast.makeText(requireContext(), "Có lỗi xảy ra trong FragmentChiTietDanhMuc/setControl", Toast.LENGTH_SHORT).show();
        }
    }

    //Phương thức khởi tạo dữ liệu cho danh sách
    private void Construct() {
        //khởi tạo Icon có sẵn
        icon.add("ic_demo");
        icon.add("ic_demo");
        icon.add("ic_demo");

        //khởi tạo màu sắc
        mauSac.add("#ffffff");
        mauSac.add("#000000");
        mauSac.add("#fff000");
    }
}
