package com.noman.donateit.data;

import java.util.ArrayList;

/**
 * Created by noman on 4/20/17.
 */

public class SiteDataHolder {

    private ArrayList<SiteModel> siteModelArrayList = new ArrayList<>();
    private static SiteDataHolder instance = null;

    public static SiteDataHolder getInstance() {
        if (instance == null) {
            instance = new SiteDataHolder();
        }
        return instance;

    }

    public ArrayList<SiteModel> getData() {
        return siteModelArrayList;
    }

    public void setData(ArrayList<SiteModel> list) {


        this.siteModelArrayList = list;
    }


}
