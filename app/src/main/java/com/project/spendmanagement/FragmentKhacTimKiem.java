package com.project.spendmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragmentKhacTimKiem extends Fragment  {
    private RecyclerView rcShow;
    EditText edtTimKiem;
    AppCompatButton btnTim;
    //them moi
    List<GiaoDich>data_gd=new ArrayList<>();
    List<ChiTieu>data_chitieu=new ArrayList<>();
    AdapterLich adapterLich;
    AdapterGiaoDich adapterGiaoDich;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_khac_timkiem, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setControl(View view) {
        rcShow=view.findViewById(R.id.rcShow);
        edtTimKiem=view.findViewById(R.id.edtTimKiem);
        btnTim=view.findViewById(R.id.btnTim);
    }

    private void setEvent() {
        try {
            btnTim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(edtTimKiem.getText().toString()!="") {
                        GiaoDich_Db giaoDichDb=new GiaoDich_Db(requireContext());
                        adapterLich = new AdapterLich(requireContext(), giaoDichDb.TimKiem(edtTimKiem.getText().toString()));
                        rcShow.setAdapter(adapterLich);
                    }else {
                        Toast.makeText(requireContext(), "Vui lòng nhập thông tin cần tìm", Toast.LENGTH_SHORT).show();
                    }
                }
            });
                    }
        catch (Exception ex)
        {
            Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
        }
    }
}
