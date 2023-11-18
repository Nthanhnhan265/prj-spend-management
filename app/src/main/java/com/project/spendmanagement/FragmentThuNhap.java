    package com.project.spendmanagement;

    import android.app.DatePickerDialog;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.GridView;
    import android.widget.ImageView;
    import android.widget.Toast;

    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.List;

    public class FragmentThuNhap extends Fragment {
        private Button btnDatePicker;
        private GridView gvDanhMucThu;
        private List<DanhMuc> listThuNhap;
        private AdapterDanhMuc iconDanhMucAdapter;
        private Button btnTienChi;
        private Button btnThu ;
        private EditText edtNhapGhiChu, edtTienThu;
        private ImageView ivNgayTruoc, ivNgaySau;
        MainActivity main;
        // them moi
        GiaoDich_Db giaoDich_db;
//        private AdapterLich dateAdapter;
//        private RecyclerView rcShow;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home_thunhap, container, false);
            Toast.makeText(requireContext(), "Page Thu", Toast.LENGTH_SHORT).show();
            setControl(view);
            setEvent();

            return view;
        }
        private void setEvent() {

            ivNgayTruoc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String dateString = btnDatePicker.getText().toString();
                    dateString=dateString.substring(0,dateString.indexOf(' '));
                    Date inputDate = new Date(dateString);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(inputDate);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    inputDate=calendar.getTime();
                    btnDatePicker.setText(inputDate.toString());
                }
            });
            main=(MainActivity) getActivity();
            setCurrentDate();
            btnThu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Get the selected date from the button
                        String selectedDate = btnDatePicker.getText().toString();

                        // Get other input values
                        String ghiChu = edtNhapGhiChu.getText().toString();
                        String tienThuStr = edtTienThu.getText().toString();

                        // Kiểm tra xem các giá trị có hợp lệ không
                        if (selectedDate.isEmpty() || ghiChu.isEmpty() || tienThuStr.isEmpty()) {
                            Toast.makeText(requireContext(), "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int tienThu = Integer.parseInt(tienThuStr);

                        // Kiểm tra xem đã chọn danh mục chưa
//                        DanhMuc selectedDanhMuc = iconDanhMucAdapter.getDanhMucDuocChon();
//                        Log.d("ButtonThu", "Selected DanhMuc: " + selectedDanhMuc);
//                        if (selectedDanhMuc == null) {
//                            Log.e("ThêmDl", "Danh muc null");
//                            Toast.makeText(requireContext(), "Hãy chọn danh mục!", Toast.LENGTH_SHORT).show();
//                            return;
//                        }

                        // Tạo đối tượng GiaoDich
                        ThuNhap thuNhap = new ThuNhap(selectedDate, "(" + ghiChu + ")", tienThu, iconDanhMucAdapter.getDanhMucDuocChon());

                        // Lưu vào database
                        long newRowId = giaoDich_db.ThemDl(thuNhap);

                        if (newRowId != -1) {
                            Toast.makeText(requireContext(), "Đã nhập tiền thu!", Toast.LENGTH_SHORT).show();
                            edtNhapGhiChu.setText("");
                            edtTienThu.setText("");
                            edtNhapGhiChu.requestFocus();
                            iconDanhMucAdapter.resetSelectedItem();
                        } else {
                            Toast.makeText(requireContext(), "Lỗi khi thêm vào database!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException ex) {
                        Toast.makeText(requireContext(), "Hãy nhập số tiền hợp lệ!", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(requireContext(), "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                    }
                }
            });



            btnTienChi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity main=(MainActivity) getActivity();
                    main.chuyenDenNhapChi();
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
                            btnDatePicker.setText(selectedDate );
                        }
                    }, year, month, day);

                    datePickerDialog.show();
                }
            });


        }

        private void setControl(View view) {
            btnThu=view.findViewById(R.id.btnNhapTienThu);
            btnTienChi=view.findViewById(R.id.btnTienChi);
            btnDatePicker = view.findViewById(R.id.btnDatePicker);
            gvDanhMucThu =view.findViewById(R.id.gvDanhSachDM);
            listThuNhap=new ArrayList<>();
            constructGridView();
            iconDanhMucAdapter =new AdapterDanhMuc(requireContext(),listThuNhap);
            edtTienThu=view.findViewById(R.id.edtTienThu);
            edtNhapGhiChu=view.findViewById(R.id.edtNhapGhiChu);
            ivNgayTruoc =view.findViewById(R.id.ivNgayTruoc);
            ivNgaySau =view.findViewById(R.id.ivNgaySau);
            // them moi khoi tao giaodich_db
            giaoDich_db = new GiaoDich_Db(requireContext(), "dbGiaoDich", null, 1);
       //     rcShow = view.findViewById(R.id.rcShow);  // Hãy đảm bảo thay thế bằng ID RecyclerView thực tế của bạn
//            dateAdapter = new AdapterLich(requireContext(), main.listGiaoDich);
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
            listThuNhap.add(new DanhMuc(111,"Trợ cấp",R.drawable.ic_money));
            listThuNhap.add(new DanhMuc(111,"Đầu tư",R.drawable.baseline_fastfood_25));
            listThuNhap.add(new DanhMuc(111,"Lãnh lương",R.drawable.baseline_fastfood_25));

            listThuNhap.add(new DanhMuc(111,"Y Tế",R.drawable.baseline_bloodtype_24));
            listThuNhap.add(new DanhMuc(111,"Mua sắm",R.drawable.baseline_fastfood_25));
            listThuNhap.add(new DanhMuc(111,"Điện",R.drawable.baseline_battery_charging_full_24));
            listThuNhap.add(new DanhMuc(111,"Ăn chơi",R.drawable.baseline_fastfood_24));
            listThuNhap.add(new DanhMuc(111,"Giáo dục",R.drawable.baseline_fastfood_24));
            listThuNhap.add(new DanhMuc(111,"Tiền Nhà",R.drawable.baseline_fastfood_24));
            listThuNhap.add(new DanhMuc(111,"Đi xe",R.drawable.baseline_fastfood_24));




        }
    }
