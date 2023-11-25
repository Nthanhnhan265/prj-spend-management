package com.project.spendmanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;


public class FragmentBaoCaoChi extends Fragment {
    //khai báo
    PieChart pcChiTieu;
    ArrayList<PieEntry>phanTramDanhMucChi;
    PieData pieData;
    GiaoDich_Db giaoDichDb;
    AppCompatButton btnTienThu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_page_bao_cao_chi, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    //Phương thức lấy các phần tử
    private void setControl(View view) {
        int thang=11;
        int nam=2023;

        giaoDichDb=new GiaoDich_Db(requireContext());
        pcChiTieu=view.findViewById(R.id.pcChiTieu);
        //tạo dữ liệu cho biểu đồ
        phanTramDanhMucChi= giaoDichDb.LayPhanTramDanhMucChi(thang,nam);
        PieDataSet dataSet = new PieDataSet(phanTramDanhMucChi, "Danh mục chi");
        dataSet.setColors(new int[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED});

        pieData = new PieData(dataSet);
        pieData.setValueTextSize(15f);
        btnTienThu=view.findViewById(R.id.btnTienThu);

    }
    //Phương thức thực hiện các event
    private void setEvent() {
        pcChiTieu.setData(pieData);

        // Tùy chỉnh biểu đồ PieChart
        pcChiTieu.getDescription().setEnabled(false);
        pcChiTieu.setDrawHoleEnabled(true);
        pcChiTieu.setHoleColor(Color.TRANSPARENT);
        pcChiTieu.setTransparentCircleColor(Color.LTGRAY);
        pcChiTieu.setTransparentCircleAlpha(50);
        pcChiTieu.setHoleRadius(40f);
        pcChiTieu.setTransparentCircleRadius(40f);
        pcChiTieu.setTouchEnabled(true);
        pcChiTieu.setDrawEntryLabels(false);

        // Tắt hiển thị giá trị trên biểu đồ
        pcChiTieu.getData().setDrawValues(true);

        pcChiTieu.getLegend().setEnabled(true);
        pcChiTieu.getLegend().setTextSize(15f);
        // Tạo đối tượng Legend
        Legend legend = pcChiTieu.getLegend();
        // Thiết lập chiều dọc cho chú thích
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        // Đặt vị trí của chú thích (có thể là LEFT, RIGHT, TOP, BOTTOM, LEFT_OF_CHART, RIGHT_OF_CHART, ABOVE_CHART, BELOW_CHART)
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setDrawInside(false); // Đặt giá trị này để chú thích không bị che phủ bởi biểu đồ tròn
        legend.setTextColor(Color.parseColor("#BDD8F1")); // Đặt màu cho chú thích



        //ấn để xem chi tiết biểu đồ
        pcChiTieu.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Được gọi khi người dùng chọn một phần của biểu đồ
                if (e == null) return;

                float value = e.getY();
                Object data = e.getData();
                String label = (data != null) ? data.toString() : "";

                // Hiển thị thông tin trong một cửa sổ hoặc AlertDialog
                showValueAndLabelDialog(value, label);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //ấn để xem báo cáo thu:
        btnTienThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentBaoCaoThu fragmentBaoCaoThu=new FragmentBaoCaoThu() ;
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,fragmentBaoCaoThu);
                transaction.commit();

            }
        });

    }
    // Phương thức hiển thị thông tin khi click vào một phần của biểu đồ
    private void showValueAndLabelDialog(float value, String label) {
        // Bạn có thể sử dụng một cửa sổ dialog hoặc Toast để hiển thị thông tin
        String message = label + ": " + value + "%";
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }




}