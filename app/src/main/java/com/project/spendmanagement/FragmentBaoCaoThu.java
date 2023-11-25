package com.project.spendmanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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


public class FragmentBaoCaoThu extends Fragment {
    //khai báo
    PieChart pcThu;
    ArrayList<PieEntry> phanTramDanhMucThu;
    PieData pieData;
    GiaoDich_Db giaoDichDb;
    AppCompatButton btnTienChi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_page_bao_cao_thu, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    //Phương thức lấy các phần tử
    private void setControl(View view) {
        int thang=11;
        int nam=2023;

        giaoDichDb=new GiaoDich_Db(requireContext());
        pcThu =view.findViewById(R.id.pcThu);
        //tạo dữ liệu cho biểu đồ
        phanTramDanhMucThu = giaoDichDb.LayPhanTramDanhMucThu(thang,nam);
        PieDataSet dataSet = new PieDataSet(phanTramDanhMucThu, "DANH MỤC THU");
        dataSet.setColors(new int[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED});

        pieData = new PieData(dataSet);
        pieData.setValueTextSize(16f);
        btnTienChi=view.findViewById(R.id.btnTienChi);

    }
    //Phương thức thực hiện các event
    private void setEvent() {
        pcThu.setData(pieData);

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

    }
    // Phương thức hiển thị thông tin khi click vào một phần của biểu đồ
    private void showValueAndLabelDialog(float value, String label) {
        // Bạn có thể sử dụng một cửa sổ dialog hoặc Toast để hiển thị thông tin
        String message = label + ": " + value + "%";
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }




}