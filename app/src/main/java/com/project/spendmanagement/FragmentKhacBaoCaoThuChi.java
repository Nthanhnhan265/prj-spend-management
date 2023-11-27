package com.project.spendmanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;


public class FragmentKhacBaoCaoThuChi extends Fragment {
    //khai báo
    PieChart pcThu;
    ArrayList<PieEntry> phanTramDanhMucThu;
    PieData pieData;
    GiaoDich_Db giaoDichDb;
    AppCompatButton btnTienChi;
    TextView tvChonThang;
    NumberPicker yearPicker ;
    NumberPicker monthPicker;
    PieDataSet dataSet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_page_khac_baocaothuchi, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    //Phương thức lấy các phần tử
    private void setControl(View view) {

        giaoDichDb=new GiaoDich_Db(requireContext());
        pcThu =view.findViewById(R.id.pcThu);
        //tạo dữ liệu cho biểu đồ
        phanTramDanhMucThu = giaoDichDb.LayPhanTramDanhMucThu(FragmentBaoCaoChi.thang,FragmentBaoCaoChi.nam);
        dataSet = new PieDataSet(phanTramDanhMucThu, "DANH MỤC THU");
        dataSet.setColors(new int[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED});

        pieData = new PieData(dataSet);
        pieData.setValueTextSize(16f);
        btnTienChi=view.findViewById(R.id.btnTienChi);
        tvChonThang=view.findViewById(R.id.tvChonThang);
        monthPicker=view.findViewById(R.id.monthPicker);
        yearPicker=view.findViewById(R.id.yearPicker);
    }
    //Phương thức thực hiện các event
    private void setEvent() {
        pcThu.setData(pieData);
        tvChonThang.setText(FragmentBaoCaoChi.thang+"/"+FragmentBaoCaoChi.nam);
        // Tùy chỉnh biểu đồ PieChart
        pcThu.getDescription().setEnabled(false);
        pcThu.setDrawHoleEnabled(true);
        pcThu.setHoleColor(Color.TRANSPARENT);
        pcThu.setTransparentCircleColor(Color.LTGRAY);
        pcThu.setTransparentCircleAlpha(50);
        pcThu.setHoleRadius(40f);
        pcThu.setTransparentCircleRadius(40f);
        pcThu.setTouchEnabled(true);
        pcThu.setDrawEntryLabels(false);

        // Tắt hiển thị giá trị trên biểu đồ
        pcThu.getData().setDrawValues(true);
        pcThu.getLegend().setEnabled(true);
        pcThu.getLegend().setTextSize(15f);
        // Tạo đối tượng Legend
        Legend legend = pcThu.getLegend();
        // Thiết lập chiều dọc cho chú thích
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        // Đặt vị trí của chú thích (có thể là LEFT, RIGHT, TOP, BOTTOM, LEFT_OF_CHART, RIGHT_OF_CHART, ABOVE_CHART, BELOW_CHART)
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setDrawInside(false); // Đặt giá trị này để chú thích không bị che phủ bởi biểu đồ tròn
        legend.setTextColor(Color.parseColor("#BDD8F1")); // Đặt màu cho chú thích



        //ấn để xem chi tiết biểu đồ
        pcThu.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e instanceof PieEntry) {
                    PieEntry entry = (PieEntry) e;
                    String tenDanhMuc = entry.getLabel();
                    String maDanhMuc = entry.getData().toString();

                    // Hiển thị chi tiết giao dịch với danh mục
                    FragmentBaoCaoChiTiet fragmentBaoCaoChiTiet =new FragmentBaoCaoChiTiet(maDanhMuc,tenDanhMuc);
                    FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container,fragmentBaoCaoChiTiet);
                    transaction.addToBackStack(null).commit();


                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //đặt sự kiện nút mở biểu đồ chi
        btnTienChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentBaoCaoChi fragmentBaoCaoThu=new FragmentBaoCaoChi() ;
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentBaoCaoThu);
                transaction.commit();

            }
        });
            //ấn để chọn lịch
        tvChonThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();


            }
        });
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
                FragmentBaoCaoChi.thang = monthPicker.getValue();
                FragmentBaoCaoChi.nam = yearPicker.getValue();
                int thang=FragmentBaoCaoChi.thang;
                int nam=FragmentBaoCaoChi.nam;
                tvChonThang.setText(thang+"/"+nam);
                //Sau khi chọn tháng thì truy vấn danh sách mới để hiện lên sơ đồ
                ArrayList<PieEntry> phanTramDanhMuc=giaoDichDb.LayPhanTramDanhMucThu(thang,nam);
                dataSet.setValues(phanTramDanhMuc);
                //load lại biểu đồ
                pcThu.notifyDataSetChanged();
                pcThu.invalidate();
            }
        });

        // Tạo và hiển thị hộp thoại
        builder.create().show();
    }





}