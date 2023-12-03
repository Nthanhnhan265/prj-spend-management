package com.project.spendmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragmentPageLich extends Fragment  {
    private RecyclerView rcShow;
    private Button btnNgay;
    private TextView tvThu, tvChi,tvTong;
    public static AdapterLich adapterLich;
    MainActivity main;
    //them moi
    List<GiaoDich>data_gd=new ArrayList<>();
    List<ChiTieu>data_chitieu=new ArrayList<>();
    ArrayAdapter adapter;
    GiaoDich_Db giaoDichDb;

    AdapterGiaoDich adapterGiaoDich;
    int thang=Calendar.getInstance().get(Calendar.MONTH+1);
    int nam=Calendar.getInstance().get(Calendar.YEAR);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_lich, container, false);
        setControl(view);
        setEvent();

        return view;
    }

    private void setEvent() {
        try {
            int thang=Calendar.getInstance().get(Calendar.MONTH)+1;
            int nam=Calendar.getInstance().get(Calendar.YEAR);
            tinhTongThuChi(thang,nam);
            btnNgay.setText(thang+"/"+nam);
            btnNgay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });
            // Sử dụng getActivity() để lấy ngữ cảnh của hoạt động
            giaoDichDb = new GiaoDich_Db(this.requireContext());
            adapterLich = new AdapterLich(requireContext(), giaoDichDb.LayGiaoDichTrongThang(thang,nam));
            adapterGiaoDich=new AdapterGiaoDich(requireContext(),main.listGiaoDich);
            rcShow.setAdapter(adapterLich);

//            Toast.makeText(main, "Đã đọc dữ liệu thành công!", Toast.LENGTH_SHORT).show();
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
            adapterLich =new AdapterLich(requireContext(),main.listGiaoDich);
        }
        btnNgay = view.findViewById(R.id.btnDatePicker);

    }
    // Function tinh tổng Thu và Chi
    private void tinhTongThuChi(int thang, int nam) {
        GiaoDich_Db giaoDichDb=new GiaoDich_Db(requireContext());
        NumberFormat num=NumberFormat.getInstance();
        num.setGroupingUsed(true);
        double []tongThuChi= giaoDichDb.LayThuChiTrongThang(thang,nam);
        double thu=tongThuChi[0];
        double chi=tongThuChi[1];
        tvThu.setText(num.format(thu));
        tvChi.setText(num.format(chi));
        tvTong.setText(num.format(thu-chi));
    }



    //Hiện họp thoại để chọn tháng/năm
    private void showDatePickerDialog() {
        // Tạo đối tượng AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Chọn Tháng và Năm");

        // Thiết lập giao diện của hộp thoại
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        builder.setView(dialogView);

        final NumberPicker monthPicker = dialogView.findViewById(R.id.monthPicker);
        final NumberPicker yearPicker = dialogView.findViewById(R.id.yearPicker);

        // Thiết lập NumberPicker cho tháng và năm
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(Calendar.getInstance().get(Calendar.MONTH) + 1); // Tháng bắt đầu từ 0
        monthPicker.setWrapSelectorWheel(true);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearPicker.setMinValue(currentYear - 10);
        yearPicker.setMaxValue(currentYear + 10);
        yearPicker.setValue(currentYear);
        yearPicker.setWrapSelectorWheel(true);

        // Thiết lập nút ok
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút OK
                thang = monthPicker.getValue();
                nam = yearPicker.getValue();
                btnNgay.setText(thang+"/"+nam);
                //lấy dữ liệu và load lên danh sách
                main.listGiaoDich.clear();
                main.listGiaoDich.addAll( giaoDichDb.LayGiaoDichTrongThang(thang,nam));
                adapterLich=new AdapterLich(requireContext(),main.listGiaoDich);
                rcShow.setAdapter(adapterLich);
                //gán thu,chi
                NumberFormat num=NumberFormat.getInstance();
                num.setGroupingUsed(true);
                double []tongThuChi= giaoDichDb.LayThuChiTrongThang(thang,nam);
                double thu=tongThuChi[0];
                double chi=tongThuChi[1];
                tvThu.setText(num.format(thu));
                tvChi.setText(num.format(chi));
                tvTong.setText(num.format(thu-chi));

            }
        });

        // Tạo và hiển thị hộp thoại
        builder.create().show();
    }

}
