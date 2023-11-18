package com.project.spendmanagement;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
public class GiaoDich_Db extends SQLiteOpenHelper {

    public GiaoDich_Db(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
                Log.d("Database", "Row updated successfully. Rows affected: " + rowsAffected);
            } else {
                Log.e("Database", "No rows updated. MaGD not found: " + giaoDich.getMaGD());
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
        List<GiaoDich> data = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String sql = "SELECT * FROM tblGiaoDich";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int maGDIndex = cursor.getColumnIndex("MaGD");
                int ngayGDIndex = cursor.getColumnIndex("Ngay");
                int ghiChuIndex = cursor.getColumnIndex("GhiChu");
                int giaTriIndex = cursor.getColumnIndex("NhapTien");
                int loaiGiaoDichIndex = cursor.getColumnIndex("LoaiGiaoDich");
                int maDanhMucIndex = cursor.getColumnIndex("MaDanhMuc");

                int maGD = (maGDIndex >= 0) ? cursor.getInt(maGDIndex) : 0;
                String ngay = cursor.getString(ngayGDIndex);
                String ghiChu = cursor.getString(ghiChuIndex);
                int giaTri = cursor.getInt(giaTriIndex);
                String loaiGiaoDich = cursor.getString(loaiGiaoDichIndex);
                int maDanhMuc = (maDanhMucIndex >= 0) ? cursor.getInt(maDanhMucIndex) : 0;

                Log.d("Database", "MaGD: " + maGD + ", Ngay: " + ngay + ", GhiChu: " + ghiChu + ", GiaTri: " + giaTri + ", LoaiGiaoDich: " + loaiGiaoDich + ", MaDanhMuc: " + maDanhMuc);

                GiaoDich giaoDich;
                if (maDanhMuc > 0) {
                    DanhMuc danhMuc = layDanhMucTheoId(maDanhMuc);
                    if ("ThuNhap".equals(loaiGiaoDich)) {
                        giaoDich = new ThuNhap(ngay, ghiChu, giaTri, danhMuc);
                    } else {
                        giaoDich = new ChiTieu(ngay, ghiChu, giaTri, danhMuc);
                    }
                } else {
                    giaoDich = new GiaoDich(ngay, ghiChu, giaTri) {
                        @Override
                        public DanhMuc getDanhMuc() {
                            return null;
                        }
                    };
                }

                data.add(giaoDich);
            } while (cursor.moveToNext());
        }
       // cursor.close();
//        sqLiteDatabase.close();
        return data;
    }

    private DanhMuc layDanhMucTheoId(int idDanhMuc) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tblDanhMuc WHERE IdDanhMuc = ?",
                new String[]{String.valueOf(idDanhMuc)});

        DanhMuc danhMuc = null;
        if (cursor.moveToFirst()) {
            int tenDanhMucIndex = cursor.getColumnIndex("TenDanhMuc");
            int iconIndex = cursor.getColumnIndex("Icon");

            if (tenDanhMucIndex != -1 && iconIndex != -1) {
                String tenDanhMuc = cursor.getString(tenDanhMucIndex);
                int icon = cursor.getInt(iconIndex);
                danhMuc = new DanhMuc(idDanhMuc,tenDanhMuc, icon);
            }
        }
        cursor.close();
        db.close();

        return danhMuc;
    }


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
