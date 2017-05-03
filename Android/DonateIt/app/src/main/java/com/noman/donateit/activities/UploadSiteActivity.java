package com.noman.donateit.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.ImagePickerSheetView;
import com.noman.donateit.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import static com.noman.donateit.data.UtilityFuncs.switchCatStr;


/**
 * Created by noman on 4/16/17.
 */

public class UploadSiteActivity extends AppCompatActivity {

    int MY_PERMISSION_REQUEST_READ_STORAGE = 1;
    int MY_PERMISSION_REQUEST_WRITE_STORAGE = 1;
    private EditText organET;
    private EditText typeET;
    private EditText nameET;
    private ImageView upload;
    private ImageView selectedImage;
    private Button submitButton;
    private BottomSheetLayout bottomSheetLayout;
    byte[] image = null;
    private Location mLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_site);

        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomSheet);
        bottomSheetLayout.setPeekOnDismiss(true);

        mLocation = getIntent().getParcelableExtra("Location");

        organET = (EditText) findViewById(R.id.organET);
        typeET = (EditText) findViewById(R.id.typeET);
        nameET = (EditText) findViewById(R.id.userNameET);
        upload = (ImageView) findViewById(R.id.imageViewImage);
        selectedImage = (ImageView) findViewById(R.id.selectedImage);
        submitButton = (Button) findViewById(R.id.submitButton);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_READ_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_WRITE_STORAGE);
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSheetView();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });


    }

    private void showSheetView() {
        ImagePickerSheetView sheetView = new ImagePickerSheetView.Builder(this)
                .setMaxItems(30)
                .setShowCameraOption(false)
                .setShowPickerOption(false)
                .setImageProvider(new ImagePickerSheetView.ImageProvider() {
                    @Override
                    public void onProvideImage(ImageView imageView, Uri imageUri, int size) {
                        Glide.with(UploadSiteActivity.this)
                                .load(imageUri)
                                .centerCrop()
                                .crossFade()
                                .into(imageView);
                    }
                })
                .setOnTileSelectedListener(new ImagePickerSheetView.OnTileSelectedListener() {
                    @Override
                    public void onTileSelected(ImagePickerSheetView.ImagePickerTile selectedTile) {
                        bottomSheetLayout.dismissSheet();
                        if (selectedTile.isImageTile()) {
                            showSelectedImage(selectedTile.getImageUri());
                        } else {
                            genericError(null);
                        }
                    }
                })
                .setTitle("Choose an image...")
                .create();

        bottomSheetLayout.showWithSheetView(sheetView);
    }

    private void showSelectedImage(Uri selectedImageUri) {
        selectedImage.setImageDrawable(null);
        Glide.with(this)
                .load(selectedImageUri)
                .crossFade()
                .fitCenter()
                .into(selectedImage);
        convertImage(selectedImageUri);
        //Log.e("File size ", "is " + image.length);
    }

    private void genericError(String message) {
        Toast.makeText(this, message == null ? "Something went wrong." : message, Toast.LENGTH_SHORT).show();
    }

    private void convertImage(Uri imageUri) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bmpSample = BitmapFactory.decodeFile(imageUri.getPath(), options);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bmpSample.compress(Bitmap.CompressFormat.JPEG, 100, out);
            image = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submit() {
        String userName = nameET.getText().toString();
        String organ = organET.getText().toString();
        String type = typeET.getText().toString();

        ParseObject toSubmitObj = new ParseObject("DonationSites");
        ParseFile submittedImage = new ParseFile(userName+organ+type + ".jpeg", image);
        ParseGeoPoint point = new ParseGeoPoint();

        if (mLocation == null) {
            point.setLatitude(40.6944238);
            point.setLongitude(-73.9887725);
        }
        else {
            point.setLatitude(mLocation.getLatitude());
            point.setLongitude(mLocation.getLongitude());
        }

        toSubmitObj.put("submittedUserName", userName);
        toSubmitObj.put("siteOrganization", organ);
        toSubmitObj.put("siteCategory", type);
        toSubmitObj.add("siteCategoryTest", switchCatStr(type));
        toSubmitObj.put("siteImage", submittedImage);
        toSubmitObj.put("siteLatLong", point);
        toSubmitObj.put("flagged", true);

        toSubmitObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(getBaseContext(), "Thank you for your submission!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UploadSiteActivity.this, MainActivity.class));
            }
        });




    }
}
