package com.project.spendmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CapNhatDuLieu {

    HomeFragment_ChiTieu homeFragment;
    PageLich pageLich;
    PageBaoCao pageBaoCao;
    PageKhac pageKhac;
     List<GiaoDich> listGiaoDich =new ArrayList<>();
    // them moi
    public static ArrayList<String>icons=new ArrayList<>();
    private GiaoDich_Db giaoDichDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            constructIcon();
//             RESET DATABASE
//            GiaoDich_Db giaoDichDb=new GiaoDich_Db(this);
//            giaoDichDb.deleteDatabase(MainActivity.this); // 'this' is the reference to the current activity
//            giaoDichDb.ChenDanhMuc();
         //khong xoa

        GiaoDich_Db giaoDichDb = new GiaoDich_Db(this);
        if (giaoDichDb.checkDatabase()) {
            // Database đã tồn tại, không cần phải xóa nó
            Log.i("Database", "Database đã tồn tại, không cần phải xóa nó");
        } else {
            // Database chưa tồn tại, xóa nó và tạo lại
            giaoDichDb.deleteDatabase(MainActivity.this);
            giaoDichDb.ChenDanhMuc();

            Log.i("Database", "Đã xóa database và tạo lại");
        }
        BottomNavigationView navigationView=findViewById(R.id.bottom_nav);

        // Khoi Tao Cac Fragment
        homeFragment = new HomeFragment_ChiTieu();
        pageLich = new PageLich();
        pageBaoCao = new PageBaoCao();
        pageKhac = new PageKhac();

      //  adapterGiaoDich = new AdapterGiaoDich();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        navigationView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nhap) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;

                }
                if (item.getItemId() == R.id.lich) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, pageLich).commit();
                    listGiaoDich.clear();
                    // Đọc dữ liệu từ cơ sở dữ liệu
                    listGiaoDich.addAll(giaoDichDb.DocDl());

                    return true;
                }
                if (item.getItemId() == R.id.baocao) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, pageBaoCao).commit();
                    return true;
                }
                if (item.getItemId() == R.id.khac) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, pageKhac).commit();
                    return true;
                }
                return false;
            }
        });


    }
    public void chuyenDenNhapThu() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FragmentThuNhap())
                .addToBackStack(null)  // (Tùy chọn) cho phép quay lại Fragment trước đó
                .commit();
    }
    public void chuyenDenNhapChi() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, homeFragment)
                .addToBackStack(null)
                .commit();
    }
    public GiaoDich_Db getGiaoDichDb() {
        return giaoDichDb;
    }

    public void chuyenDenLich() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, pageLich)
                .commit();
    }


    @Override
    public void themGiaoDich(GiaoDich transaction) {
        if (listGiaoDich != null) {
            listGiaoDich.add(transaction);
//            if (adapterGiaoDich != null) {
//                adapterGiaoDich.notifyDataSetChanged();
//            }
        }
    }
    //Khởi tạo các icons
    public void constructIcon() {
        icons.add("ic_danhmuc_doan");
        icons.add("ic_danhmuc_tiente");
        icons.add("ic_danhmuc_suachua");
        icons.add("ic_danhmuc_muasam");
        icons.add("ic_danhmuc_tien");
        icons.add("ic_danhmuc_vitien");

    }

}







