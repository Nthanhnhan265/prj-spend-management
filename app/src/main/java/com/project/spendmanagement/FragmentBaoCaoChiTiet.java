package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FragmentBaoCaoChiTiet extends Fragment  {
    //khai báo
    private RecyclerView rcShow;
    public static AdapterLich dateAdapter;
    List<GiaoDich>data_gd=new ArrayList<>();
    GiaoDich_Db giaoDichDb;
    String maDanhMuc;
    String tenDanhMuc;
    TextView tvTieuDe;


    //constructors
    FragmentBaoCaoChiTiet() {

    }
    FragmentBaoCaoChiTiet (String maDanhMuc,String tenDanhMuc) {
        this.maDanhMuc =maDanhMuc;
        this.tenDanhMuc =tenDanhMuc;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_baocao, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    private void setControl(View view) {
        rcShow=view.findViewById(R.id.rcShow);
        tvTieuDe=view.findViewById(R.id.tvTieuDe);
    }
    private void setEvent() {
        try {
            int thang=11;
            int nam=2023;
            tvTieuDe.setText(tenDanhMuc);
            giaoDichDb = new GiaoDich_Db(this.requireContext());
            data_gd=giaoDichDb.LayGiaoDichTheoDanhMuc(thang,nam,Integer.parseInt(maDanhMuc));
            dateAdapter = new AdapterLich(requireContext(), data_gd);
            rcShow.setAdapter(dateAdapter);

        }
        catch (Exception ex)
        {
            Toast.makeText(requireContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
