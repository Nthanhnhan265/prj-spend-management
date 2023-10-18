package com.project.spendmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    PageLich pageLich;
    PageBaoCao pageBaoCao;
    PageKhac pageKhac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        BottomNavigationView  navigationView=findViewById(R.id.bottom_nav);
        // Khởi tạo các fragment
        homeFragment = new HomeFragment();
        pageLich = new PageLich();
        pageBaoCao = new PageBaoCao();
        pageKhac = new PageKhac();
             getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
           navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
        }







