package com.project.spendmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentDSDanhMucChi extends Fragment {
    RecyclerView rvDSDanhMuc;
    AdapterDSDanhMuc adapterDSDanhMuc;
    List<DanhMuc>ls=new ArrayList<>();
    ImageView ivThoat;
    ImageView btnThemDanhMuc;
    AppCompatButton btnTienThu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_danhmucchi, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {
        try {

            //Bat su kien nut them
           btnThemDanhMuc.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   AdapterIcon.iconDuocChon=0;
                   FragmentChiTietDanhMuc.loaiDM="Chi";
                   FragmentChiTietDanhMuc fragmentChiTietDanhMuc=new FragmentChiTietDanhMuc();
                   FragmentTransaction transaction=(getActivity()).getSupportFragmentManager().beginTransaction();
                   transaction.replace(R.id.container, fragmentChiTietDanhMuc);
                   transaction.addToBackStack(null);
                   transaction.commit();
               }
           });
           //Bat su kien nut thoat
           ivThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sử dụng FragmentManager để quay lại Fragment trước đó
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
           //Bat su kien nut TienThu
            btnTienThu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentDSDanhMucThu fragmentDSDanhMucThu=new FragmentDSDanhMucThu();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,fragmentDSDanhMucThu)
                            .commit();

                }
            });
        }catch ( Exception ex) {
            Toast.makeText(requireContext(),"Có lỗi xảy ra trong FragmentDSDanhMuc/setEvent", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }

    }

    //Phương thức setControl để ánh xạ dữ liệu
    private void setControl(View view) {
        try {
            btnThemDanhMuc=view.findViewById(R.id.ivThemDanhMuc);
            rvDSDanhMuc=view.findViewById(R.id.rvDanhMucChi);
            ivThoat=view.findViewById(R.id.ivThoat);
            btnTienThu=view.findViewById(R.id.btnTienThu);
            ls.clear();
            //Đọc dữ liệu từ csdl
            GiaoDich_Db gd=new GiaoDich_Db(requireContext());
            ls.addAll(gd.LayDanhMucChi());
            //ls.add();
            adapterDSDanhMuc=new AdapterDSDanhMuc(requireContext(),ls);
            rvDSDanhMuc.setAdapter(adapterDSDanhMuc);

        }catch ( Exception ex) {
            Toast.makeText(requireContext(),"Có lỗi xảy ra trong FragmentDSDanhMuc/setControl", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }
}
