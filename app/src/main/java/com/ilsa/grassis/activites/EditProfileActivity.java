package com.ilsa.grassis.activites;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.utils.Dailogs;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, lastName, userName, emailAddress, phoneNumber;
    private CircleImageView user_Image_Profile;
    private ImageView userEdit_Profile_Image;
    private Button connectWithFb, saveButton;
    private Spinner spinYear, spinDays, spinMonths, spingender;
    private static final int REQUEST_RUNTIME_PERMISSION = 123;
    private String image;
    private ArrayList<String> days, years, MyMonth, MyGender;
    private ArrayAdapter<String> adapterMonths, genderAdapter;

    private boolean is_Image_picked = false;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.editToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Title and subtitle
        toolbar.setTitle("Edit Profile");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setBackgroundColor(getResources().getColor(
                R.color.white));
        //toolbar.setNavigationIcon(R.mipmap.backarrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setSupportActionBar(toolbar);

        if ((CheckPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) && (CheckPermission(EditProfileActivity.this, Manifest.permission.CAMERA))) {
            // you have permission go ahead

        } else {
            RequestPermission(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_RUNTIME_PERMISSION);
        }

        InItComponent();
        AddListener();

        Glide.with(this).load(AppContoller.userData.getUser().getAvatar().getSmall())
                .asBitmap()
                .placeholder(R.mipmap.capture_img)
                .into(new BitmapImageViewTarget(user_Image_Profile));

        name.setText(AppContoller.userData.getUser().getFirst_name());
        lastName.setText(AppContoller.userData.getUser().getLast_name());
        userName.setText(AppContoller.userData.getUser().getUsername());
        emailAddress.setText(AppContoller.userData.getUser().getEmail());
        phoneNumber.setText(AppContoller.userData.getUser().getPhone_number());

    }

    private void InItComponent() {
        user_Image_Profile = (CircleImageView) findViewById(R.id.user_image_profile);
        userEdit_Profile_Image = (ImageView) findViewById(R.id.userEdit_Profile_Image);
        name = (EditText) findViewById(R.id.profile_Edt_Name);
        lastName = (EditText) findViewById(R.id.profile_Edt_LastName);
        userName = (EditText) findViewById(R.id.profile_Edt_UserName);
        emailAddress = (EditText) findViewById(R.id.profile_Edt_Email);
        phoneNumber = (EditText) findViewById(R.id.profile_Edt_PhoneNumber);
        saveButton = (Button) findViewById(R.id.activity_log_in_SaveButton);
    }

    private void AddListener() {
        userEdit_Profile_Image.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userEdit_Profile_Image:
                if (CheckPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    CropImage.startPickImageActivity(EditProfileActivity.this);
                } else {
                    RequestPermission(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_RUNTIME_PERMISSION);
                }
                break;
            case R.id.activity_log_in_SaveButton:

                Dailogs.ShowToast(mContext, "Service is not functional", Toast.LENGTH_SHORT);
                //UpdateUser();
                break;
        }
    }

    void UpdateUser() {
        if (!(name.getText().toString().equalsIgnoreCase(""))) {

            if (!(lastName.getText().toString().equalsIgnoreCase(""))) {

                if (!(emailAddress.getText().toString().equalsIgnoreCase(""))) {

                    UpdatedProfile(name.getText().toString(), lastName.getText().toString(), spinDays.getSelectedItem().toString(), spinMonths.getSelectedItem().toString(), spinYear.getSelectedItem().toString(), emailAddress.getText().toString(), spingender.getSelectedItem().toString(), image);
                } else
                    Toast.makeText(EditProfileActivity.this, "Email is Empty", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(EditProfileActivity.this, "Last Name is Empty", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(EditProfileActivity.this, "Name is Empty", Toast.LENGTH_SHORT).show();
    }

    private String convertToBase64(String imagePath) {

        Bitmap bm = resizeBitmap(imagePath, 512, 512);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {

        switch (permsRequestCode) {
            case REQUEST_RUNTIME_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // you have permission go ahead
                } else {
                    // you do not have permission show toast.
                }
            }
        }
    }

    public void RequestPermission(Activity thisActivity, String[] Permission, int Code) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Permission[0])
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Permission[0])) {
            } else {
                ActivityCompat.requestPermissions(thisActivity,
                        Permission,
                        Code);
            }
        }
    }

    public boolean CheckPermission(Context context, String Permission) {
        if (ContextCompat.checkSelfPermission(context,
                Permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            startCropImageActivity(imageUri);
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                user_Image_Profile.setImageURI(result.getUri());
                image = convertToBase64(result.getUri().getPath());
                is_Image_picked = true;

                Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }


        if (!(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) && (!(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE))) {
            //callbackManager.onActivityResult(requestCode, resultCode, data);
            is_Image_picked = true;
        }

    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    private void UpdatedProfile(final String nameString, final String lastnameString, final String day, final String month, final String year, final String emailString, final String genderstring, final String imageString) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
