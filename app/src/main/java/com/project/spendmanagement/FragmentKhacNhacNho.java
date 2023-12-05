package com.project.spendmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentKhacNhacNho extends Fragment  {
    //khai báo
    ImageView ivThemNhacNho;
    ImageView ivThoat;
    RecyclerView rvNhacNho;
    AdapterNhacNho adapterNhacNho;
    //constructors
    FragmentKhacNhacNho() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach_nhacnho, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    private void setControl(View view) {
        GiaoDich_Db giaoDichDb=new GiaoDich_Db(requireContext());
        ivThemNhacNho=view.findViewById(R.id.ivThemNhacNho);
        ivThoat=view.findViewById(R.id.ivThoat);
        rvNhacNho=view.findViewById(R.id.rvNhacNho);
        adapterNhacNho=new AdapterNhacNho(requireContext(),giaoDichDb.DocDlNhacNho());
    }
    private void setEvent() {
        rvNhacNho.setAdapter(adapterNhacNho);
        ivThemNhacNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentKhacThemSuaXoaNhacNho fragment=new FragmentKhacThemSuaXoaNhacNho("Thêm");
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        ivThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng FragmentManager để quay lại Fragment trước đó
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

}
