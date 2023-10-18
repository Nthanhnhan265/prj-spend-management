package com.project.spendmanagement;

//import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import java.util.Calendar;


public class HomeFragment extends Fragment {

  // private EditText dateEditText;
 //  private Button datepickerButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


      //  setControl();
        //setEvent();
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }

//    private void setEvent() {
//        // Su Kien Ngay Thang
//
//        datepickerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year; // Format ngày tháng năm
//                        dateEditText.setText(selectedDate);
//                    }
//                }, year, month, day);
//
//                datePickerDialog.show();
//            }
//        });
    }

//    private void setControl() {
//        dateEditText = getView().findViewById(R.id.dateEditText);
//        datepickerButton = getView().findViewById(R.id.datepickerButton);
//    }

