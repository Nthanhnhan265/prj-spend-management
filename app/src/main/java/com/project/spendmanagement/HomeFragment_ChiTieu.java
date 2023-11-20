package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
  GiaoDich_Db giaoDich_db;
AdapterGiaoDich adapterGiaoDich;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home_chitieu, container, false);
    setControl(view);
    setEvent();
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
//    btnNhapTienChi.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        try {
//          // Get the selected date from the button
//          String selectedDate = datepickerButton.getText().toString();
//
//          // Get other input values
//          String ghiChu = edtNhapGhiChu.getText().toString();
//          int tienThu = Integer.parseInt(edtTienChi.getText().toString());
//          DanhMuc selectedDanhMuc = icon_adapter.getDanhMucDuocChon();
//
//          // Create a new GiaoDich object based on the input values
//          ChiTieu chiTieu = new ChiTieu(selectedDate, "(" + ghiChu + ")", tienThu, selectedDanhMuc);
//
//          // Get the database instance
//          SQLiteDatabase db = giaoDich_db.getWritableDatabase();
//
//          // Insert the data into the database
//          long newRowId = giaoDich_db.ThemDl(chiTieu);
//
//          // Check if the insertion was successful
//          if (newRowId != -1) {
//            Toast.makeText(requireContext(), "Đã nhập tiền chi!", Toast.LENGTH_SHORT).show();
//            edtNhapGhiChu.setText("");
//            edtTienChi.setText("");
//            edtNhapGhiChu.requestFocus();
//            icon_adapter.resetSelectedItem();
//          } else {
//            Toast.makeText(requireContext(), "Lỗi khi thêm vào database!", Toast.LENGTH_SHORT).show();
//          }
//        } catch (Exception ex) {
//          Toast.makeText(requireContext(), "Hãy nhập thông tin!", Toast.LENGTH_SHORT).show();
//        }
//      }
//    });
//    btnNhapTienChi.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        MainActivity activity = (MainActivity) getActivity();
//        CapNhatDuLieu listener = (CapNhatDuLieu) activity;
//
//        if (listener != null) {
//          try {
//            // Thêm Giao dich moi
//            listener.themGiaoDich(new ChiTieu(datepickerButton.getText().toString(),
//                    "("+edtNhapGhiChu.getText().toString()+")",
//                    Integer.parseInt(edtTienChi.getText().toString()),icon_adapter.getDanhMucDuocChon()));
//
//            Toast.makeText(requireContext(), "Đã nhập tiền chi!", Toast.LENGTH_SHORT).show();
//            edtNhapGhiChu.setText("");
//            edtTienChi.setText("");
//            edtNhapGhiChu.requestFocus();
//            icon_adapter.resetSelectedItem();
//          } catch (Exception ex) {
//            Toast.makeText(requireContext(), "Hãy nhập thông tin!", Toast.LENGTH_SHORT).show();
//          }
//        }
//      }
//    });
    adapterGiaoDich=new AdapterGiaoDich(requireContext(),main.listGiaoDich);
    btnNhapTienChi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        /* 
        MainActivity activity = (MainActivity) getActivity();
        CapNhatDuLieu listener = (CapNhatDuLieu) activity;

        if (listener != null) {
          try {
            // Thêm Giao dich moi
            listener.themGiaoDich(new ChiTieu(datepickerButton.getText().toString(),
                    "("+edtNhapGhiChu.getText().toString()+")",
                    Integer.parseInt(edtTienChi.getText().toString()),icon_adapter.getDanhMucDuocChon()));
            Toast.makeText(requireContext(), "Đã nhập tiền chi!", Toast.LENGTH_SHORT).show();
            edtNhapGhiChu.setText("");
            edtTienChi.setText("");
            edtNhapGhiChu.requestFocus();
            icon_adapter.resetSelectedItem();
          } catch (Exception ex) {
            Toast.makeText(requireContext(), "Hãy nhập thông tin!", Toast.LENGTH_SHORT).show();
        */
        try {
          String selectedDate = datepickerButton.getText().toString();
          String ghiChu = edtNhapGhiChu.getText().toString();
          int tienThu = Integer.parseInt(edtTienChi.getText().toString());
          DanhMuc selectedDanhMuc = icon_adapter.getDanhMucDuocChon();

          ChiTieu chiTieu = new ChiTieu(selectedDate, "(" + ghiChu + ")", tienThu, selectedDanhMuc);

          // Thêm vào cơ sở dữ liệu
          long newRowId = giaoDich_db.ThemDl(chiTieu);

          if (newRowId != -1) {
            Toast.makeText(requireContext(), "Đã nhập tiền chi và lưu vào cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
            // Cập nhật giao diện hoặc thực hiện các bước khác nếu cần thiết
            main.listGiaoDich.clear();
            main.listGiaoDich.addAll(giaoDich_db.DocDl());
          adapterGiaoDich.notifyDataSetChanged();
          } else {
            Toast.makeText(requireContext(), "Lỗi khi thêm vào cơ sở dữ liệu!", Toast.LENGTH_SHORT).show();
          }

          // Cập nhật giao diện hoặc thực hiện các bước khác nếu cần thiết
          edtNhapGhiChu.setText("");
          edtTienChi.setText("");
          edtNhapGhiChu.requestFocus();
          icon_adapter.resetSelectedItem();
        } catch (Exception ex) {
          Toast.makeText(requireContext(), "Hãy nhập thông tin!", Toast.LENGTH_SHORT).show();
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
    giaoDich_db = new GiaoDich_Db(requireContext());

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
    listChiTieu.add(new DanhMuc(111,"Y Tế",R.drawable.baseline_bloodtype_24));
    listChiTieu.add(new DanhMuc(112,"Mua sắm",R.drawable.baseline_fastfood_25));
    listChiTieu.add(new DanhMuc(113,"Điện",R.drawable.baseline_battery_charging_full_24));
    listChiTieu.add(new DanhMuc(114,"Ăn chơi",R.drawable.baseline_fastfood_24));
    listChiTieu.add(new DanhMuc(115,"Giáo dục",R.drawable.baseline_fastfood_24));
    listChiTieu.add(new DanhMuc(116,"Tiền Nhà",R.drawable.baseline_fastfood_24));
    listChiTieu.add(new DanhMuc(117,"Đi xe",R.drawable.baseline_fastfood_24));
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
