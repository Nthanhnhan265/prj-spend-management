package com.project.spendmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentChinhSuaChi extends Fragment {

    private Button btnDatePicker;
    private GridView gvDanhMucThu;
    private List<DanhMuc> listDanhMuc;
    private AdapterDanhMuc iconDanhMucAdapter;
    private Button btnChinhSuaThu;
    private EditText edtNhapGhiChu, edtTienThu;
    private TextView tvXoa;

    MainActivity main;
    GiaoDich_Db giaoDich_db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_chitieu, container, false);
        setControl(view);
        setEvent();

        return view;
    }

    private void setEvent() {
        try {
            main = (MainActivity) getActivity();
            setCurrentDate();
            GiaoDich gdDuocChon=GiaoDich.LayGiaoDichQuaMa(main.listGiaoDich,AdapterGiaoDich.maDuocChon);
            edtNhapGhiChu.setText(gdDuocChon.getGhiChu());
            edtTienThu.setText(gdDuocChon.getGiaTri());
            btnDatePicker.setText(gdDuocChon.getNgayGD());
            gvDanhMucThu.setSelection(listDanhMuc.indexOf(gdDuocChon.getDanhMuc()));
            gvDanhMucThu.getSelectedItem();
            btnChinhSuaThu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getActivity();
                    CapNhatDuLieu listener = (CapNhatDuLieu) activity;
                    boolean flag = false;
                    if (listener != null) {
                        try {
                            for (GiaoDich gd : main.listGiaoDich) {
                                if (gd.getMaGD() == AdapterGiaoDich.maDuocChon) {
                                    // Kiểm tra và cập nhật thông tin giao dịch
                                    if (!gd.getNgayGD().equals(btnDatePicker.getText().toString())) {
                                        gd.setNgayGD(btnDatePicker.getText().toString());
                                        flag = true;
                                    }
                                    if (!gd.getGhiChu().equals(edtNhapGhiChu.getText().toString())) {
                                        gd.setGhiChu(edtNhapGhiChu.getText().toString());
                                        flag = true;
                                    }
                                    if(gd.getGiaTri()!=edtTienThu.getText().toString()) {
                                        gd.setGiaTri(Integer.parseInt(edtTienThu.getText().toString()));
                                        flag=true;
                                    }
//                                    if(gd.getDanhMuc().getTenDanhMuc()!=iconDanhMucAdapter.getDanhMucDuocChon().getTenDanhMuc()) {
//                                        gd.getDanhMuc().setTenDanhMuc(iconDanhMucAdapter.getDanhMucDuocChon().getTenDanhMuc());
//                                        gd.getDanhMuc().setIcon(iconDanhMucAdapter.getDanhMucDuocChon().getIcon());
//                                        flag=true;
//                                    }
                                    DanhMuc danhMucDuocChon = iconDanhMucAdapter.getDanhMucDuocChon();
                                    if (!gdDuocChon.getDanhMuc().getTenDanhMuc().equals(danhMucDuocChon.getTenDanhMuc())) {
                                        gdDuocChon.getDanhMuc().setTenDanhMuc(danhMucDuocChon.getTenDanhMuc());
                                        gdDuocChon.getDanhMuc().setIcon(danhMucDuocChon.getIcon());
                                    }
                                    // Gọi phương thức SuaDl của GiaoDich_Db để cập nhật vào cơ sở dữ liệu
                                    giaoDich_db.SuaDl(gdDuocChon);

                                    //   giaoDich_db.SuaDl(gd);
                                }
                            }

                            if (flag) {
                                Toast.makeText(requireContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(requireContext(), "Vui lòng nhập thông tin!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


            gvDanhMucThu.setAdapter(iconDanhMucAdapter);

            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCustomDatePickerDialog();
                }

            });
            btnDatePicker.setOnClickListener(new View.OnClickListener() {
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
                            btnDatePicker.setText(selectedDate);
                        }
                    }, year, month, day);

                    datePickerDialog.show();
                }
            });
            tvXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc chắn muốn xóa?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        GiaoDich gdDuocChon = GiaoDich.LayGiaoDichQuaMa(main.listGiaoDich, AdapterGiaoDich.maDuocChon);

                                        // Gọi phương thức XoaDL của GiaoDich_Db để xóa khỏi cơ sở dữ liệu
                                        giaoDich_db.XoaDL(gdDuocChon);

                                        main.listGiaoDich.remove(gdDuocChon);
                                        Toast.makeText(requireContext(), "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
                                        main.chuyenDenLich();
                                    } catch (Exception ex) {
                                        Toast.makeText(requireContext(), "Có lỗi xảy ra khi xóa dữ liệu!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(requireContext(), "Hủy", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            });
        }
        catch (Exception ex) {
            Toast.makeText(requireContext(),"Có lỗi xảy ra",Toast.LENGTH_SHORT).show();
            throw (ex);
        }
    }

    private void setControl(View view) {
        btnChinhSuaThu =view.findViewById(R.id.btnChinhSuaChi);
        btnDatePicker = view.findViewById(R.id.btnDatePickerChi);
        gvDanhMucThu =view.findViewById(R.id.gvExtenseCategory);
        listDanhMuc =new ArrayList<>();
        constructGridView();
        iconDanhMucAdapter =new AdapterDanhMuc(requireContext(), listDanhMuc);
        edtTienThu=view.findViewById(R.id.edtTienChiSuaChi);
        edtNhapGhiChu=view.findViewById(R.id.edtNhapGhiChuSuaChi);
        tvXoa=view.findViewById(R.id.btnXoaChi);
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
            btnDatePicker.setText(selectedDate );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void constructGridView() {
//        listDanhMuc.add(new DanhMuc("Trợ cấp",R.drawable.ic_money));
//        listDanhMuc.add(new DanhMuc("Đầu tư",R.drawable.baseline_fastfood_25));
//        listDanhMuc.add(new DanhMuc("Lãnh lương",R.drawable.baseline_fastfood_25));
//        listDanhMuc.add(new DanhMuc("Lãnh lương",R.drawable.baseline_fastfood_25));
//        listDanhMuc.add(new DanhMuc("Lãnh lương",R.drawable.baseline_fastfood_25));
//        listDanhMuc.add(new DanhMuc("Lãnh lương",R.drawable.baseline_fastfood_25));

//        listDanhMuc.add(new DanhMuc(123,"Y Tế",R.drawable.baseline_bloodtype_24));
//        listDanhMuc.add(new DanhMuc(123,"Mua sắm",R.drawable.baseline_fastfood_25));
//        listDanhMuc.add(new DanhMuc(124,"Điện",R.drawable.baseline_battery_charging_full_24));
//        listDanhMuc.add(new DanhMuc(125,"Ăn chơi",R.drawable.baseline_fastfood_24));
//        listDanhMuc.add(new DanhMuc(126,"Giáo dục",R.drawable.baseline_fastfood_24));
//        listDanhMuc.add(new DanhMuc(127,"Tiền Nhà",R.drawable.baseline_fastfood_24));
//        listDanhMuc.add(new DanhMuc(128,"Đi xe",R.drawable.baseline_fastfood_24));



    }
}



