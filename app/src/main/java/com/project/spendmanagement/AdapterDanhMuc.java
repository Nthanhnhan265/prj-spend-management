package com.project.spendmanagement;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



import androidx.appcompat.widget.AppCompatButton;

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
                }
            });

            return view;
        }
        public DanhMuc getSelectedText() {
            if(danhMucDuocChon==null) {
                throw new RuntimeException("Chua chon danh muc");
            }
            return danhMucDuocChon;
        }

        public void resetSelectedItem() {
            btnDanhMuc.setBackground(context.getDrawable(R.drawable.custom_ic_danhmuc));
            notifyDataSetChanged();
        }

}
