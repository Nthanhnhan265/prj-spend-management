package com.project.spendmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentDSDanhMuc extends Fragment {
    RecyclerView rvDSDanhMuc;
    AdapterDSDanhMuc adapterDSDanhMuc;
    List<DanhMuc>ls=new ArrayList<>();
    ImageView btnThemDanhMuc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_danhmuc, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {
        try {
           btnThemDanhMuc.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   FragmentChiTietDanhMuc fragmentChiTietDanhMuc=new FragmentChiTietDanhMuc();
                   FragmentTransaction transaction=(getActivity()).getSupportFragmentManager().beginTransaction();
                   transaction.replace(R.id.container, fragmentChiTietDanhMuc);
                   transaction.addToBackStack(null);
                   transaction.commit();
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
            btnThemDanhMuc=view.findViewById(R.id.btnThemDanhMuc);
            rvDSDanhMuc=view.findViewById(R.id.rvDanhMucChi);
            ls.add(new DanhMuc("Quần áo",R.drawable.ic_money));
            ls.add(new DanhMuc("Tiền điện",R.drawable.ic_money));
            ls.add(new DanhMuc("Ăn uống",R.drawable.ic_money));
            adapterDSDanhMuc=new AdapterDSDanhMuc(requireContext(),ls);
            rvDSDanhMuc.setAdapter(adapterDSDanhMuc);

        }catch ( Exception ex) {
            Toast.makeText(requireContext(),"Có lỗi xảy ra trong FragmentDSDanhMuc/setControl", Toast.LENGTH_SHORT).show();
            Log.e("err: ",ex.getMessage());
        }
    }
}
