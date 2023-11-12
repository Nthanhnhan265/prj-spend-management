package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PageLich extends Fragment  {
    private RecyclerView rcShow;
    private Button datepickerButton;
    private TextView tvThu, tvChi;
    public static AdapterLich dateAdapter;


    MainActivity main;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_lich, container, false);
        setControl(view);
        setEvent();

        return view;
    }

    private void setEvent() {
        tvThu.setText(tinhTongThu()+"");

        rcShow.setAdapter(dateAdapter);
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
                            datepickerButton.setText(selectedDate + "("+ dayOfWeek+")");
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
        main=(MainActivity) getActivity() ;
        rcShow=view.findViewById(R.id.rcShow);
        tvThu=view.findViewById(R.id.tvTienThu);
        tvChi=view.findViewById(R.id.tvTienChi);

        if(main.listGiaoDich.size()!=0) {
            dateAdapter=new AdapterLich(requireContext(),main.listGiaoDich);
        }

        datepickerButton = view.findViewById(R.id.btnDatePicker);
    }

    private int tinhTongThu(){
        int tong=0;
        for(GiaoDich gd: main.listGiaoDich) {
            if(gd instanceof ThuNhap) {
                tong+=Integer.parseInt(gd.getGiaTri());
            }
        }
        return tong;
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

    public static void capNhatList() {
        dateAdapter.notifyDataSetChanged();
    }
}
