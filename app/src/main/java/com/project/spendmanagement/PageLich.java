package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PageLich extends Fragment  {
    private RecyclerView rcShow;
    private Button datepickerButton;
    private TextView tvThu, tvChi,tvTong;
    public static AdapterLich dateAdapter;


    MainActivity main;
    //them moi
    List<GiaoDich>data_gd=new ArrayList<>();
    List<ChiTieu>data_chitieu=new ArrayList<>();
    ArrayAdapter adapter;
    GiaoDich_Db giaoDichDb;
    Button btnDocDl;
    int tongThu,tongChi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_lich, container, false);
        setControl(view);
        setEvent();

        return view;
    }

    private void setEvent() {
        try {
            tvThu.setText(tinhTongThu() + "d");
            tvChi.setText(tinhTongChi() + "d");
            tinhTong();
            datepickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCustomDatePickerDialog();
                }
            });
            // Sử dụng getActivity() để lấy ngữ cảnh của hoạt động
            giaoDichDb = new GiaoDich_Db(requireContext(), "dbGiaoDich", null, 3);
            dateAdapter = new AdapterLich(requireContext(), main.listGiaoDich);

           rcShow.setAdapter(dateAdapter);
            main.listGiaoDich.addAll(giaoDichDb.DocDl());
            Toast.makeText(main, "Đã đọc dữ liệu thành công!", Toast.LENGTH_SHORT).show();
//            btnDocDl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        // Đọc dữ liệu từ cơ sở dữ liệu
//                      // main.listGiaoDich.clear();
//                        main.listGiaoDich.addAll(giaoDichDb.DocDl());
//
//                        Toast.makeText(main, "So PT :"+main.listGiaoDich.get(0).getGhiChu(), Toast.LENGTH_SHORT).show();
//                        dateAdapter.updateData(main.listGiaoDich);
//                        rcShow.setAdapter(dateAdapter);
//                        dateAdapter.notifyDataSetChanged();
//                        // Tính lại tổng thu chi và cập nhật TextView
//                        tinhTong();
//                        Log.d("Database", "Đã đọc dữ liệu thành công! Số lượng giao dịch: " + main.listGiaoDich.size());
//                        Log.d("Adapter", "Đã cập nhật Adapter. Số lượng item: " + dateAdapter.getItemCount());
//
//                        Toast.makeText(main, "Đã đọc dữ liệu thành công!", Toast.LENGTH_SHORT).show();
//                    } catch (Exception ex) {
//                        Toast.makeText(main, "Lỗi: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });


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
                                datepickerButton.setText(selectedDate + "(" + dayOfWeek + ")");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, year, month, day);

                    datePickerDialog.show();
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(main, "Loi"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl(View view) {
        main=(MainActivity) getActivity() ;
        rcShow=view.findViewById(R.id.rcShow);
        tvThu=view.findViewById(R.id.tvTienThu);
        tvChi=view.findViewById(R.id.tvTienChi);
        tvTong=view.findViewById(R.id.tvTong);
        if(main.listGiaoDich.size()!=0) {
            dateAdapter=new AdapterLich(requireContext(),main.listGiaoDich);
        }

        datepickerButton = view.findViewById(R.id.btnDatePicker);
        btnDocDl=view.findViewById(R.id.btnDocDl);
    }
    // Function tinh tổng Thu và Chi
    private void tinhTong() {
        tongThu = tinhTongThu();
        tongChi = tinhTongChi();

        tvThu.setText(tongThu + "");
        tvChi.setText(tongChi + "");

        int tong = tongThu - tongChi;
        tvTong.setText(tong + "");
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
    private int tinhTongChi(){
        int tong=0;
        for(GiaoDich gd: main.listGiaoDich) {
            if(gd instanceof ChiTieu) {
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
