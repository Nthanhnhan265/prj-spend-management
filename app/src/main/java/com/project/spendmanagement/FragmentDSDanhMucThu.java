package com.project.spendmanagement;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDSDanhMucThu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDSDanhMucThu extends Fragment {
    //Khai bao
    RecyclerView rvDSDanhMuc;
    AdapterDSDanhMuc adapterDSDanhMuc;
    List<DanhMuc> ls=new ArrayList<>();
    ImageView ivThoat;
    ImageView btnThemDanhMuc;
    AppCompatButton btnTienChi;
    //constructor
    public FragmentDSDanhMucThu() {

    }

    public static FragmentDSDanhMucThu newInstance(String param1, String param2) {
        FragmentDSDanhMucThu fragment = new FragmentDSDanhMucThu();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_danhmucthu, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    //Phuong thuc them su kien cho control
    private void setEvent() {
        try {
            //Bat su kien nut them
            btnThemDanhMuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdapterIcon.iconDuocChon=0;
                    FragmentChiTietDanhMuc.loaiDM="Thu";
                    FragmentChiTietDanhMuc fragmentChiTietDanhMuc=new FragmentChiTietDanhMuc();
                    FragmentTransaction transaction=(getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentChiTietDanhMuc);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            });
            //bat su kien nut thoat
            ivThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sử dụng FragmentManager để quay lại Fragment trước đó
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
            //bat su kien nut tienchi
            btnTienChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentDSDanhMucChi fragmentChiTietDanhMuc=new FragmentDSDanhMucChi();
                    FragmentTransaction transaction=(getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentChiTietDanhMuc);
                    transaction.commit();
                }
            });

        }catch ( Exception ex) {
            Toast.makeText(requireContext(),"Có lỗi xảy ra trong FragmentDSDanhMucThu/setEvent", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }

    }

    //Phương thức setControl để ánh xạ dữ liệu
    private void setControl(View view) {
        try {
            btnThemDanhMuc=view.findViewById(R.id.ivThemDanhMuc);
            rvDSDanhMuc=view.findViewById(R.id.rvDanhMucChi);
            ivThoat=view.findViewById(R.id.ivThoat);
            btnTienChi=view.findViewById(R.id.btnTienChi);
            ls.clear();
            //Đọc dữ liệu từ csdl
            GiaoDich_Db gd=new GiaoDich_Db(requireContext());
            ls.addAll(gd.LayDanhMucThu());
            adapterDSDanhMuc=new AdapterDSDanhMuc(requireContext(),ls);
            rvDSDanhMuc.setAdapter(adapterDSDanhMuc);

        }catch ( Exception ex) {
            Toast.makeText(requireContext(),"Có lỗi xảy ra trong FragmentDSDanhMucThu/setControl", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }

}