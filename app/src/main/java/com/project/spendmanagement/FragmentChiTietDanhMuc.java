package com.project.spendmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentChiTietDanhMuc extends Fragment {
    //Khai báo các control
    RecyclerView recyclerViewIcon;
    //RecyclerView recyclerViewMauSac;
    AppCompatButton btnLuuDanhMuc;
    ImageView ivThoat;
    //ArrayList<String>mauSac=new ArrayList<>();
    ArrayList<String>icon=new ArrayList<>();
    AdapterIcon adapterIcon;
    AdapterMauSac adapterMauSac;
    EditText edtTenDM;
    MainActivity main=(MainActivity) getActivity();
    public static String loaiDM=null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitietdanhmuc, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {
        try {
            recyclerViewIcon.setAdapter(adapterIcon);
            ivThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sử dụng FragmentManager để quay lại Fragment trước đó
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
            //thêm danh mục
            btnLuuDanhMuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //kiểm tra dữ liệu đầu vào
                    if(!edtTenDM.getText().equals("") && adapterIcon.getIconDuocChon()!=-1) {
                        DanhMuc dm=new DanhMuc(0,edtTenDM.getText().toString().trim(),loaiDM,adapterIcon.getIconDuocChon());

                        GiaoDich_Db giaoDich=new GiaoDich_Db(requireContext());
                        if(giaoDich.ThemDanhMuc(dm)>=0) {
                            Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            requireActivity().getSupportFragmentManager().popBackStack(); //thêm thành công và thoát
                        }else {
                            Toast.makeText(requireContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(requireContext(), "Vui lòng chọn thông tin!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(requireContext(), "Có lỗi xảy ra trong FragmentChiTietDanhMuc/setControl", Toast.LENGTH_SHORT).show();
        }
    }

    //Phương thức setControl để ánh xạ dữ liệu
    private void setControl(View view) {
        try {
            recyclerViewIcon = view.findViewById(R.id.rvDanhSachIcon);
            //recyclerViewMauSac = view.findViewById(R.id.rvDanhSachMau);
            btnLuuDanhMuc = view.findViewById(R.id.btnLuuDanhMuc);
            ivThoat = view.findViewById(R.id.ivThoat);
            btnLuuDanhMuc = view.findViewById(R.id.btnLuuDanhMuc);
            adapterIcon=new AdapterIcon(requireContext(),MainActivity.icons);
            edtTenDM=view.findViewById(R.id.edtTenDM);
        } catch (Exception ex) {
            Toast.makeText(requireContext(), "Có lỗi xảy ra trong FragmentChiTietDanhMuc/setControl", Toast.LENGTH_SHORT).show();
        }
    }

    //Phương thức khởi tạo dữ liệu cho danh sách
    private void Construct() {



    }
}
