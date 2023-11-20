package com.project.spendmanagement;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
public class GiaoDich_Db extends SQLiteOpenHelper {

    //, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version
    private boolean isChenDanhMucExecuted = false;
    public GiaoDich_Db(@Nullable Context context) {
        super(context, "dbGiaoDich", null, 3);

    }
    public SQLiteDatabase getDatabase() {
        return getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng DanhMuc
        String taoBangDanhMuc = "CREATE TABLE tblDanhMuc (" +
                "IdDanhMuc INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenDanhMuc TEXT," +
                "LoaiDanhMuc TEXT,"+
                "Icon INTEGER)";
        db.execSQL(taoBangDanhMuc);

        // Tạo bảng GiaoDich
        String taoBangGiaoDich = "CREATE TABLE tblGiaoDich (" +
                "MaGD INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Ngay TEXT," +
                "GhiChu TEXT," +
                "NhapTien INTEGER," +
                "LoaiGiaoDich TEXT," +
                "MaDanhMuc INTEGER," +
                "FOREIGN KEY (MaDanhMuc) REFERENCES tblDanhMuc(IdDanhMuc))";
        db.execSQL(taoBangGiaoDich);

        if (!isChenDanhMucExecuted) {
            //ChenDanhMuc();
            isChenDanhMucExecuted = true; // Đánh dấu là đã thực hiện
        }


    }
    public void deleteDatabase(Context context) {
        context.deleteDatabase("dbGiaoDich");
    }
    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = this.getReadableDatabase();
        } catch (Exception e) {
            // Database chưa tồn tại hoặc có lỗi xảy ra
        }
        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }
    public long ThemDl(GiaoDich giaoDich) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Ngay", giaoDich.getNgayGD());
        values.put("GhiChu", giaoDich.getGhiChu());
        values.put("NhapTien", giaoDich.getGiaTri());
        values.put("LoaiGiaoDich", giaoDich instanceof ThuNhap ? "ThuNhap" : "ChiTieu");
        values.put("MaDanhMuc", giaoDich.getDanhMuc() != null ? giaoDich.getDanhMuc().getId() : 0);
        DanhMuc danhMuc = giaoDich.getDanhMuc();
        if (danhMuc != null) {
            values.put("MaDanhMuc", danhMuc.getId());
        } else {
            values.put("MaDanhMuc", (Integer) null);
        }
        Log.d("Database", "Đã thêm vào bảng  tblGiaoDich: " +
                "Ngay: " + giaoDich.getNgayGD() +
                ", GhiChu: " + giaoDich.getGhiChu() +
                ", NhapTien: " + giaoDich.getGiaTri() +
                ", LoaiGiaoDich: " + (giaoDich instanceof ThuNhap ? "ThuNhap" : "ChiTieu") +
                ", MaDanhMuc: " + (giaoDich.getDanhMuc() != null ? giaoDich.getDanhMuc().getId() : 0));


        long newRowId = db.insert("tblGiaoDich", null, values);

        if (newRowId != -1) {
            Log.d("Database", "Thêm Thành Công . Row ID: " + newRowId);
        } else {
            Log.e("Database", "Thêm thất bại !.");
        }

        db.close();
        return newRowId;
    }

    public void XoaDL(GiaoDich giaoDich) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tblGiaoDich", "MaGD=?", new String[]{String.valueOf(giaoDich.getMaGD())});
        db.close();

    }
    public void SuaDl(GiaoDich giaoDich) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Ngay", giaoDich.getNgayGD());
        values.put("GhiChu", giaoDich.getGhiChu());
        values.put("NhapTien", giaoDich.getGiaTri());
        values.put("LoaiGiaoDich", giaoDich instanceof ThuNhap ? "ThuNhap" : "ChiTieu");
        values.put("MaDanhMuc", giaoDich.getDanhMuc() != null ? giaoDich.getDanhMuc().getId() : 0);
        try {
            int rowsAffected = db.update("tblGiaoDich", values, "MaGD=?", new String[]{String.valueOf(giaoDich.getMaGD())});

            if (rowsAffected > 0) {
                Log.d("Database", "Cập nhật Thành Cônng . Số dòng cập nhật : " + rowsAffected);
            } else {
                Log.e("Database", "Cập nhật không thành công . MaGD not found: " + giaoDich.getMaGD());
            }
        } catch (Exception e) {
            Log.e("Database", "Error updating row. MaGD: " + giaoDich.getMaGD(), e);
        } finally {
            db.close();
        }


//        db.update("tblGiaoDich", values, "MaGD=?", new String[]{String.valueOf(giaoDich.getMaGD())});
//        db.close();
    }
    public List<GiaoDich> DocDl() {
        List<GiaoDich> data_gd = new ArrayList<>();
        List<ThuNhap> data_thunhap = new ArrayList<>();
        List<ChiTieu> data_chitieu = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        String query = "SELECT * FROM tblGiaoDich";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int maGDIndex = cursor.getColumnIndex("MaGD");
                int ngayGDIndex = cursor.getColumnIndex("Ngay");
                int ghiChuIndex = cursor.getColumnIndex("GhiChu");
                int giaTriIndex = cursor.getColumnIndex("NhapTien");
                int loaiGiaoDichIndex = cursor.getColumnIndex("LoaiGiaoDich");
                int maDanhMucIndex = cursor.getColumnIndex("MaDanhMuc");

                // Kiểm tra xem các cột có tồn tại trong Cursor không
                if (maGDIndex != -1 && ngayGDIndex != -1 && ghiChuIndex != -1
                        && giaTriIndex != -1 && loaiGiaoDichIndex != -1 && maDanhMucIndex != -1) {
                    int maGD = cursor.getInt(maGDIndex);
                    String ngayGD = cursor.getString(ngayGDIndex);
                    String ghiChu = cursor.getString(ghiChuIndex);
                    int giaTri = cursor.getInt(giaTriIndex);
                    String loaiGiaoDich = cursor.getString(loaiGiaoDichIndex);
                    int maDanhMuc = cursor.getInt(maDanhMucIndex);

                    GiaoDich giaoDich;
                    if ("ThuNhap".equals(loaiGiaoDich)) {
                        // Sửa lại cách tạo đối tượng ThuNhap
                        giaoDich = new ThuNhap(ngayGD, ghiChu, giaTri, layDanhMucTheoId(maDanhMuc));
                      //  data_gd.add(giaoDich);
                      //  data_thunhap.add((ThuNhap) giaoDich);
                    } else {
                        // Sửa lại cách tạo đối tượng ChiTieu
                        giaoDich = new ChiTieu(ngayGD, ghiChu, giaTri, layDanhMucTheoId(maDanhMuc));
                     //   data_gd.add(giaoDich);
                     //   data_chitieu.add((ChiTieu) giaoDich);
                    }
                    data_gd.add(giaoDich);
                } else {
                    Log.e("Database", "One or more columns not found in Cursor");
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        Log.d("Database", "Đã đọc dữ liệu từ cơ sở dữ liệu! Số lượng giao dịch: " + data_gd.size());

        // Kiểm tra và log dữ liệu của các danh sách
        Log.d("Database", "Số lượng Thu nhập: " + data_thunhap.size());
        for (ThuNhap thuNhap : data_thunhap) {
            Log.d("Database", thuNhap.toString());
        }

        Log.d("Database", "Số lượng Chi tiêu: " + data_chitieu.size());
        for (ChiTieu chiTieu : data_chitieu) {
            Log.d("Database", chiTieu.toString());
        }

        return data_gd;
    }
    private DanhMuc layDanhMucTheoId(int idDanhMuc) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tblDanhMuc WHERE IdDanhMuc = ?",
                new String[]{String.valueOf(idDanhMuc)});

        DanhMuc danhMuc = null;
        if (cursor.moveToFirst()) {
            int tenDanhMucIndex = cursor.getColumnIndex("TenDanhMuc");
            int iconIndex = cursor.getColumnIndex("Icon");
            int loaiDanhMucIndex = cursor.getColumnIndex("LoaiDanhMuc");

            if (tenDanhMucIndex != -1 && iconIndex != -1) {
                String tenDanhMuc = cursor.getString(tenDanhMucIndex);
                String loaiDanhMuc = cursor.getString(loaiDanhMucIndex);
                int icon = cursor.getInt(iconIndex);
                danhMuc = new DanhMuc(idDanhMuc, tenDanhMuc,loaiDanhMuc, icon);
            }
        }
        if(cursor!=null) {
            cursor.close();

        }
        db.close();

        return danhMuc;
    }
    //Tạo phương thức chèn danh mục cho DB
    public void ChenDanhMuc() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM tblDanhMuc", null);
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                int count = cursor.getInt(0);

                if (count == 0) {
                    ContentValues values1 = new ContentValues();
                    values1.put("TenDanhMuc", "Ăn uống");
                    values1.put("LoaiDanhMuc ", "Chi");
                    values1.put("Icon", R.drawable.ic_danhmuc_doan);
                    database.insert("tblDanhMuc", null, values1);

                    ContentValues values2 = new ContentValues();
                    values2.put("TenDanhMuc", "Sửa chữa");
                    values2.put("LoaiDanhMuc ", "Chi");
                    values2.put("Icon", R.drawable.ic_danhmuc_suachua);
                    database.insert("tblDanhMuc", null, values2);

                    ContentValues values3 = new ContentValues();
                    values3.put("TenDanhMuc", "Mua sắm");
                    values3.put("LoaiDanhMuc ", "Chi");
                    values3.put("Icon", R.drawable.ic_danhmuc_muasam);
                    database.insert("tblDanhMuc", null, values3);

                    ContentValues values4 = new ContentValues();
                    values4.put("TenDanhMuc", "Trợ cấp");
                    values4.put("LoaiDanhMuc ", "Thu");
                    values4.put("Icon", R.drawable.ic_danhmuc_tien);
                    database.insert("tblDanhMuc", null, values4);

                    ContentValues values5 = new ContentValues();
                    values5.put("TenDanhMuc", "Trợ cấp");
                    values5.put("LoaiDanhMuc ", "Thu");
                    values5.put("Icon", R.drawable.ic_danhmuc_vitien);
                    database.insert("tblDanhMuc", null, values5);
                }
            } finally {
                cursor.close();
            }
        }

        database.close();

    }
    public List<DanhMuc> LayDanhMucChi() {
        List<DanhMuc> danhMucList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tblDanhMuc WHERE LoaiDanhMuc='Chi'", null);

        try {

            if (cursor.moveToFirst()) {
                do {
                    int iMaDM = cursor.getColumnIndex("IdDanhMuc");
                    int iTenDanhMuc = cursor.getColumnIndex("TenDanhMuc");
                    int iLoaiDM = cursor.getColumnIndex("LoaiDanhMuc");
                    int iIcon = cursor.getColumnIndex("Icon");

                    if (iMaDM >= 0 && iTenDanhMuc >= 0 && iIcon >= 0) {
                        danhMucList.add(new DanhMuc(
                                cursor.getInt(iMaDM),
                                cursor.getString(iTenDanhMuc),
                                cursor.getString(iLoaiDM),
                                cursor.getInt(iIcon)
                        ));
                    } else {
                        Log.e("GiaoDich_Db/LayBangDanhMuc", "Không tìm thấy cột! " + iMaDM + iTenDanhMuc + iIcon);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("GiaoDich_Db/LayBangDanhMuc", "Có lỗi xảy ra: " + ex.getMessage());
        } finally {
            // Đóng cursor và database trong finally block
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return danhMucList;

    }
    public List<DanhMuc> LayDanhMucThu() {
        List<DanhMuc> danhMucList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tblDanhMuc WHERE LoaiDanhMuc='Thu'", null);

        try {

            if (cursor.moveToFirst()) {
                do {
                    int iMaDM = cursor.getColumnIndex("IdDanhMuc");
                    int iTenDanhMuc = cursor.getColumnIndex("TenDanhMuc");
                    int iLoaiDM = cursor.getColumnIndex("LoaiDanhMuc");
                    int iIcon = cursor.getColumnIndex("Icon");

                    if (iMaDM >= 0 && iTenDanhMuc >= 0 && iIcon >= 0) {
                        danhMucList.add(new DanhMuc(
                                cursor.getInt(iMaDM),
                                cursor.getString(iTenDanhMuc),
                                cursor.getString(iLoaiDM),
                                cursor.getInt(iIcon)
                        ));
                    } else {
                        Log.e("GiaoDich_Db/LayBangDanhMuc", "Không tìm thấy cột! " + iMaDM + iTenDanhMuc + iIcon);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("GiaoDich_Db/LayBangDanhMuc", "Có lỗi xảy ra: " + ex.getMessage());
        } finally {
            // Đóng cursor và database trong finally block
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return danhMucList;

    }
//    private DanhMuc layDanhMucTheoId(int idDanhMuc) {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM tblDanhMuc WHERE IdDanhMuc = ?",
//                new String[]{String.valueOf(idDanhMuc)});
//
//        DanhMuc danhMuc = null;
//        if (cursor.moveToFirst()) {
//            int tenDanhMucIndex = cursor.getColumnIndex("TenDanhMuc");
//            int iconIndex = cursor.getColumnIndex("Icon");
//
//            if (tenDanhMucIndex != -1 && iconIndex != -1) {
//                String tenDanhMuc = cursor.getString(tenDanhMucIndex);
//                int icon = cursor.getInt(iconIndex);
//                danhMuc = new DanhMuc(123,tenDanhMuc, icon);
//            }
//        }
//        cursor.close();
//        db.close();
//
//        return danhMuc;
//    }
//

//    private int layIdDanhMuc(DanhMuc danhMuc) {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT IdDanhMuc FROM tblDanhMuc WHERE TenDanhMuc = ?",
//                new String[]{danhMuc.getTenDanhMuc()});
//
//        int idDanhMuc = -1;
//        int columnIndex = cursor.getColumnIndex("IdDanhMuc");
//        if (columnIndex != -1 && cursor.moveToFirst()) {
//            idDanhMuc = cursor.getInt(columnIndex);
//        }
//
//        cursor.close();
//        db.close();
//
//        return idDanhMuc;
//    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
