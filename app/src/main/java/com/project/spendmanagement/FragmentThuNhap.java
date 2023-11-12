package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentThuNhap extends Fragment {
    private Button btnDatePicker;
    private GridView gvDanhMucThu;
    private List<DanhMuc> listThuNhap;
    private AdapterDanhMuc iconDanhMucAdapter;
    private Button btnTienChi;
    private Button btnThu ;
    private EditText edtNhapGhiChu, edtTienThu;
    private ImageView ivNgayTruoc, ivNgaySau;
    MainActivity main;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_thunhap, container, false);
        Toast.makeText(requireContext(), "Page Thu", Toast.LENGTH_SHORT).show();
        setControl(view);
        setEvent();

        return view;
    }

    private void setEvent() {

        ivNgayTruoc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dateString = btnDatePicker.getText().toString();
                dateString=dateString.substring(0,dateString.indexOf(' '));
                Date inputDate = new Date(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(inputDate);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                inputDate=calendar.getTime();
                btnDatePicker.setText(inputDate.toString());
            }
        });
        main=(MainActivity) getActivity();
        setCurrentDate();
        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity activity = (MainActivity) getActivity();
                CapNhatDuLieu listener = (CapNhatDuLieu) activity;

                if (listener != null) {
                    try {
                        // Thêm mục mới thông qua DataUpdateListener
                        listener.themGiaoDich(new ThuNhap(btnDatePicker.getText().toString(),
                                edtNhapGhiChu.getText().toString(),
                                Integer.parseInt(edtTienThu.getText().toString()),
                                iconDanhMucAdapter.getDanhMucDuocChon()));
                        Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        edtNhapGhiChu.setText("");
                        edtTienThu.setText("");
                        iconDanhMucAdapter.resetSelectedItem();
                    } catch (Exception ex) {
                        Toast.makeText(requireContext(), "Vui lòng nhập thông tin!", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
        btnTienChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main=(MainActivity) getActivity();
                main.chuyenDenNhapChi();
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
                        btnDatePicker.setText(selectedDate );
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });


    }

    private void setControl(View view) {
        btnThu=view.findViewById(R.id.btnNhapTienThu);
        btnTienChi=view.findViewById(R.id.btnTienChi);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);
        gvDanhMucThu =view.findViewById(R.id.gvDanhSachDM);
        listThuNhap=new ArrayList<>();
        constructGridView();
        iconDanhMucAdapter =new AdapterDanhMuc(requireContext(),listThuNhap);
        edtTienThu=view.findViewById(R.id.edtTienThu);
        edtNhapGhiChu=view.findViewById(R.id.edtNhapGhiChu);
        ivNgayTruoc =view.findViewById(R.id.ivNgayTruoc);
        ivNgaySau =view.findViewById(R.id.ivNgaySau);
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
            btnDatePicker.setText(selectedDate );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void constructGridView() {
        listThuNhap.add(new DanhMuc("Trợ cấp",R.drawable.ic_money));
        listThuNhap.add(new DanhMuc("Đầu tư",R.drawable.baseline_fastfood_25));
        listThuNhap.add(new DanhMuc("Lãnh lương",R.drawable.baseline_fastfood_25));




    }
}
