package com.project.spendmanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    private IconAdapter icon_adapter;
  private List<Category> data_Extensecategory;
  private List<Category> data_Icomecategory;
  private Button datepickerButton;
  private GridView gvExtenseCategory;
  private Button btnTienThu;
  // them moi
  private ListView lvDanhSachTienChi;
  private EditText edtNhapGhiChu,edtTienChi;


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
    setCurrentDate();

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

    gvExtenseCategory.setAdapter(icon_adapter);

  }


  private void setControl(View view) {
    btnTienThu=view.findViewById(R.id.btnTienThu);
    data_Extensecategory=new ArrayList<>();
    constructListView();
    icon_adapter=new IconAdapter(requireContext(),data_Extensecategory);
    gvExtenseCategory=view.findViewById(R.id.gvExtenseCategory);
    datepickerButton = view.findViewById(R.id.datepickerButton);
    // them moi


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
  private void constructListView() {
   // Drawable top =getResources().getDrawable(R.drawable.baseline_fastfood_24, null);

    data_Extensecategory.add(new Category("Mua sam",R.drawable.baseline_fastfood_25));
    data_Extensecategory.add(new Category("Mua sam",R.drawable.baseline_fastfood_24));
    data_Extensecategory.add(new Category("Mua sam",R.drawable.baseline_fastfood_24));
    data_Extensecategory.add(new Category("Mua sam",R.drawable.baseline_fastfood_24));
    data_Extensecategory.add(new Category("Mua sam",R.drawable.baseline_fastfood_24));
    data_Extensecategory.add(new Category("Mua sam",R.drawable.baseline_fastfood_24));
    data_Extensecategory.add(new Category("Mua sam",R.drawable.baseline_fastfood_24));
//    data_Extensecategory.add(new Category("Mua sắm","a"));
//    data_Extensecategory.add(new Category("Đi nhậu","a"));
//    data_Extensecategory.add(new Category("Điện","a"));
//    data_Extensecategory.add(new Category("Mua sắm","a"));
//    data_Extensecategory.add(new Category("Đi nhậu","a"));
//    data_Extensecategory.add(new Category("Điện","a"));


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
