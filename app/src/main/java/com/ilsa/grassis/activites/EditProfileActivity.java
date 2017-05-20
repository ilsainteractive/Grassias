package com.ilsa.grassis.activites;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
        setFonts();
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

                UpdateUser();
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

    private void setFonts() {

        /*name.setTypeface(Typefaces.get(this, Typefaces.OPENSANS_RAGULAR));
        lastName.setTypeface(Typefaces.get(this, Typefaces.OPENSANS_RAGULAR));
        emailAddress.setTypeface(Typefaces.get(this, Typefaces.OPENSANS_RAGULAR));
        phoneNumber.setTypeface(Typefaces.get(this, Typefaces.OPENSANS_RAGULAR));
        connectWithFb.setTypeface(Typefaces.get(this, Typefaces.OPENSANS_BOLD));
        saveButton.setTypeface(Typefaces.get(this, Typefaces.OPENSANS_BOLD));*/
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

      /*  final ProgressDialog pd = new ProgressDialog(EditProfileActivity.this);
        pd.setMessage("Please Wait");
        pd.show();

        String m = "0";
        if (month.equalsIgnoreCase("January"))
            m = "1";
        else if (month.equalsIgnoreCase("February"))
            m = "2";
        else if (month.equalsIgnoreCase("March"))
            m = "3";
        else if (month.equalsIgnoreCase("April"))
            m = "4";
        else if (month.equalsIgnoreCase("May"))
            m = "5";
        else if (month.equalsIgnoreCase("June"))
            m = "6";
        else if (month.equalsIgnoreCase("July"))
            m = "7";
        else if (month.equalsIgnoreCase("September"))
            m = "8";
        else if (month.equalsIgnoreCase("October"))
            m = "9";
        else if (month.equalsIgnoreCase("November"))
            m = "10";
        else if (month.equalsIgnoreCase("December"))
            m = "11";
        else if (month.equalsIgnoreCase("January"))
            m = "12";

        final String dob = year + "-" + m + "-" + day;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.BASE_URL + "updateProfile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.get("success").toString().equalsIgnoreCase("true")) {

                                Gson gson = new GsonBuilder().create();
                                UserVo userVo = gson.fromJson(jsonObject.getJSONObject("data").getJSONObject("User").toString(), UserVo.class);
                                SharedPreferenceHelper.setSharedPreferenceString(mContext, Constants.USER_TOKEN, userVo.getUser_token());
                                SharedPreferenceHelper.setSharedPreferenceString(mContext, Constants.USER_FIRST_NAME, userVo.getFirstName());
                                SharedPreferenceHelper.setSharedPreferenceString(mContext, Constants.USER_LAST_NAME, userVo.getLastName());
                                SharedPreferenceHelper.setSharedPreferenceString(mContext, Constants.USER_EMAIL, userVo.getEmail());
                                SharedPreferenceHelper.setSharedPreferenceString(mContext, Constants.USER_PICTURE, userVo.getPicture());
                                SharedPreferenceHelper.setSharedPreferenceString(mContext, Constants.USER_ID, userVo.getId());

                                if (!userVo.getDob_date().equalsIgnoreCase(""))
                                    SharedPreferenceHelper.setSharedPreferenceInt(ProfileActivity.this, Constants.USER_DAY, Integer.parseInt(userVo.getDob_date()));
                                if (!userVo.getDob_month().equalsIgnoreCase(""))
                                    SharedPreferenceHelper.setSharedPreferenceInt(ProfileActivity.this, Constants.USER_MONTH, Integer.parseInt(userVo.getDob_month()));
                                if (!userVo.getDob_year().equalsIgnoreCase(""))
                                    SharedPreferenceHelper.setSharedPreferenceInt(ProfileActivity.this, Constants.USER_YEAR, Integer.parseInt(userVo.getDob_year()));
                                if (!userVo.getDob().equalsIgnoreCase(""))
                                    SharedPreferenceHelper.setSharedPreferenceString(mContext, Constants.USER_DOB, userVo.getDob());
                                if (!userVo.getGender().equalsIgnoreCase(""))
                                    SharedPreferenceHelper.setSharedPreferenceString(mContext, Constants.USER_GENDER, userVo.getGender());

                                AppController.IsUserUpdated = true;
                                Toast.makeText(mContext, jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(mContext, jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException ignored) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        AppController.IsUserUpdated = false;
                        Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("data[api_key]", Constants.API_KEY);
                params.put("data[User][user_token]", SharedPreferenceHelper.getSharedPreferenceString(ProfileActivity.this, "USER_TOKEN", null));
                params.put("data[User][firstName]", nameString);
                params.put("data[User][lastName]", lastnameString);
                params.put("data[User][email]", emailString);
                params.put("data[User][dob]", dob);
                params.put("data[User][gender]", genderstring);

                if (is_Image_picked)
                    params.put("picture", imageString);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/
    }


}
