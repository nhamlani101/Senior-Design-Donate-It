package com.noman.donateit.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import com.noman.donateit.fragment.MapFragment;
import com.noman.donateit.R;
import com.noman.donateit.data.SiteDataHolder;
import com.noman.donateit.fragment.SiteListFragment;
import com.noman.donateit.data.SiteModel;

import java.util.ArrayList;
import java.util.List;

import static com.noman.donateit.data.UtilityFuncs.switchCatInt;

/**
 * Created by noman on 3/29/17.
 */

public class MapListTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int filter;
    private Location mLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tabs);

        filter = getIntent().getIntExtra("FilterCategory", 0);
        mLocation = getIntent().getParcelableExtra("Location");

        setTitle(switchCatInt(filter) + " Sites");
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ArrayList<SiteModel> data = SiteDataHolder.getInstance().getData();
        Log.d("DataHolder Size", "Number is: " + data.size());

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        MapFragment mapFragment = new MapFragment();
        SiteListFragment listFragment = new SiteListFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("Filter", filter);
        bundle.putParcelable("Location", mLocation);

        mapFragment.setArguments(bundle);
        listFragment.setArguments(bundle);

        adapter.addFragment(mapFragment, "Map");
        adapter.addFragment(listFragment, "List");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
