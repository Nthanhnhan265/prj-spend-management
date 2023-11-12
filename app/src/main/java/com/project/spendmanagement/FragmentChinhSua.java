package com.project.spendmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentChinhSua extends Fragment implements AdapterGiaoDich.OnItemClickListener{
    private AdapterDanhMuc icon_adapter;
    private List<DanhMuc> listChiTieu;
    private Button datepickerButton;
    // private GridView gvExtenseCategory;
    private Button btnTienThu, btnNhapTienChi;
    // them moi
    private Button btnChinhSua,btnXoa;
    private ListView lvDanhSachTienChi;
    private EditText edtNhapGhiChu, edtTienChi;
    MainActivity main;
    AdapterGiaoDich adapterGiaoDich;
    AdapterLich adapterLich;
    private GridView gvDanhMucChi;
    private int index=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_extense, container, false);
        // Toast.makeText(requireContext()"", Toast.LENGTH_SHORT).show();
       // adapterGiaoDich = new AdapterGiaoDich(requireContext(), main.listgiaodich);
       // adapterGiaoDich.setOnItemClickListener(this::onItemClick);

        setControl(view);
        setEvent();
        return view;
    }
    private void setControl(View view) {

        btnTienThu = view.findViewById(R.id.btnTienThu);
        listChiTieu = new ArrayList<>();
        icon_adapter = new AdapterDanhMuc(requireContext(), listChiTieu);
        gvDanhMucChi = view.findViewById(R.id.gvExtenseCategory);
        datepickerButton = view.findViewById(R.id.btnDatePickerSua);
        // them moi
        constructListView();
        btnNhapTienChi = view.findViewById(R.id.btnNhapTienChi);
        edtNhapGhiChu = view.findViewById(R.id.edtNhapGhiChuSua);
        edtTienChi = view.findViewById(R.id.edtNhapGhiChuSua);
        btnChinhSua=view.findViewById(R.id.btnChinhSua);
        btnXoa=view.findViewById(R.id.btnXoa);
    }
    private void setEvent() {
        setCurrentDate();
        datepickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDatePickerDialog();
            }

        });
        main=(MainActivity) getActivity();
        btnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChiTieu chiTieu = new ChiTieu();
                chiTieu.setNgayGD(datepickerButton.getText().toString());
                chiTieu.setGhiChu(edtNhapGhiChu.getText().toString());
                chiTieu.setGiaTri(Integer.parseInt(edtTienChi.getText().toString()));

                // Cập nhật mục đã chọn trong danh sách chính
                main.listgiaodich.set(index, chiTieu);
                // Thông báo cho AdapterLich cập nhật RecyclerView
                PageLich.capNhatList();

                Toast.makeText(requireContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
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
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index == -1) {
                    Toast.makeText(requireContext(), "Chưa chọn giao dịch để xóa", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Remove the selected transaction from the list of transactions
                main.listgiaodich.remove(index);

                // Notify the AdapterLich to update the RecyclerView
                PageLich.capNhatList();

                Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

                // Clear the selection index
                index = -1;
            }
        });

        gvDanhMucChi.setAdapter(icon_adapter);
    }
    private void constructListView() {
        listChiTieu.add(new DanhMuc("Mua sắm",R.drawable.baseline_fastfood_25));
        listChiTieu.add(new DanhMuc("Y Tế",R.drawable.baseline_bloodtype_24));
        listChiTieu.add(new DanhMuc("Điện",R.drawable.baseline_battery_charging_full_24));
        listChiTieu.add(new DanhMuc("Ăn chơi",R.drawable.baseline_fastfood_24));
        listChiTieu.add(new DanhMuc("Giáo dục",R.drawable.baseline_fastfood_24));
        listChiTieu.add(new DanhMuc("Tiền Nhà",R.drawable.baseline_fastfood_24));
        listChiTieu.add(new DanhMuc("Đi xe",R.drawable.baseline_fastfood_24));

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

    @Override
    public void onItemClick(int position) {
        // Lấy mục đã chọn từ adapter
        GiaoDich selectedGiaoDich = adapterGiaoDich.getItemAtPosition(position);
        index = position;

        // Điền dữ liệu vào các yếu tố UI trong FragmentChinhSua từ dữ liệu của mục đã chọn
        datepickerButton.setText(selectedGiaoDich.getNgayGD());
        edtNhapGhiChu.setText(selectedGiaoDich.getGhiChu());
        edtTienChi.setText(Integer.parseInt(selectedGiaoDich.getTextOfValue()));

    }
}


