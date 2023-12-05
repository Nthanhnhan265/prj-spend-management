package com.project.spendmanagement;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    HomeFragment_ChiTieu homeFragment;
    FragmentPageLich pageLich;
    FragmentBaoCaoChi pageBaoCao;
    FragmentPageKhac pageKhac;
     List<GiaoDich> listGiaoDich =new ArrayList<>();
    // them moi
    public static ArrayList<String>icons=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            constructIcon();

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU)  {
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
                }

            }
//         //khong xoa
//        GiaoDich_Db giaoDichDb=new GiaoDich_Db(this);
//        giaoDichDb.deleteDatabase(MainActivity.this);
//        giaoDichDb.ChenDanhMuc();

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
        pageLich = new FragmentPageLich();
        pageBaoCao = new FragmentBaoCaoChi();
        pageKhac = new FragmentPageKhac();

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

    public void chuyenDenLich() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, pageLich)
                .commit();
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

    public void showNotification(String title,String desc) {
        String id="CHANEL_ID";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),id) ;
        builder.setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setContentText(desc)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,
                intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel=notificationManager.getNotificationChannel(id);
            if(notificationChannel==null) {
                int importance =NotificationManager.IMPORTANCE_HIGH;
                notificationChannel=new NotificationChannel(id,"desc",importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);

            }
        }
        notificationManager.notify(0,builder.build());


    }
}







