package com.noman.donateit.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.noman.donateit.adapters.CategoryListAdapter;
import com.noman.donateit.data.MainScreenCategory;
import com.noman.donateit.R;
import com.noman.donateit.data.SiteDataHolder;
import com.noman.donateit.data.SiteModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.noman.donateit.data.UtilityFuncs.switchCatStr;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private ListView mListView;
    private CategoryListAdapter mAdapter;
    private List<MainScreenCategory> categoryList = new ArrayList<>();
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryList.add(new MainScreenCategory("Books", R.drawable.ic_local_library_black_24dp));
        categoryList.add(new MainScreenCategory("Food", R.drawable.ic_restaurant_black_24dp));
        categoryList.add(new MainScreenCategory("Electronics", R.drawable.ic_battery_charging_full_black_24dp));
        categoryList.add(new MainScreenCategory("Cars", R.drawable.ic_directions_car_black_24dp));
        categoryList.add(new MainScreenCategory("Blood", R.drawable.ic_whatshot_black_24dp));
        categoryList.add(new MainScreenCategory("Stationary", R.drawable.ic_edit_black_24dp));
        categoryList.add(new MainScreenCategory("Clothes", R.drawable.ic_group_black_24dp));
        categoryList.add(new MainScreenCategory("Shoes", R.drawable.ic_directions_run_black_24dp));

        mListView = (ListView) findViewById(R.id.catListView);
        mAdapter = new CategoryListAdapter(categoryList, getApplicationContext());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent sendToView = new Intent(getBaseContext(), MapListTabActivity.class);
                int swtiched = switchCatStr(categoryList.get(i).getName());
                //Toast.makeText(MainActivity.this, "Category is: " + categoryList.get(i).getName() + "  " + swtiched, Toast.LENGTH_SHORT).show();
                sendToView.putExtra("FilterCategory", swtiched);
                sendToView.putExtra("Location", mLastLocation);
                startActivity(sendToView);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendToAdd = new Intent(getBaseContext(), UploadSiteActivity.class);
                sendToAdd.putExtra("Location", mLastLocation);
                startActivity(sendToAdd);
            }
        });

        populateData();
        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Log.e("Client", "Client issues");
        }
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

    private void populateData() {
        final ArrayList<SiteModel> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DonationSites");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject obj : objects) {
                        String organ = obj.getString("siteOrganization");
                        ParseGeoPoint somePoint = obj.getParseGeoPoint("siteLatLong");
                        LatLng point = new LatLng(somePoint.getLatitude(), somePoint.getLongitude());
                        boolean userFlag = obj.getBoolean("flagged");
                        List<Integer> catList = obj.getList("siteCategoryTest");
                        String objAddress = obj.getString("siteAddress");
                        String objCity = obj.getString("siteCity");
                        String objState = obj.getString("siteState");
                        String objZip = obj.getString("siteZIP");

                        SiteModel data = new SiteModel(organ, objAddress,
                                objCity, objState, objZip, point, userFlag, catList);

                        list.add(data);

                    }
                }
                else {
                    Log.d("PARSE ERROR:", e.getMessage());
                }
            }
        });
        SiteDataHolder.getInstance().setData(list);
    }


    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Log.e("Client", "Connection failed");

    }

    @Override
    public void onConnected(Bundle arg0) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.e("Last Locaiton", mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude());

//        if (mLastLocation != null) {
//            Log.d("Location lat", ""+ mLastLocation.getLatitude());
//            Log.d("Location long", ""+ mLastLocation.getLongitude());
//        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Log.e("Client", "Connection suspended");


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

}
