package com.project.spendmanagement;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.data.PieEntry;

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

    public void XoaDL(int maGD) {
        SQLiteDatabase database = getWritableDatabase();
        try {
            int dong = database.delete("tblGiaoDich", "MaGD=?", new String[]{String.valueOf(maGD)});

            if (dong > 0) {
                Log.d("Database", "Xóa giao dịch thành công. Số dòng đã xóa: " + dong);

            } else {
                Log.e("Database", "Xóa giao dịch không thành công. MaGD " + maGD);

            }
        } catch (Exception e) {
            Log.e("Database", "Lỗi xóa danh mục. MaGD: ");
        } finally {
            database.close();
        }
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
            Log.e("Database", "Error updating row. MaGD: " + giaoDich.getMaGD());
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
                        Log.d("Ngay ",ngayGD);
                        giaoDich = new ThuNhap(ngayGD, ghiChu, giaTri, layDanhMucTheoId(maDanhMuc));
                        giaoDich.setMaGD(maGD);
                    } else {
                        // Sửa lại cách tạo đối tượng ChiTieu
                        Log.d("Ngay ",ngayGD);
                        giaoDich = new ChiTieu(ngayGD, ghiChu, giaTri, layDanhMucTheoId(maDanhMuc));
                        giaoDich.setMaGD(maGD);
                    }
                    data_gd.add(giaoDich);
                } else {
                    Log.e("Database", "");
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
    //Lấy danh sách danh mục chi trong db
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
    //Lấy danh sách danh mục thu trong db
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
    //Thêm danh mục vào db
    public int ThemDanhMuc(DanhMuc danhMuc) {
        SQLiteDatabase database = getWritableDatabase();
        try {
            ContentValues values1 = new ContentValues();
            values1.put("TenDanhMuc", danhMuc.getTenDanhMuc());
            values1.put("LoaiDanhMuc ", danhMuc.getLoaiDM());
            values1.put("Icon", danhMuc.getIcon());
            database.insert("tblDanhMuc", null, values1);
            return 1;
        }catch (Exception ex ) {
            database.close();
            Log.e("ThemDanhMuc: " ,ex.getMessage());
            return -1;
        }

    }

// Phương thức sửa danh mục trong db
    public int SuaDanhMuc(DanhMuc danhMuc) {
        SQLiteDatabase database = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("TenDanhMuc", danhMuc.getTenDanhMuc());
            values.put("LoaiDanhMuc", danhMuc.getLoaiDM());
            values.put("Icon", danhMuc.getIcon());

            int dong = database.update("tblDanhMuc", values, "IdDanhMuc=?", new String[]{String.valueOf(danhMuc.getId())});

            if (dong > 0) {
                Log.d("Database", "Sửa danh mục thành công");
                return 1; // Sửa thành công
            } else {
                Log.e("Database", "Sửa danh mục không thành công. Không thấy mã " + danhMuc.getId());
                return -1; // Sửa không thành công, IdDanhMuc không tìm thấy
            }
        } catch (Exception e) {
            Log.e("Database", "Lỗi cập nhật, Ma danh mục : " + danhMuc.getId(), e);
            return -1; // Lỗi khi sửa danh mục
        } finally {
            database.close();
        }
    }

// Phương thức xóa danh mục trong db
    public int XoaDanhMuc(int idDanhMuc) {
        SQLiteDatabase database = getWritableDatabase();

        try {
            int dong = database.delete("tblDanhMuc", "IdDanhMuc=?", new String[]{String.valueOf(idDanhMuc)});

            if (dong > 0) {
                Log.d("Database", "Xóa danh mục thành công. Số dòng đã xóa: " + dong);
                return 1; // Xóa thành công
            } else {
                Log.e("Database", "Xóa danh mục không thành công. IdDanhMuc không tìm thấy: " + idDanhMuc);
                return -1; // Xóa không thành công, IdDanhMuc không tìm thấy
            }
        } catch (Exception e) {
            Log.e("Database", "Lỗi xóa danh mục. IdDanhMuc: " + idDanhMuc, e);
            return -1; // Lỗi khi xóa danh mục
        } finally {
            database.close();
        }
    }

//Phương thức trả về phần trăm của từng danh mục chi trong tháng
    public ArrayList<PieEntry> LayPhanTramDanhMucChi(int thang, int nam) {
        ArrayList<PieEntry>phamTramDanhMuc=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String thangNam = thang+"/"+nam;
        String query = "SELECT " +
                "G.MaDanhMuc, " +
                "D.TenDanhMuc, " +
                "STRFTIME('%m/%Y', G.Ngay) AS ThangNam, " +
                "SUM(G.NhapTien) AS TongTienTrongThang, " +
                "(SUM(G.NhapTien) * 100.0) / " +
                "(SELECT SUM(NhapTien) FROM tblGiaoDich WHERE LoaiGiaoDich = 'ChiTieu' AND Ngay LIKE ?) AS PhanTramChiTieu " +
                "FROM tblGiaoDich G " +
                "JOIN tblDanhMuc D ON G.MaDanhMuc = D.IdDanhMuc " +
                "WHERE G.LoaiGiaoDich = 'ChiTieu' AND G.Ngay LIKE ? " +
                "GROUP BY G.MaDanhMuc, D.TenDanhMuc, ThangNam";
        Cursor cursor= db.rawQuery(query,new String[]{"%" + thangNam + "%", "%" + thangNam + "%"});

        try {
            if (cursor.moveToFirst()) {
                do {
                    //lấy chỉ mục của cột được truyền vào
                    int iTenDM = cursor.getColumnIndex("TenDanhMuc");
                    int iMaDM = cursor.getColumnIndex("MaDanhMuc");
                    int iPhanTramChiTieu = cursor.getColumnIndex("PhanTramChiTieu");
                    Log.d("LayPhanTramDanhMucChi: ",iTenDM+iPhanTramChiTieu+"");
                    if (iTenDM >= 0 && iPhanTramChiTieu>=0) {
                        //khi có chỉ mục thì có thể lấy nội dung của cột
                        phamTramDanhMuc.add(new PieEntry(cursor.getInt(iPhanTramChiTieu),cursor.getString(iTenDM),cursor.getString(iMaDM)));
                   } else {
                        Log.e("GiaoDich_Db/LayPhanTramDanhMucChi", "Không tìm thấy cột! " + iTenDM + iPhanTramChiTieu);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("GiaoDich_Db/LayBangDanhMuc", "Có lỗi xảy ra: " + ex.getMessage());
        }
        finally {
            // Đóng cursor và database trong finally block
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return phamTramDanhMuc;
    }


//Phương thức trả về phần trăm của từng danh mục thu trong tháng
    public ArrayList<PieEntry> LayPhanTramDanhMucThu(int thang, int nam) {
        ArrayList<PieEntry>phamTramDanhMuc=new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String thangNam = thang+"/"+nam;
        String query = "SELECT " +
                "G.MaDanhMuc, " +
                "D.TenDanhMuc, " +
                "STRFTIME('%m/%Y', G.Ngay) AS ThangNam, " +
                "SUM(G.NhapTien) AS TongTienTrongThang, " +
                "(SUM(G.NhapTien) * 100.0) / " +
                "(SELECT SUM(NhapTien) FROM tblGiaoDich WHERE LoaiGiaoDich = 'ThuNhap' AND Ngay LIKE ?) AS PhanTramThuNhap " +
                "FROM tblGiaoDich G " +
                "JOIN tblDanhMuc D ON G.MaDanhMuc = D.IdDanhMuc " +
                "WHERE G.LoaiGiaoDich = 'ThuNhap' AND G.Ngay LIKE ? " +
                "GROUP BY G.MaDanhMuc, D.TenDanhMuc, ThangNam";
        Cursor cursor= db.rawQuery(query,new String[]{"%" + thangNam + "%", "%" + thangNam + "%"});

        try {
            if (cursor.moveToFirst()) {
                do {
                    //lấy chỉ mục của cột được truyền vào
                    int iMaDM = cursor.getColumnIndex("MaDanhMuc");
                    int iTenDM = cursor.getColumnIndex("TenDanhMuc");
                    int iPhanTramThuNhap = cursor.getColumnIndex("PhanTramThuNhap");
                    if (iTenDM >= 0 && iPhanTramThuNhap>=0) {
                        //khi có chỉ mục thì có thể lấy nội dung của cột
                        phamTramDanhMuc.add(new PieEntry(cursor.getInt(iPhanTramThuNhap),cursor.getString(iTenDM),cursor.getString(iMaDM)));
                    } else {
                        Log.e("GiaoDich_Db/LayPhanTramDanhMucChi", "Không tìm thấy cột! " + iTenDM + iPhanTramThuNhap);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("GiaoDich_Db/LayBangDanhMuc", "Có lỗi xảy ra: " + ex.getMessage());
        }
        finally {
            // Đóng cursor và database trong finally block
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return phamTramDanhMuc;
    }
//ToDo: phương thức trả về giao dịch khi biết mã và tháng
    public List<GiaoDich> LayGiaoDichTheoDanhMuc(int thang, int nam, int maDanhMucDaChon) {
        List<GiaoDich> data_gd = new ArrayList<>();
        List<ThuNhap> data_thunhap = new ArrayList<>();
        List<ChiTieu> data_chitieu = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        String query = "SELECT * FROM tblGiaoDich WHERE Ngay like ? And MaDanhMuc=?";
        Cursor cursor = database.rawQuery(query, new String[]{"%"+thang+"/"+nam+"%",maDanhMucDaChon+""});

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
                        Log.d("Ngay ",ngayGD);
                        giaoDich = new ThuNhap(ngayGD, ghiChu, giaTri, layDanhMucTheoId(maDanhMuc));
                        giaoDich.setMaGD(maGD);
                    } else {
                        // Sửa lại cách tạo đối tượng ChiTieu
                        Log.d("Ngay ",ngayGD);
                        giaoDich = new ChiTieu(ngayGD, ghiChu, giaTri, layDanhMucTheoId(maDanhMuc));
                        giaoDich.setMaGD(maGD);
                    }
                    data_gd.add(giaoDich);
                } else {
                    Log.e("Database", "");
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



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
