package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PageThu extends Fragment {
    private Button datepickerButton;
    private GridView gvIcomeCategory;
    private List<Category> listThuNhap;
    private IconAdapter icon_adapter;
    private Button btnTienChi;
    private Button btnThu ;
    private EditText edtNhapGhiChu, edtTienThu;
    private ImageView ivBefore, ivNext;
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

        ivBefore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String dateString = datepickerButton.getText().toString();
                dateString=dateString.substring(0,dateString.indexOf(' '));
                Date inputDate = new Date(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(inputDate);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                inputDate=calendar.getTime();
                datepickerButton.setText(inputDate.toString());
            }
        });
        main=(MainActivity) getActivity();
        setCurrentDate();
        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity activity = (MainActivity) getActivity();
                DataUpdateListener listener = (DataUpdateListener) activity;

                if (listener != null) {
                    try {
                        // Thêm mục mới thông qua DataUpdateListener
                        listener.onAddTransaction(new Extense(datepickerButton.getText().toString(),
                                "("+edtNhapGhiChu.getText().toString()+")",
                                Integer.parseInt(edtTienThu.getText().toString()),
                                icon_adapter.getSelectedText()));
                        Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        edtNhapGhiChu.setText("");
                        edtTienThu.setText("");
                        icon_adapter.resetSelectedItem();
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
        gvIcomeCategory.setAdapter(icon_adapter);
        datepickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDatePickerDialog();
            }

        });
        datepickerButton.setOnClickListener(new View.OnClickListener() {
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

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date date = sdf.parse(selectedDate);
                            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                            String dayOfWeek = dayFormat.format(date); // Lấy thứ

                            // Xuất ngày và thứ
                            datepickerButton.setText(selectedDate + " ("+ dayOfWeek+")");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });


    }

    private void setControl(View view) {
        btnThu=view.findViewById(R.id.btnNhapTienThu);
        btnTienChi=view.findViewById(R.id.btnTienChi);
        datepickerButton = view.findViewById(R.id.datepickerButton);
        gvIcomeCategory=view.findViewById(R.id.gvIcomeCategory);
        listThuNhap=new ArrayList<>();
        constructGridView();
        icon_adapter=new IconAdapter(requireContext(),listThuNhap);
        edtTienThu=view.findViewById(R.id.edtTienThu);
        edtNhapGhiChu=view.findViewById(R.id.edtNhapGhiChu);
        ivBefore=view.findViewById(R.id.ivBefore);
        ivNext=view.findViewById(R.id.ivNext);
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
            //String dayOfWeek = dayFormat.format(Date.parse(selectedDate)); // Lấy thứ

            // Xuất ngày và thứ
            datepickerButton.setText(selectedDate );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void constructGridView() {
        listThuNhap.add(new Category("Trợ cấp",R.drawable.ic_money));
        listThuNhap.add(new Category("Lãnh lương",R.drawable.ic_next));
        listThuNhap.add(new Category("Đầu tư",R.drawable.baseline_fastfood_25));
        listThuNhap.add(new Category("Trợ cấp",R.drawable.baseline_fastfood_25));
        listThuNhap.add(new Category("Lãnh lương",R.drawable.baseline_fastfood_25));
        listThuNhap.add(new Category("Đầu tư",R.drawable.baseline_fastfood_25));
        listThuNhap.add(new Category("Trợ cấp",R.drawable.baseline_fastfood_25));
        listThuNhap.add(new Category("Lãnh lương",R.drawable.baseline_fastfood_25));
        listThuNhap.add(new Category("Đầu tư",R.drawable.baseline_fastfood_25));



    }
}
