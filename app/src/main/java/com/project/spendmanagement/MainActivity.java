package com.project.spendmanagement;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
           BottomNavigationView navigationView=findViewById(R.id.bottom_nav);

        // Khoi Tao Cac Fragment
        homeFragment = new HomeFragment_ChiTieu();
        pageLich = new PageLich();
        pageBaoCao = new PageBaoCao();
        pageKhac = new PageKhac();
        FragmentDSDanhMuc pageDSDanhMuc=new FragmentDSDanhMuc();

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
    public void chuyenDenLich() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, pageLich)
                .commit();
    }


    @Override
    public void themGiaoDich(GiaoDich transaction) {
        if (listGiaoDich != null) {
            listGiaoDich.add(transaction);

        }
    }

}







