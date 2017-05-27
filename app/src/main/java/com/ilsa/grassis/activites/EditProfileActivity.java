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
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilsa.grassis.R;
import com.ilsa.grassis.library.AppContoller;
import com.ilsa.grassis.library.Constants;
import com.ilsa.grassis.rootvo.UserDataVO;
import com.ilsa.grassis.utils.Dailogs;
import com.ilsa.grassis.utils.Helper;
import com.ilsa.grassis.utils.ShPrefsHelper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mContext = this;
        mActivity = this;

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

                // Dailogs.ShowToast(mContext, "Service is not functional", Toast.LENGTH_SHORT);
                UpdateUser();
                break;
        }
    }

    private boolean IsFieldValid(Editable editable, int whoHasFocus) {
        switch (whoHasFocus) {
            case Constants.SIGNUP_FIRST_NAME:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            case Constants.SIGNUP_LAST_NAME:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            case Constants.SIGNUP_USER_NAME:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_USER_NAME_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_USER_NAME_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            case Constants.SIGNUP_EMAIL_VALIDATION:

                if (!editable.toString().equalsIgnoreCase("")) {

                    return emailValidator(editable.toString());
                } else {
                    return false;
                }
            case Constants.SIGNUP_PHONE_VALIDATION:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_PHONE_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_PHONE_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
            case Constants.SIGNUP_PASSWORD_VALIDATION:
                if (!editable.toString().equalsIgnoreCase("")) {
                    if (editable.length() > Constants.FIELD_PASSWORD_VALIDATION_LENGTH_MIN && editable.length() < Constants.FIELD_PASSWORD_VALIDATION_LENGTH_MAX)
                        return true;
                    else
                        return false;
                } else {
                    return false;
                }
        }
        return false;
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    void UpdateUser() {

        if (IsFieldValid(name.getEditableText(), Constants.SIGNUP_FIRST_NAME)) {
            if (IsFieldValid(lastName.getEditableText(), Constants.SIGNUP_LAST_NAME)) {
                if (IsFieldValid(userName.getEditableText(), Constants.SIGNUP_USER_NAME)) {
                    if (IsFieldValid(emailAddress.getEditableText(), Constants.SIGNUP_EMAIL_VALIDATION)) {
                        if (IsFieldValid(phoneNumber.getEditableText(), Constants.SIGNUP_PHONE_VALIDATION)) {
                            if (Helper.checkInternetConnection(mContext)) {
                                UpdatedProfile(name.getText().toString(), lastName.getText().toString(), userName.getText().toString(), emailAddress.getText().toString(), phoneNumber.getText().toString());
                            } else
                                Dailogs.ShowToast(mContext, getString(R.string.no_internet_msg), Constants.SHORT_TIME);
                        } else {
                            phoneNumber.setError("Length must be between 8 to 11 character");
                        }
                    } else {
                        emailAddress.setError(getString(R.string.invalid_email));
                    }
                } else {
                    userName.setError("Length must be between 5 to 14 character");
                }
            } else {
                lastName.setError("Length must be between 3 to 9 character");
            }
        } else {
            name.setError("Length must be between 3 to 9 character");
        }
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

                //  Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
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

    private void UpdatedProfile(String firstName, String lastName, String userName, String email, String phoneNumber) {

        final ProgressDialog pd = new ProgressDialog(EditProfileActivity.this);
        pd.setMessage("Updating...");
        pd.setCancelable(false);
        pd.show();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"user\": {\n    \"first_name\": \"" + firstName + "\",\n    \"last_name\": \"" + lastName + "\",\n    \"email\": \"" + email + "\",\n    \"phone_number\": \"" + phoneNumber + "\",\n    \"username\": \"" + userName + "\"\n  }\n}");
        Request request = new Request.Builder()
                .url("http://kushmarketing.herokuapp.com/api/users/me")
                .put(body)
                .addHeader("accept", "application/vnd.kush_marketing.com; version=1")
                .addHeader("authorization", "Bearer " + AppContoller.userData.getUser().getAccess_token())
                .addHeader("x-client-email", AppContoller.userData.getUser().getEmail())
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "595b4ebe-7a87-23b6-a62e-57da5bff19a1")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                pd.dismiss();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string().toString();
                Log.i("response", res);
                if (!response.isSuccessful()) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                pd.dismiss();
                                JSONObject jsonObject = new JSONObject(res);
                                JSONObject error = jsonObject.getJSONObject("error");
                                String message = error.get("message").toString();
                                Dailogs.ShowToast(mContext, message, Constants.LONG_TIME);
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    pd.dismiss();

                    Gson gson = new GsonBuilder().create();
                    UserDataVO userDatavo = gson.fromJson(res, UserDataVO.class);

                    if (userDatavo.getUser() != null) {
                        // AppContoller.IsLoggedIn = true;
                        // AppContoller.FavDispensariesIds = AppContoller.userData.getUser().getFavorites().getDispensaries();

                        userDatavo.getUser().setAccess_token(AppContoller.userData.getUser().getAccess_token());
                        userDatavo.getUser().setToken(AppContoller.userData.getUser().getToken());

                        AppContoller.userData = userDatavo;
                        ShPrefsHelper.setSharedPreferenceString(mContext, Constants.USER_VO, userDatavo.toString());
                        // ShPrefsHelper.setSharedPreferenceString(mContext, Constants.PASSWORD, password);
                        pd.dismiss();
                    }
                }
            }
        });
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
