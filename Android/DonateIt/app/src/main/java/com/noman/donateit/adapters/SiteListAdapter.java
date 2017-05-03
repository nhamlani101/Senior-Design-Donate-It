package com.noman.donateit.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.noman.donateit.R;
import com.noman.donateit.data.SiteModel;

import java.util.ArrayList;

import static com.noman.donateit.data.UtilityFuncs.switchCatInt;

/**
 * Created by noman on 4/26/17.
 */

public class SiteListAdapter extends BaseAdapter {

    private ArrayList<SiteModel> siteList;
    private Context mContext;

    public SiteListAdapter(ArrayList<SiteModel> siteList, Context mContext) {
        this.siteList = siteList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return siteList.size();
    }

    @Override
    public Object getItem(int i) {
        return siteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view.inflate(mContext, R.layout.site_list_row, null);
        TextView siteOrgan = (TextView) v.findViewById(R.id.siteListOrgan);
        TextView siteAdd = (TextView) v.findViewById(R.id.siteListAddress);
        TextView siteCat = (TextView) v.findViewById(R.id.siteListCats);
        siteOrgan.setText(siteList.get(i).getOrganization());
        String theAddress = siteList.get(i).getAddress()
                + ", " + siteList.get(i).getCity() + ", " + siteList.get(i).getState()
                + " " + siteList.get(i).getZip();

        StringBuilder builder = new StringBuilder();

        for (int j = 0; j < siteList.get(i).getCategories().size(); j++) {
            builder.append(switchCatInt(siteList.get(i).getCategories().get(j)));
            if (j != (siteList.get(i).getCategories().size() - 1)) {
                builder.append(", ");

            }
        }

        siteAdd.setText(theAddress);
        siteCat.setText(builder.toString());

        return v;
    }
}
