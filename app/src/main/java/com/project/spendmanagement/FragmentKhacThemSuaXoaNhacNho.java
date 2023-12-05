package com.project.spendmanagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.data.PieEntry;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class FragmentKhacThemSuaXoaNhacNho extends Fragment {
    //khai báo
    TextView tvTieuDe;
    String tieuDe;
    TextView tvXoa;
    Boolean hienThiXoa = false;
    ImageView ivThoat;
    private AppCompatButton btnLuu; 
    private Button datepickerButton;
    private Button btnGio;
    private NhacNho nhacNhoDuocChon = null;
    EditText edtTieuDe;
    EditText edtNoiDung;
    //constructors
    FragmentKhacThemSuaXoaNhacNho(String tieuDe) {
        if (tieuDe == "Thêm") {
            this.tieuDe = "Thêm nhắc nhở";
        } else if (tieuDe == "Chỉnh sửa") {
            this.tieuDe = "Chỉnh sửa nhắc nhở";
            hienThiXoa = true;
        }
    }

    //truyền nhắc nhở để chỉnh sửa
    FragmentKhacThemSuaXoaNhacNho(String tieuDe, NhacNho nhacNho) {
        if (tieuDe.equals("Thêm")) {
            this.tieuDe = "Thêm nhắc nhở";
        } else if (tieuDe.equals("Chỉnh sửa")) {
            this.tieuDe = "Chỉnh sửa nhắc nhở";
            hienThiXoa = true;
            nhacNhoDuocChon = nhacNho;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_nhacnho, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setControl(View view) {
        tvTieuDe = view.findViewById(R.id.tvTieuDe);
        tvXoa = view.findViewById(R.id.tvXoa);
        datepickerButton = view.findViewById(R.id.btnDatePicker);
        btnGio = view.findViewById(R.id.btnGio);
        btnLuu=view.findViewById(R.id.btnLuu);
        edtTieuDe=view.findViewById(R.id.edtTieuDe);
        edtNoiDung=view.findViewById(R.id.edtNoiDung);
        ivThoat=view.findViewById(R.id.ivThoat);
    }

    private void setEvent() {
        try {
            //Hiển thị thời gian hiện tại
            datepickerButton.setText(
                    Calendar.getInstance().get(
                            (Calendar.DAY_OF_MONTH))+
                         "/"+(Calendar.getInstance().get(Calendar.MONTH)+1)+
                         "/"+Calendar.getInstance().get(Calendar.YEAR)
            );
            btnGio.setText(
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE)
            );

            tvTieuDe.setText(tieuDe);
            if (hienThiXoa != true) {
                tvXoa.setVisibility(View.GONE);
            }else {
                String[]thoiGian=nhacNhoDuocChon.getThoiGian().toString().split(" ");
                datepickerButton.setText(thoiGian[0]);
                btnGio.setText(thoiGian[1]);
                edtTieuDe.setText(nhacNhoDuocChon.getTieuDe());
                edtNoiDung.setText(nhacNhoDuocChon.getNoiDung());
            }
            //ấn để chọn ngày
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
                                datepickerButton.setText(selectedDate + "");
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, year, month, day);

                    datePickerDialog.show();
                }
            });
            //ấn để chọn giờ
            btnGio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePicker();
                }
            });
            //ấn để thoát


            //TODO: thêm nhắc nhở sau đó thêm thông báo
            btnLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GiaoDich_Db giaoDichDb = new GiaoDich_Db(requireContext());
                    //Sửa
                    long hienTai = 0;
                    hienTai = GiaoDich_Db.getMilis(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +
                            "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) +
                            "/" + Calendar.getInstance().get(Calendar.YEAR) + " " + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE));
                    long nhacNho = GiaoDich_Db.getMilis(datepickerButton.getText() + " " + btnGio.getText());
                    long hieu=nhacNho-hienTai;
                    if (nhacNhoDuocChon != null) {
                        if (hienTai <= nhacNho) {
                            if (
                                    datepickerButton.getText() != ""
                                            && btnGio.getText() != ""
                                            && !edtTieuDe.getText().toString().equals("")
                                            && !edtNoiDung.getText().toString().equals("")
                            ) {
                                //sửa nhắc nhở được chon theo nội dung mới
                                nhacNhoDuocChon.setThoiGian(datepickerButton.getText() + " " + btnGio.getText());
                                nhacNhoDuocChon.setNoiDung(edtNoiDung.getText().toString());
                                nhacNhoDuocChon.setTieuDe(edtTieuDe.getText().toString());
                                //sửa vào DB
                                if (giaoDichDb.SuaNhacNho(nhacNhoDuocChon)) {
                                    //sửa thông báo
                                    ThongBaoNhacNho.ThongBao(
                                            requireContext(),
                                            hieu,
                                            nhacNhoDuocChon.getTieuDe(),
                                            nhacNhoDuocChon.getMaNhacNho(),
                                            nhacNhoDuocChon
                                    );
                                    requireActivity().getSupportFragmentManager().popBackStack();
                                    Toast.makeText(requireContext(), "Sửa thành công!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(requireContext(), "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(requireContext(), "Vui lòng chọn thời gian lớn hơn hiện tại", Toast.LENGTH_SHORT).show();
                        }


                    }
                    //Thêm
                    else {
                        if (hienTai<=nhacNho) {
                            if (
                                    datepickerButton.getText() != ""
                                            && btnGio.getText() != ""
                                            && tvTieuDe.getText() != ""
                                            && !edtTieuDe.getText().toString().equals("")
                                            && !edtNoiDung.getText().toString().equals("")
                            ) {
                                NhacNho nhacNhoMoi = new NhacNho(
                                        0,
                                        edtTieuDe.getText().toString(),
                                        datepickerButton.getText() + " " + btnGio.getText(),
                                        edtNoiDung.getText().toString());
                                long dongID = giaoDichDb.ThemNhacNho(nhacNhoMoi);
                                    Log.e("ID nhac nho dc chon: ",dongID+" , "+nhacNhoMoi);
                                if (dongID != -1) {
                                    //lấy milis của thời gian
                                    long miliseconds = nhacNhoMoi.getMilisecond();
                                    //gọi phương thức trong lớp ThongBaoNhacNho
                                    ThongBaoNhacNho.ThongBao(
                                            requireContext(),
                                            hieu,
                                            nhacNhoMoi.getTieuDe(),
                                            dongID,nhacNhoMoi);

                                    requireActivity().getSupportFragmentManager().popBackStack();
                                    Toast.makeText(requireContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(requireContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(requireContext(), "vui lòng chọn thời gian lớn hơn hiện tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            //TODO: xóa nhắc nhở
            tvXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GiaoDich_Db giaoDich_db=new GiaoDich_Db(requireContext());
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Xác nhận xóa")
                            .setMessage("Bạn có chắc chắn muốn xóa?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        //Gọi phương thức xóa nhắc nhở khỏi DB
                                        if(giaoDich_db.XoaNhacNho(nhacNhoDuocChon.getMaNhacNho())) {
                                            //hủy thông báo
                                            ThongBaoNhacNho.HuyThongBao(
                                                    requireContext(),
                                                    nhacNhoDuocChon.getMaNhacNho()
                                            );

                                            requireActivity().getSupportFragmentManager().popBackStack();
                                            Toast.makeText(requireContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(requireContext(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                                        }


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

            //ấn để thoát
            ivThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });

        } catch (Exception ex) {

            Log.e("setEvent: ", ex.getMessage());
        }

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


    //Hiện họp thoại để chọn gio:phut
    private void showTimePicker() {
        // Tạo đối tượng AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Chọn Giờ");

        // Thiết lập giao diện của hộp thoại
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        builder.setView(dialogView);

        final NumberPicker hourPicker = dialogView.findViewById(R.id.monthPicker);
        final NumberPicker minutePicker = dialogView.findViewById(R.id.yearPicker);

        // Thiết lập NumberPicker cho giờ
        hourPicker.setVisibility(View.VISIBLE);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(24);
        hourPicker.setValue(currentHour);
        hourPicker.setWrapSelectorWheel(true);

        // Thiết lập NumberPicker cho phút
        minutePicker.setVisibility(View.VISIBLE);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(60);
        minutePicker.setValue(currentMinute);
        minutePicker.setWrapSelectorWheel(true);

        // Thiết lập nút ok
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút OK
                int gio = hourPicker.getValue();
                int phut = minutePicker.getValue();
                btnGio.setText(gio + ":" + phut);
            }
        });

        // Tạo và hiển thị hộp thoại
        builder.create().show();
    }
}
