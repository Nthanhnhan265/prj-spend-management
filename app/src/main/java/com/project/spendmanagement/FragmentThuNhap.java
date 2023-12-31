    package com.project.spendmanagement;

    import android.app.DatePickerDialog;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Adapter;
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



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home_thunhap, container, false);
            Toast.makeText(requireContext(), "Page Thu", Toast.LENGTH_SHORT).show();
            setControl(view);
            setEvent();

            return view;
        }
        private void setEvent() {
            AdapterDanhMuc.themDanhMuc=1;
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
                        // Lấy ngày trong lịch
                        String selectedDate = btnDatePicker.getText().toString();
                        // Lấy ghi chú và số tiền
                        String ghiChu = edtNhapGhiChu.getText().toString();
                        String tienThuStr = edtTienThu.getText().toString();

                        // Kiểm tra xem các giá trị có hợp lệ không
                        if (selectedDate.isEmpty() || ghiChu.isEmpty() || tienThuStr.isEmpty()) {
                            Toast.makeText(requireContext(), "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int tienThu = Integer.parseInt(tienThuStr);


                        // Tạo đối tượng GiaoDich
                        ThuNhap thuNhap = new ThuNhap(selectedDate, ghiChu, tienThu, iconDanhMucAdapter.getDanhMucDuocChon());
                        GiaoDich_Db giaoDich_db=new GiaoDich_Db(requireContext());
                        // Lưu vào database
                        long newRowId = giaoDich_db.ThemDl(thuNhap);

                        if (newRowId != -1) {
                            Toast.makeText(requireContext(), "Đã nhập tiền thu vào database !", Toast.LENGTH_SHORT).show();
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
                        Log.e("Err", "onClick: "+ex.getMessage() );
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
        //đọc từ DB lên
        private void constructGridView() {
            GiaoDich_Db giaoDichDb=new GiaoDich_Db(requireContext());
            listThuNhap.addAll(giaoDichDb.LayDanhMucThu());
            //dùng mở giao diện chỉnh sửa, thêm danh mục
            listThuNhap.add(new DanhMuc(000,"Thêm",null,R.drawable.ic_add));

        }
    }
