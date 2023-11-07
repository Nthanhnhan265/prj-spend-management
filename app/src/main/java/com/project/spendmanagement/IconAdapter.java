package com.project.spendmanagement;

import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



import androidx.appcompat.widget.AppCompatButton;

import java.util.List;

public class IconAdapter extends BaseAdapter {
    private Context context;
    private List<Category> data;
    private int selectedPosition = -1;
    AppCompatButton btnCategory;
    Category selectedCategory;
    public IconAdapter(Context context, List<Category> data) {
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
                view = inflater.inflate(R.layout.custom_item_category, null);
            }

            btnCategory = view.findViewById(R.id.btnCategory);
            Category item = data.get(i);
            Drawable top=btnCategory.getContext().getResources().getDrawable(item.getIcon());
            btnCategory.setCompoundDrawablesWithIntrinsicBounds(null,top,null,null);
            btnCategory.setText(item.getTen_category());

            if (i == selectedPosition) {
                btnCategory.setBackground(context.getDrawable(R.drawable.custom_ic_category));
            } else {
                btnCategory.setBackground(context.getDrawable(R.drawable.custom_ic_category_clicked));
            }

                btnCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Cập nhật vị trí mục được chọn
                    selectedPosition = i;

                    // Cập nhật lại giao diện
                    notifyDataSetChanged();

                    // Lấy text của mục được chọn
                     selectedCategory = data.get(selectedPosition);
                }
            });

            return view;
        }
        public Category getSelectedText() {
            return selectedCategory;
        }

        public void resetSelectedItem() {
            btnCategory.setBackground(context.getDrawable(R.drawable.custom_ic_category));
            notifyDataSetChanged();
        }
//
//        try {
//            if (view == null) {
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.custom_item_category, null);
//            }
//
//            AppCompatButton btnCategory = view.findViewById(R.id.btnCategory);
//            Category item = data.get(i);
//            Drawable top = context.getResources().getDrawable(R.drawable.baseline_add_24, null);
//
////            btnCategory.setBackgroundResource(item.getIcon());
////            btnCategory.setText(item.getIcon());
//       //     btnCategory.setCompoundDrawables(null, null, null, null);
//        //    btnCategory.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null); // Đặt biểu tượng trên cùng
//        //   btnCategory.setCompoundDrawables(null, top, null, null);
//       //    btnCategory.setCompoundDrawablePadding(100);
//            btnCategory.setText(item.getTen_category());
//        }
//        catch (Exception ex) {
//
//        }
//        return view;

}
