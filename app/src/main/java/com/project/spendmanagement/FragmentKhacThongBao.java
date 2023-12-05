package com.project.spendmanagement;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragmentKhacThongBao extends Fragment  {
    //khai báo
    ImageView ivThoat;
    RecyclerView rvNhacNho;
    AdapterNhacNho adapterNhacNho;
    int ngay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    int thang= Calendar.getInstance().get(Calendar.MONTH)+1;
    int nam= Calendar.getInstance().get(Calendar.YEAR);
    int gio= Calendar.getInstance().get(Calendar.HOUR);
    int phut= Calendar.getInstance().get(Calendar.MINUTE);

    //constructors
    FragmentKhacThongBao() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach_thongbao, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    private void setControl(View view) {
        GiaoDich_Db giaoDichDb=new GiaoDich_Db(requireContext());
        rvNhacNho=view.findViewById(R.id.rvNhacNho);
        ivThoat=view.findViewById(R.id.ivThoat);
        adapterNhacNho=new AdapterNhacNho(requireContext(),giaoDichDb.DocDlNhacNhoDenHen(
                ngay+"/"+thang+"/"+nam+" "+gio+":"+phut
        ));
    }

    private void setEvent() {


        rvNhacNho.setAdapter(adapterNhacNho);
        ivThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng FragmentManager để quay lại Fragment trước đó
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

}
