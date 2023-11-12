package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment_ChiTieu extends Fragment {
    private AdapterDanhMuc icon_adapter;
  private List<DanhMuc> listChiTieu;
  private Button datepickerButton;
 // private GridView gvExtenseCategory;

  private Button btnTienThu,btnNhapTienChi;
  // them moi
  private ListView lvDanhSachTienChi;
  private EditText edtNhapGhiChu,edtTienChi;
  MainActivity main;
  private GridView gvDanhMucChi;



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_chitieu, container, false);
    setControl(view);
    setEvent();
    // Kiểm tra null trước khi sử dụng requireContext()
//    if (getActivity() != null) {
//      icon_adapter = new AdapterDanhMuc(requireContext(), listChiTieu);
//    }

    return view;

  }

  private void setEvent() {
    //lien ket man hinh nhap thu ở đây
    btnTienThu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        MainActivity main = (MainActivity) getActivity();
        if(main!=null) {
          main.chuyenDenNhapThu();
        }
      }
    });
    main=(MainActivity) getActivity();
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
           //   String dayOfWeek = dayFormat.format(date); // Lấy thứ

              // Xuất ngày và thứ
              datepickerButton.setText(selectedDate+"");
            } catch (ParseException e) {
              e.printStackTrace();
            }
          }
        }, year, month, day);

        datePickerDialog.show();
      }
    });
    // Thêm sự kiện nhập tiền chi
    btnNhapTienChi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        MainActivity activity = (MainActivity) getActivity();
        CapNhatDuLieu listener = (CapNhatDuLieu) activity;

        if (listener != null) {
          try {
            // Thêm Giao dich moi
            listener.themGiaoDich(new ChiTieu(datepickerButton.getText().toString(),
                    "("+edtNhapGhiChu.getText().toString()+")",
                    Integer.parseInt(edtTienChi.getText().toString()),icon_adapter.getSelectedText()));
            Toast.makeText(requireContext(), "Đã nhập tiền chi!", Toast.LENGTH_SHORT).show();
            edtNhapGhiChu.setText("");
            edtTienChi.setText("");
            edtNhapGhiChu.requestFocus();
            icon_adapter.resetSelectedItem();
          } catch (Exception ex) {
            Toast.makeText(requireContext(), "Hãy nhập thông tin!", Toast.LENGTH_SHORT).show();

          }

        }
      }
    });
    gvDanhMucChi.setAdapter(icon_adapter);
  }
  private void setControl(View view) {

    btnTienThu=view.findViewById(R.id.btnTienThu);
    listChiTieu=new ArrayList<>();
    constructListView();
    icon_adapter=new AdapterDanhMuc(requireContext(),listChiTieu);
    gvDanhMucChi=view.findViewById(R.id.gvExtenseCategory);
    datepickerButton = view.findViewById(R.id.btnDatePicker);
    // them moi
     btnNhapTienChi=view.findViewById(R.id.btnNhapTienChi);
     edtNhapGhiChu=view.findViewById(R.id.edtNhapGhiChu);
     edtTienChi=view.findViewById(R.id.edtTienChi);

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
      datepickerButton.setText(selectedDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void constructListView() {
    listChiTieu.add(new DanhMuc("Y Tế",R.drawable.baseline_bloodtype_24));
    listChiTieu.add(new DanhMuc("Mua sắm",R.drawable.baseline_fastfood_25));
    listChiTieu.add(new DanhMuc("Điện",R.drawable.baseline_battery_charging_full_24));
    listChiTieu.add(new DanhMuc("Ăn chơi",R.drawable.baseline_fastfood_24));
    listChiTieu.add(new DanhMuc("Giáo dục",R.drawable.baseline_fastfood_24));
    listChiTieu.add(new DanhMuc("Tiền Nhà",R.drawable.baseline_fastfood_24));
    listChiTieu.add(new DanhMuc("Đi xe",R.drawable.baseline_fastfood_24));

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
