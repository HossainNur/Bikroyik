package com.inovex.bikroyik.UI.activity;

import static com.inovex.bikroyik.utils.Constants.CAMERA_REQUEST;
import static com.inovex.bikroyik.utils.Constants.MY_CAMERA_PERMISSION_CODE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.imageUpload.ApiServices;
import com.inovex.bikroyik.data.imageUpload.ResponseApiModel;
import com.inovex.bikroyik.data.imageUpload.RetroClient;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ContactModel;
import com.inovex.bikroyik.data.model.PhoneBookModel;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.utils.ApiConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewContact extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 101;
    private ImageView backButton, btn_imgContact, btn_moreInfoImg, profile_image;
    private TextView tv_toolbarText, compulsory_phone, compulsory_name;
    private EditText et_mobileNumber, et_name, et_email, et_address, et_note;
    private Button btn_save;
    private TextView btn_moreInformation;
    private ConstraintLayout view_moreInfo;
    Bitmap expense_image;

    ArrayList<String> imagePathList;

    private RadioGroup radioGroup;
    private RadioButton radioButton,rbCustomer,rbSupplier;

    String imageName = null;
    String currentPhotoPath = "";
    private TextView tvMobileNumber,tv_name,contact_text,
            tv_moreInformation,tv_email,tv_address,tv_note;

    ImageView btn_camera;
    private SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_new_contact);

        sharedPreference = SharedPreference.getInstance(getApplicationContext());

        tv_toolbarText = (TextView) findViewById(R.id.toolbar_title);
        //tv_toolbarText.setText(getResources().getString(R.string.new_contact));
        btn_imgContact = (ImageView) findViewById(R.id.btn_imgContact);
        et_mobileNumber = (EditText) findViewById(R.id.et_mobileNumber);
        et_name = (EditText) findViewById(R.id.et_name);
        btn_save = (Button) findViewById(R.id.save);
        btn_moreInformation = (TextView) findViewById(R.id.tv_moreInformation);
        btn_moreInfoImg = (ImageView) findViewById(R.id.down_arrow);
        view_moreInfo = (ConstraintLayout) findViewById(R.id.view_moreInfo);
        btn_camera = (CircleImageView) findViewById(R.id.btn_camera);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        et_address = (EditText) findViewById(R.id.et_address);
        et_email = (EditText) findViewById(R.id.et_email);
        et_note = (EditText) findViewById(R.id.et_note);
        compulsory_phone = (TextView) findViewById(R.id.compulsory_phone);
        compulsory_name = (TextView) findViewById(R.id.compulsory_name);

        tv_note = (TextView) findViewById(R.id.tv_note);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_moreInformation = (TextView) findViewById(R.id.tv_moreInformation);
        contact_text = (TextView) findViewById(R.id.contact_text);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tvMobileNumber = (TextView) findViewById(R.id.tvMobileNumber);
        rbCustomer = findViewById(R.id.radioCustomer);
        rbSupplier = findViewById(R.id.radioSupplier);




        //auto selected a radio button
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_typeOfContact);
        radioGroup.check(R.id.radioCustomer);

        imagePathList = new ArrayList<>();

        if(sharedPreference.getLanguage().equals("English"))
        {
            tvMobileNumber.setText("Mobile Number");
            tv_name.setText("Name");
            contact_text.setText("Please Select Type");
            tv_moreInformation.setText("More Information");
            tv_email.setText("Email");
            tv_address.setText("Address");
            tv_note.setText("Note");
            setTitle("New Contact");
            rbSupplier.setText("supplier");
            rbCustomer.setText("customer");
            tv_toolbarText.setText("New Contact");
            et_mobileNumber.setHint("Mobile Number");
            et_name.setHint("Name");
            et_address.setHint("Address");
            et_note.setHint("Note");
            et_email.setHint("Email");
            btn_save.setText("save");


        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                ContactModel contactModel = new ContactModel();
                boolean flag = false;

                if (!TextUtils.isEmpty(et_name.getText().toString())){
                    contactModel.setName(et_name.getText().toString());
                }else {
                    flag = true;
                    if (compulsory_name.getVisibility() == View.GONE){
                        compulsory_name.setVisibility(View.VISIBLE);
                    }
                }

                if (!TextUtils.isEmpty(et_mobileNumber.getText().toString())){
                    contactModel.setMobile(et_mobileNumber.getText().toString());
                }else {
                    flag = true;
                    if (compulsory_phone.getVisibility() == View.GONE){
                        compulsory_phone.setVisibility(View.VISIBLE);
                    }
                }

                // find the radiobutton by returned id
                if (!flag){

                    if (imageName !=null && currentPhotoPath != null){
                        contactModel.setImage(imageName);
                    }

                    if (!TextUtils.isEmpty(et_address.getText().toString())){
                        contactModel.setAddress(et_address.getText().toString());
                    }else {
                        contactModel.setAddress("");
                    }
                    if (!TextUtils.isEmpty(et_email.getText().toString())){
                        contactModel.setEmail(et_email.getText().toString());
                    }else {
                        contactModel.setEmail("");
                    }
                    if (!TextUtils.isEmpty(et_note.getText().toString())){
                        contactModel.setNote(et_note.getText().toString());
                    }else {
                        contactModel.setNote("");
                    }


                    radioButton = (RadioButton) findViewById(selectedId);
                    if (!TextUtils.isEmpty(radioButton.getText().toString())
                            && radioButton.getText().toString().equals("কাস্টমার")){
                        contactModel.setType("customer");
                    }else {
                        contactModel.setType("supplier");
                    }

                    contactModel.setStoreId(sharedPreference.getStoreId());
                    contactModel.setSubscriberId(sharedPreference.getSubscriberId());

//                    Toast.makeText(NewContact.this,
//                            radioButton.getText(), Toast.LENGTH_SHORT).show();

                    sendClientInfoToServer(getApplicationContext(), ApiConstants.CLIENT_CREATE, contactModel);

                    if (imagePathList != null){
                        imageUpload();
                    }

                }

            }
        });

        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_imgContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewContact.this, PhoneBookActivity.class));
            }
        });

        btn_moreInformation.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (view_moreInfo.getVisibility() == View.VISIBLE){
                    view_moreInfo.setVisibility(View.GONE);
                    btn_moreInfoImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down_24));
                }else {
                    view_moreInfo.setVisibility(View.VISIBLE);
                    btn_moreInfoImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up_24));
                }
            }
        });

        btn_moreInfoImg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (view_moreInfo.getVisibility() == View.VISIBLE){
                    view_moreInfo.setVisibility(View.GONE);
                    btn_moreInfoImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down_24));
                }else {
                    view_moreInfo.setVisibility(View.VISIBLE);
                    btn_moreInfoImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up_24));
                }
            }
        });


        if (getIntent().getSerializableExtra("value") != null){
            PhoneBookModel phoneBookModel = (PhoneBookModel) getIntent().getSerializableExtra("value");

            if (phoneBookModel != null){
                if (!TextUtils.isEmpty(phoneBookModel.getPhoneNumber())){
                    et_mobileNumber.setText(phoneBookModel.getPhoneNumber());
                }
                if (!TextUtils.isEmpty(phoneBookModel.getContactName())){
                    et_name.setText(phoneBookModel.getContactName());
                }

            }
        }


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(NewContact.this, btn_camera);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.photo_option_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.camera:
                                cameraAction();
                                return true;
                            case R.id.upload:
                                chooseImageFromGallery();
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                // Showing the popup menu
                popupMenu.show();

            }
        });

    }

    private void cameraAction(){
        int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    NewContact.this,
                    new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            dispatchTakePictureIntent();
        }
    }

    private void chooseImageFromGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d("image>>", "photoFile : "+photoFile);
              /*  Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.navigationdrawerfinal.fileprovider", // Over here
                        photoFile);*/

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.inovex.bikroyik.provider",
                        photoFile);
                //android.support.FILE_PROVIDER_PATHS
                Log.d("image>>", "PhotoUri : "+photoURI);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageName = image.getName();
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.d("image>>", "image Name : "+imageName);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            imagePathList.add(currentPhotoPath);
            setPic(profile_image);
        }

        else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                String result = null;
                if (imageUri.getScheme().equals("content")) {
                    Cursor cursor = getContentResolver().query(imageUri, null, null, null, null);
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            result = cursor.getString(cursor.getColumnIndexOrThrow((OpenableColumns.DISPLAY_NAME)));
                        }
                    } finally {
                        cursor.close();
                    }
                }
                if (result == null) {
                    result = imageUri.getPath();
                    int cut = result.lastIndexOf('/');
                    if (cut != -1) {
                        result = result.substring(cut + 1);
                    }
                }
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                imageName = imageFileName + result;

                currentPhotoPath = getRealPathFromURI(imageUri);
                Log.d("_tag_", "onActivityResult: "+" "+imageName);
                if (currentPhotoPath != null){
                    imagePathList.add(currentPhotoPath);
                    setPic(profile_image);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(NewContact.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void setPic(ImageView imgView) {
        // Get the dimensions of the View
        if(imgView != null) {
            int targetW = imgView.getWidth();
            int targetH = imgView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            if (targetW >0 && targetH >0){
                int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));
                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
                expense_image = bitmap;
                imgView.setImageBitmap(bitmap);
            }

        }
    }

    private String prepareJson(ContactModel contactModel){
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(contactModel);
    }

    private void imageUpload(){
        ArrayList<MultipartBody.Part> multipartBodyList = new ArrayList<>();


        if (imagePathList != null && imagePathList.size()>0){
//            for(String filePath : imagePathList){
//                File file = new File(filePath);
//                final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
//                partImage = MultipartBody.Part.createFormData("images", file.getName(),reqBody);
//                multipartBodyList.add(partImage);
//            }

            File file = new File(imagePathList.get(imagePathList.size()-1));
            final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageName,reqBody);
            RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "customer");

            ApiServices api = RetroClient.getApiServices();
            Call<ResponseApiModel> upload = api.uploadImage(type, partImage);
            upload.enqueue(new Callback<ResponseApiModel>() {
                @Override
                public void onResponse(Call<ResponseApiModel> call, Response<ResponseApiModel> response) {
                    Log.d("RETRO", "successfully uploaded photo!");
                    Log.d("RETRO", "ON RESPONSE  : " + response);
                    imagePathList.clear();
                }

                @Override
                public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                }
            });
        }

    }

    private void sendClientInfoToServer(Context context, String url, ContactModel contactModel){
        VolleyMethods volleyMethods = new VolleyMethods();
        String reqBody = prepareJson(contactModel);
        Log.d("_tag_", "json: "+reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJson(contactModel), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                Log.d("_tag_", "response: "+result);
                if (!TextUtils.isEmpty(result) && "error".equals(result)){
                    Log.d("_tag_", "response: "+result);
                }
            }
        });
    }
}