package com.project.spendmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentChinhSuaDanhMuc extends Fragment {
    //Khai báo các control
    RecyclerView recyclerViewIcon;
    AppCompatButton btnLuuDanhMuc;
    ImageView ivThoat;
    ArrayList<String> mauSac=new ArrayList<>();
    ArrayList<String>icon=new ArrayList<>();
    AdapterIcon adapterIcon;
    EditText edtTenDM;
    MainActivity main=(MainActivity) getActivity();
    DanhMuc danhMuc=AdapterDSDanhMuc.danhMucDuocChon;
    public static String loaiDM=null;
    TextView tvXoa;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitietdanhmuc, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {
        try {

            //Hien thi thong tin danh muc da chon
            edtTenDM.setText(danhMuc.getTenDanhMuc());
            AdapterIcon.iconDuocChon= danhMuc.getIcon();
            ivThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sử dụng FragmentManager để quay lại Fragment trước đó
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
            tvXoa.setVisibility(View.VISIBLE);
            tvXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc chắn muốn xóa?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        GiaoDich_Db giaoDichDb=new GiaoDich_Db(requireContext());
                                        giaoDichDb.XoaDanhMuc(danhMuc.getId());
                                        Toast.makeText(requireContext(), "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
                                        requireActivity().getSupportFragmentManager().popBackStack();
                                    } catch (Exception ex) {
                                        Toast.makeText(requireContext(), "Có lỗi xảy ra khi xóa dữ liệu!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(requireContext(), "Hủy", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            });
            btnLuuDanhMuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!edtTenDM.getText().equals("") && adapterIcon.getIconDuocChon()!=-1) {
                        //tạo đối tượng danh mục với Mã cũ và thông tin mới
                        DanhMuc dm=new DanhMuc(danhMuc.getId(),edtTenDM.getText().toString().trim(), danhMuc.getLoaiDM(), adapterIcon.getIconDuocChon());
                        GiaoDich_Db giaoDichDb=new GiaoDich_Db(requireContext());
                        //kiểm tra có thêm thành công hay không
                        if(giaoDichDb.SuaDanhMuc(dm)>=0) {
                            Toast.makeText(requireContext(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            requireActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(requireContext(), "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Vui lòng chọn thông tin!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            recyclerViewIcon.setAdapter(adapterIcon);
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
            Construct();
            adapterIcon=new AdapterIcon(requireContext(),icon);
            edtTenDM=view.findViewById(R.id.edtTenDM);
            tvXoa=view.findViewById(R.id.tvXoa);
        } catch (Exception ex) {
            Toast.makeText(requireContext(), "Có lỗi xảy ra trong FragmentChinhSuaDanhMuc/setControl", Toast.LENGTH_SHORT).show();
        }
    }

    //Phương thức khởi tạo dữ liệu cho danh sách
    private void Construct() {

        //khởi tạo Icon có sẵn
        icon.addAll(main.icons);
    }

}
