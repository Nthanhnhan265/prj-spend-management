package com.project.spendmanagement;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class AdapterDanhMuc extends BaseAdapter {
    private Context context;
    private List<DanhMuc> data;
    private int viTri = -1;
    AppCompatButton btnDanhMuc;
    DanhMuc danhMucDuocChon;
    public AdapterDanhMuc(Context context, List<DanhMuc> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.custom_item_danhmuc, null);
            }

            btnDanhMuc = view.findViewById(R.id.btnCategory);
            DanhMuc item = data.get(i);
            Drawable top= btnDanhMuc.getContext().getResources().getDrawable(item.getIcon());
            btnDanhMuc.setCompoundDrawablesWithIntrinsicBounds(null,top,null,null);
            btnDanhMuc.setText(item.getTenDanhMuc());

            if (i == viTri) {
                btnDanhMuc.setBackground(context.getDrawable(R.drawable.custom_ic_danhmuc));
            } else {
                btnDanhMuc.setBackground(context.getDrawable(R.drawable.custom_ic_category_clicked));
            }

                btnDanhMuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Cập nhật vị trí mục được chọn
                    viTri = i;

                    // Cập nhật lại giao diện
                    notifyDataSetChanged();

                    // Lấy text của mục được chọn
                     danhMucDuocChon = data.get(viTri);

                     //chỗ này sẽ sửa thành mã sau khi có mã danh mục, dùng tên sẽ gây trùng
                     if(danhMucDuocChon.getTenDanhMuc()=="Thêm") {
                         FragmentDSDanhMuc fragmentDSDanhMuc=new FragmentDSDanhMuc();
                         FragmentTransaction transaction=((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                         transaction.replace(R.id.container, fragmentDSDanhMuc);
                         transaction.addToBackStack(null);
                         transaction.commit();
                     }
                }
            });

            return view;
        }
        public DanhMuc getDanhMucDuocChon() {
            if(danhMucDuocChon==null) {
                throw new RuntimeException("Ban Chua Chon Danh Muc Nao ");
            }
            return danhMucDuocChon;
        }

        public void resetSelectedItem() {
            btnDanhMuc.setBackground(context.getDrawable(R.drawable.custom_ic_danhmuc));
            notifyDataSetChanged();
        }
        public void setViTri(int i) {
            viTri=i;
        }
        

}
