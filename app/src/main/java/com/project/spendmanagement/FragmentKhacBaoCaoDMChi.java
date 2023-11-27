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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FragmentKhacBaoCaoDMChi extends Fragment {
    //khai báo
    PieChart pcChiTieu;
    ArrayList<PieEntry>phanTramDanhMucChi;
    PieData pieData;
    GiaoDich_Db giaoDichDb;
    AppCompatButton btnTienThu;
    TextView tvChonThang;
    NumberPicker yearPicker ;
    NumberPicker monthPicker;
    static int nam=Calendar.getInstance().get(Calendar.YEAR);
    PieDataSet dataSet;
    TextView tvChiTieu;
    TextView tvThuNhap;
    TextView tvThuChi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_page_khac_baocaodanhmuc_chi, container, false);
        setControl(view);
        setEvent();
        return view;
    }
    //Phương thức lấy các phần tử
    private void setControl(View view) {
        giaoDichDb=new GiaoDich_Db(requireContext());
        pcChiTieu=view.findViewById(R.id.pcChiTieu);
        //tạo dữ liệu cho biểu đồ
        phanTramDanhMucChi= giaoDichDb.LayPhanTramDanhMucChiNam(nam);
        dataSet = new PieDataSet(phanTramDanhMucChi, "Danh mục chi");
        dataSet.setColors(new int[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED});

        pieData = new PieData(dataSet);
        pieData.setValueTextSize(15f);
        btnTienThu=view.findViewById(R.id.btnTienThu);
        tvChonThang=view.findViewById(R.id.tvChonThang);
        monthPicker=view.findViewById(R.id.monthPicker);
        yearPicker=view.findViewById(R.id.yearPicker);
        tvChiTieu=view.findViewById(R.id.tvChiTieu);
        tvThuNhap=view.findViewById(R.id.tvThuNhap);
        tvThuChi=view.findViewById(R.id.tvThuChi);

    }
    //Phương thức thực hiện các event
    private void setEvent() {
        pcChiTieu.setData(pieData);
        tvChonThang.setText(""+nam);
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
               if(e instanceof PieEntry ) {
                    String tenDanhMuc=((PieEntry) e).getLabel();
                    String maDanhMuc=e.getData().toString();
                   // Hiển thị chi tiết giao dịch với danh mục
                   FragmentKhacBaoCaoChiTiet fragmentBaoCaoChiTiet =new FragmentKhacBaoCaoChiTiet(maDanhMuc,tenDanhMuc);
                   FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
                   transaction.replace(R.id.container,fragmentBaoCaoChiTiet);
                   transaction.addToBackStack(null).commit();
               }


            }

            @Override
            public void onNothingSelected() {

            }
        });
        //ấn để xem báo cáo thu:
        btnTienThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentKhacBaoCaoDMThu fragmentBaoCaoThu=new FragmentKhacBaoCaoDMThu() ;
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

        //hiển thị thu, chi và thu chi
        double[]thuChi= giaoDichDb.LayThuChiTrongNam(nam);
        NumberFormat num=NumberFormat.getInstance();
        num.setGroupingUsed(true);
        tvThuNhap.setText(num.format(thuChi[0]));
        tvChiTieu.setText(num.format(thuChi[1]));
        tvThuChi.setText(num.format(thuChi[0]-thuChi[1]));
    }
    //Hiện họp thoại để chọn tháng/năm
    private void showDatePickerDialog() {
        // Tạo đối tượng AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Chọn Năm");

        // Thiết lập giao diện của hộp thoại
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        builder.setView(dialogView);

        final NumberPicker monthPicker = dialogView.findViewById(R.id.monthPicker);
        final NumberPicker yearPicker = dialogView.findViewById(R.id.yearPicker);

        // Thiết lập NumberPicker cho năm
        monthPicker.setVisibility(View.GONE);
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
                nam = yearPicker.getValue();
                tvChonThang.setText(""+nam);
                //Sau khi chọn tháng thì truy vấn danh sách mới để hiện lên sơ đồ
                ArrayList<PieEntry> phanTramDanhMuc=giaoDichDb.LayPhanTramDanhMucChiNam(nam);
                dataSet.setValues(phanTramDanhMuc);
                //load lại biểu đồ
                pcChiTieu.notifyDataSetChanged();
                pcChiTieu.invalidate();

                //load lại thu và chi
                double[]thuChi= giaoDichDb.LayThuChiTrongNam(nam);
                NumberFormat num=NumberFormat.getInstance();
                num.setGroupingUsed(true);
                tvThuNhap.setText(num.format(thuChi[0]));
                tvChiTieu.setText(num.format(thuChi[1]));
                tvThuChi.setText(num.format(thuChi[0]-thuChi[1]));
            }
        });

        // Tạo và hiển thị hộp thoại
        builder.create().show();
    }

}