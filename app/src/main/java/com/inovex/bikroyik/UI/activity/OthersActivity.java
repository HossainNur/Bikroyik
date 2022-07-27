package com.inovex.bikroyik.UI.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.R;

import java.util.Calendar;
//import static com.inovex.paiker.activity.LoginActivity.DIST_ID;
//import static com.inovex.paiker.activity.SplashActivity.MyPREFERENCES;
//import static com.inovex.paiker.activity.SplashActivity.USER_ID;
//import static com.inovex.paiker.activity.SplashActivity.expenseType;




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
import com.inovex.bikroyik.data.model.BillModel;
import com.inovex.bikroyik.data.model.BuyModel;
import com.inovex.bikroyik.data.model.ContactModel;
import com.inovex.bikroyik.data.model.OthersModel;
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
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.app.ProgressDialog;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class OthersActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 101;

    private EditText expenseAmount,expenseNote;
    private ImageView backButton;
    private TextView btn_moreInformation;
    private TextView expense_amount,expense_note,toolbar_title,expenseAmountText,expenseNoteText,dateText;
    private ConstraintLayout view_moreInfo;

    private DatePickerDialog datePickerDialog;
    private Button dateButton,saveButton;
    private ImageView expenseImage;

    ArrayList<String> imagePathList;

    String imageName = null;
    String currentPhotoPath = "";


    private SharedPreference sharedPreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_others);

        ProgressDialog csprogress=new ProgressDialog(OthersActivity.this);

        sharedPreference = SharedPreference.getInstance(getApplicationContext());

        expenseAmount = (EditText) findViewById(R.id.etExpenseAmount);
        expenseNote= (EditText) findViewById(R.id.etExpenseNote);

        expense_amount =(TextView) findViewById(R.id.expense_amount);
        expense_note = (TextView) findViewById(R.id.expense_note);


        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        expenseImage = (ImageView) findViewById(R.id.add_expense_image);
        saveButton = (Button) findViewById(R.id.savebtnId);
        imagePathList = new ArrayList<>();
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        expenseAmountText = (TextView) findViewById(R.id.expenseAmount);
        expenseNoteText = (TextView) findViewById(R.id.expenseNote);
        dateText = (TextView) findViewById(R.id.textView);

        if(sharedPreference.getLanguage().equals("English"))
        {
            toolbar_title.setText("Others");
            expenseAmountText.setText("Expense Amount");
            expenseNoteText.setText("Expense Note");
            dateText.setText("Select Date: ");
            saveButton.setText("Save");
            expenseAmount.setHint("Amount");
            expenseNote.setHint("For:");
            expense_amount.setText("Expense Amount is required");
            expense_note.setText("Expense Note is required");

        }
        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Expense.class);
                startActivity(intent);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OthersModel othersModel = new OthersModel();
                boolean flag = false;
                if (!TextUtils.isEmpty(expenseAmount.getText().toString())){
                    othersModel.setAmount(Integer.parseInt(expenseAmount.getText().toString()));
                }else {
                    flag = true;
                    if (expense_amount.getVisibility() == View.GONE){
                        expense_amount.setVisibility(View.VISIBLE);
                    }
                }

                if (!TextUtils.isEmpty(expenseNote.getText().toString())){
                    othersModel.setNote(expenseNote.getText().toString());
                }else {
                    flag = true;
                    if (expense_note.getVisibility() == View.GONE){
                        expense_note.setVisibility(View.VISIBLE);
                    }
                }


                if (!flag){

                    othersModel.setExpenseType("Others");

                    if (imageName !=null && currentPhotoPath != null){
                        othersModel.setImage(imageName);
                    }
                    if (!TextUtils.isEmpty(dateButton.getText().toString())){
                        othersModel.setDate(dateButton.getText().toString());
                    }else {
                        othersModel.setDate("");
                    }


                    othersModel.setStoreId(sharedPreference.getStoreId());
                    othersModel.setSubscriberId(sharedPreference.getSubscriberId());
                    othersModel.setSubmittedBy(sharedPreference.getUserId());



                    sendClientInfoToServer(getApplicationContext(), ApiConstants.EXPENSE, othersModel);

                    if (imagePathList != null){
                        imageUpload();
                    }
                    csprogress.setMessage("Loading...");
                    csprogress.show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            csprogress.dismiss();
                        }
                    }, 1000);



                }
            }

        });



        expenseImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(OthersActivity.this, expenseImage);

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
                    OthersActivity.this,
                    new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            dispatchTakePictureIntent();
        }
    }

    private void chooseImageFromGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        imageName = image.getName();
        Log.d("_tag_", "onActivityResult: "+currentPhotoPath);

        Log.d("_tag_", "onActivityResult: "+imageName);


        Log.d("image>>", "image Name : "+imageName);
        return image;
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            imagePathList.add(currentPhotoPath);
            setPic(expenseImage);
        }else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                String result = null;
                if (imageUri.getScheme().equals("content")) {
                    Cursor cursor = getContentResolver().query(imageUri, null, null, null, null);
                    try {
                        if (cursor != null && cursor.moveToFirst()) {
                            result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
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
                    setPic(expenseImage);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(OthersActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
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
                imgView.setImageBitmap(bitmap);
            }

        }
    }

    private String prepareJson(OthersModel othersModel){
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(othersModel);
    }

    private void imageUpload(){
        ArrayList<MultipartBody.Part> multipartBodyList = new ArrayList<>();
        Log.d("_tag_", "imageUpload: ");

        if (imagePathList != null && imagePathList.size()>0){

//            for(String filePath : imagePathList){
//                File file = new File(filePath);
//                final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
//                partImage = MultipartBody.Part.createFormData("images", file.getName(),reqBody);
//                multipartBodyList.add(partImage);
//            }
            File file = new File(imagePathList.get(0));

            RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "expense");
            final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
            Log.d("_tag_", "imageUpload: "+imageName);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageName,reqBody);
            Log.d("partimage","image"+partImage);


            ApiServices api = RetroClient.getApiServices();
            //ApiService api = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL).build().create(ApiService.class);
            Call<ResponseApiModel> upload = api.uploadImage(type,partImage);
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

    private void sendClientInfoToServer(Context context, String url, OthersModel othersModel){
        VolleyMethods volleyMethods = new VolleyMethods();
        String reqBody = prepareJson(othersModel);
        Log.d("_tag_", "json: "+reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJson(othersModel), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_tag_", "response: "+result);


                if (!TextUtils.isEmpty(result) && "error".equals(result)){
                    Log.d("_tag_", "response: "+result);

                    Toasty.success(OthersActivity.this, "Expense added Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OthersActivity.this, Expense.class);
                    startActivity(intent);

                }
                else{

                    Toast toast=Toasty.error(OthersActivity.this, result.toString(),Toast.LENGTH_SHORT);
                    toast.show();

                }

            }
        });
    }


    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }


    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

}