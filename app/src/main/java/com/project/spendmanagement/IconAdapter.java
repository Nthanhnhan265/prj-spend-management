package com.project.spendmanagement;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import java.util.List;

public class IconAdapter extends BaseAdapter {
    private Context context;
    private List<Category> data;
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

        AppCompatButton btnCategory = view.findViewById(R.id.btnCategory);
        Category item=data.get(i);
        Drawable top = context.getResources().getDrawable(R.drawable.baseline_fastfood_24,null);
        btnCategory.setText(item.getTen_category());
        btnCategory.setCompoundDrawables(null,top,null,null);
        btnCategory.setCompoundDrawablePadding(100);
        return view;
    }
}
