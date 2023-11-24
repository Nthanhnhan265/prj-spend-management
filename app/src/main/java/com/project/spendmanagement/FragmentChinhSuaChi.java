package com.project.spendmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentChinhSuaChi extends Fragment {

    private Button btnDatePicker;
    private GridView gvDanhMucThu;
    private List<DanhMuc> listDanhMuc;
    private AdapterDanhMuc iconDanhMucAdapter;
    private Button btnChinhSuaThu;
    private EditText edtNhapGhiChu, edtTienThu;
    private TextView tvXoa;


    MainActivity main;
    GiaoDich_Db giaoDich_db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_giaodich, container, false);
        setControl(view);
        setEvent();

        return view;
    }

    private void setEvent() {
        try {
            main = (MainActivity) getActivity();
            setCurrentDate();
            GiaoDich gdDuocChon = AdapterGiaoDich.giaoDichDuocChon;
            Log.d("info chinh sua: ", gdDuocChon.toString());
            //hiển thị dữ liệu
            edtNhapGhiChu.setText(gdDuocChon.getGhiChu());
            edtTienThu.setText(String.valueOf(gdDuocChon.getGiaTri()));
            btnDatePicker.setText(gdDuocChon.getNgayGD());
            gvDanhMucThu.setSelection(listDanhMuc.indexOf(gdDuocChon.getDanhMuc()));
            gvDanhMucThu.getSelectedItem();
            btnChinhSuaThu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
//                        Log.d("FragmentChinhSuaThu", "btnChinhSuaThu Clicked");
//
//                        // Kiểm tra và cập nhật thông tin giao dịch
//                        GiaoDich gd = AdapterGiaoDich.giaoDichDuocChon;
//
//                        Log.d("FragmentChinhSuaThu", "Found matching GiaoDich");
//
//                        Log.d("FragmentChinhSuaThu", "Before - Ngay: " + gd.getNgayGD() + ", GhiChu: " + gd.getGhiChu() + ", GiaTri: " + gd.getGiaTri() + ", DanhMuc: " + (gd.getDanhMuc() != null ? gd.getDanhMuc().getTenDanhMuc() : "null"));
//
//                        // Kiểm tra và cập nhật thông tin giao dịch
//                        if (!gd.getNgayGD().equals(btnDatePicker.getText().toString())) {
//                            gd.setNgayGD(btnDatePicker.getText().toString());
//                        }
//                        if (!gd.getGhiChu().equals(edtNhapGhiChu.getText().toString())) {
//                            gd.setGhiChu(edtNhapGhiChu.getText().toString());
//                        }
//                        if (gd.getGiaTri() != (edtTienThu.getText().toString())) {
//                            gd.setGiaTri(Integer.parseInt(edtTienThu.getText().toString()));
//                        }
//
//                        DanhMuc danhMucDuocChon = iconDanhMucAdapter.getDanhMucDuocChon();
//                        if (gd.getDanhMuc() != null && !gd.getDanhMuc().getTenDanhMuc().equals(danhMucDuocChon.getTenDanhMuc())) {
//                            gd.getDanhMuc().setTenDanhMuc(danhMucDuocChon.getTenDanhMuc());
//                            gd.getDanhMuc().setIcon(danhMucDuocChon.getIcon());
//                        }
                        // Gọi phương thức SuaDl của GiaoDich_Db để cập nhật vào cơ sở dữ liệu
                        gdDuocChon.setNgayGD(btnDatePicker.getText().toString());
                        gdDuocChon.setGhiChu(edtNhapGhiChu.getText().toString());
                        gdDuocChon.setGiaTri(Integer.parseInt(edtTienThu.getText().toString()));
                        if(iconDanhMucAdapter.getDanhMucDuocChon()!=null) {
                            gdDuocChon.setDanhMuc(iconDanhMucAdapter.danhMucDuocChon);
                            Toast.makeText(requireContext(), "Vui lòng chọn danh mục!", Toast.LENGTH_SHORT).show();
                        }
                        giaoDich_db.SuaDl(gdDuocChon);
                        Log.d("FragmentChinhSuaThu", "After - Ngay: " + gdDuocChon.getNgayGD() + ", GhiChu: " + gdDuocChon.getGhiChu() + ", GiaTri: " + gdDuocChon.getGiaTri() + ", DanhMuc: " + (gdDuocChon.getDanhMuc() != null ? gdDuocChon.getDanhMuc().getTenDanhMuc() : "null"));
                        Toast.makeText(requireContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();

                    } catch (Exception ex) {
                        Toast.makeText(requireContext(), "Vui lòng nhập thông tin!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            gvDanhMucThu.setAdapter(iconDanhMucAdapter);

            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCustomDatePickerDialog();
                }

            });
            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                            btnDatePicker.setText(selectedDate);
                        }
                    }, year, month, day);

                    datePickerDialog.show();
                }
            });
            tvXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc chắn muốn xóa?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        // Gọi phương thức XoaDL của GiaoDich_Db để xóa khỏi cơ sở dữ liệu
                                        giaoDich_db.XoaDL(AdapterGiaoDich.giaoDichDuocChon.getMaGD());
                                        main.listGiaoDich.remove(AdapterGiaoDich.giaoDichDuocChon);
                                        Toast.makeText(requireContext(), "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
                                        main.chuyenDenLich();
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
        } catch (Exception ex) {
            Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            throw (ex);
        }
    }

    private void setControl(View view) {
        btnChinhSuaThu = view.findViewById(R.id.btnChinhSuaThu);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);
        gvDanhMucThu = view.findViewById(R.id.gvDanhSachDMThu);
        listDanhMuc = new ArrayList<>();
        constructGridView();
        iconDanhMucAdapter = new AdapterDanhMuc(requireContext(), listDanhMuc);
        edtTienThu = view.findViewById(R.id.edtTienThu);
        edtNhapGhiChu = view.findViewById(R.id.edtNhapGhiChu);
        tvXoa = view.findViewById(R.id.tvXoa);
        giaoDich_db = new GiaoDich_Db(this.requireContext());
    }

    private boolean isValidData() {
        // Kiểm tra và trả về true nếu dữ liệu hợp lệ, ngược lại trả về false
        return !TextUtils.isEmpty(btnDatePicker.getText().toString()) &&
                !TextUtils.isEmpty(edtNhapGhiChu.getText().toString()) &&
                !TextUtils.isEmpty(edtTienThu.getText().toString());
    }

    private void showCustomDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.CustomDatePickerDialogTheme, null, year, month, day);

        // Đặt background cho DatePickerDialog từ drawable custom_background.xml
        datePickerDialog.getDatePicker().setBackgroundResource(R.drawable.custom_background);
        datePickerDialog.show();
    }

    private void setCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        try {
            String selectedDate = day + "/" + (month + 1) + "/" + year;
            btnDatePicker.setText(selectedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //làm cơ sở dữ liệu
    private void constructGridView() {
        GiaoDich_Db giaoDichDb = new GiaoDich_Db(requireContext());
        listDanhMuc.addAll(giaoDichDb.LayDanhMucChi());
        //dùng mở giao diện chỉnh sửa, thêm danh mục
        listDanhMuc.add(new DanhMuc(000, "Thêm", null, R.drawable.ic_add));


    }
}



