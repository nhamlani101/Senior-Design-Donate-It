package com.noman.donateit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private CategoryListAdapter mAdapter;
    private List<MainScreenCategory> categoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryList.add(new MainScreenCategory("Food", R.drawable.ic_restaurant_menu_black_24dp));
        categoryList.add(new MainScreenCategory("Electronics", R.drawable.ic_battery_charging_full_black_24dp));
        categoryList.add(new MainScreenCategory("Clothes", R.drawable.ic_group_black_24dp));
        categoryList.add(new MainScreenCategory("Stationary", R.drawable.ic_edit_black_24dp));
        categoryList.add(new MainScreenCategory("Books", R.drawable.ic_local_library_black_24dp));
        categoryList.add(new MainScreenCategory("Cars", R.drawable.ic_directions_car_black_24dp));
        categoryList.add(new MainScreenCategory("More Categories", R.drawable.ic_more_horiz_black_24dp));

        mListView = (ListView) findViewById(R.id.catListView);
        mAdapter = new CategoryListAdapter(categoryList, getApplicationContext());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Snackbar.make(view, "You selected: " + categoryList.get(i).getName(), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent sendToView = new Intent(getBaseContext(), MapListTabActivity.class);
                sendToView.putExtra("FilterCategory", categoryList.get(i).getName());
                startActivity(sendToView);

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "LOL NOTHING YET FOOOLEDDD YAA", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
