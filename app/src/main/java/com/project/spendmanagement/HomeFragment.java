package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ListView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

  private IconAdapter icon_apt;
  private List<Category> data_category;
  private Button datepickerButton;
  private GridView gvExtenseCategory;
  private Button btnThuChi;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_chitieu, container, false);
    setControl(view);
    setEvent();

    return view;
  }

  private void setEvent() {
    //lien ket man hinh nhap thu ở đây
    //btnThuChi.setOnClickListener();
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

    gvExtenseCategory.setAdapter(icon_apt);
  }

  private void setControl(View view) {
    btnThuChi=view.findViewById(R.id.btnTienThu);
    data_category=new ArrayList<>();
    constructListView();
    icon_apt=new IconAdapter(requireContext(),data_category);
    gvExtenseCategory=view.findViewById(R.id.gvExtenseCategory);
    datepickerButton = view.findViewById(R.id.datepickerButton);

  }
  private void constructListView() {
    data_category.add(new Category("mua sam","ok"));
    data_category.add(new Category("Mua sắm","a"));
    data_category.add(new Category("Đi nhậu","a"));
    data_category.add(new Category("Điện","a"));data_category.add(new Category("Mua sắm","a"));
    data_category.add(new Category("Đi nhậu","a"));
    data_category.add(new Category("Điện","a"));

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
