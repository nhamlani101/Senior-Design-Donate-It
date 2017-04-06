package com.noman.donateit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by noman on 3/28/17.
 */

public class CategoryListAdapter extends BaseAdapter {

    private List<MainScreenCategory> categoryList;
    private Context mContext;

    public CategoryListAdapter(List<MainScreenCategory> categoryList, Context mContext) {
        this.categoryList = categoryList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view.inflate(mContext, R.layout.category_list_view, null);
        TextView catName = (TextView) v.findViewById(R.id.mainScreenCatName);
        ImageView catImage = (ImageView) v.findViewById(R.id.mainScreenImage);
        catName.setText(categoryList.get(i).getName());
        catImage.setImageResource(categoryList.get(i).getResource());
        return v;
    }
}
