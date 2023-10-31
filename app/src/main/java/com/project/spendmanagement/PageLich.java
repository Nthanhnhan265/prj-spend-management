package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class PageLich extends Fragment {
    private RecyclerView rcShow;
    private Button datepickerButton;

    private List<String>list_dates;
    private DateAdapter dateAdapter;
    private List<Transaction> list_Transaction;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_lich, container, false);
        setControl(view);
        setEvent();

        return view;
    }

    private void setEvent() {

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
        list_dates=new ArrayList<>();
        list_Transaction=new ArrayList<>();
        constructExtense();
        constructDate();
        rcShow=view.findViewById(R.id.rcShow);

        dateAdapter=new DateAdapter(requireContext(),list_Transaction);
        datepickerButton = view.findViewById(R.id.datepickerButton);
        // them moi



    }

    private void constructDate() {
    }
    private  void constructExtense() {
        list_Transaction.add(new Extense("28/1/2023","Mua hang shopee",280000,"Mua sam"));
        list_Transaction.add(new Extense("28/1/2023","Mua hang bach hoa",30000,"Mua sam"));
        list_Transaction.add(new Extense("29/1/2023","Mua quan ao",30000,"Mua sam"));
        list_Transaction.add(new Income("30/1/2023","Trung so",300000,"Tro cap"));
        list_Transaction.add(new Income("1/2/2023","Ba me gui tien",3000000,"Tro cap"));
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

}
