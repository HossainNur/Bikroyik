package com.inovex.bikroyik.UI.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Spinner;
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
import com.inovex.bikroyik.data.model.PaymentModel;
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

//import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.SimpleDateFormat;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormat;
import java.util.Calendar;
public class PaymentActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 101;
    private EditText expenseAmount,expenseNote,employeeList;
    private Button employeeBtn;
    private ImageView backButton;
    private TextView expense_amount,expense_note,employeeText,
            selectEmployee,toolbar_title,expenseAmountText,expenseNoteText,dateText;
    private DatePickerDialog datePickerDialog;
    private Button dateButton,saveButton,btnemployee;
    private ImageView expenseImage;
    ArrayList<String> imagePathList;
    String imageName = null;
    String currentPhotoPath = "";
    private Spinner spinner;
    String receive,name;



    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_payment);

        ProgressDialog csprogress=new ProgressDialog(PaymentActivity.this);


        sharedPreference = SharedPreference.getInstance(getApplicationContext());

        expenseAmount = (EditText) findViewById(R.id.etExpenseAmount);
        expenseNote= (EditText) findViewById(R.id.etExpenseNote);
        expense_amount =(TextView) findViewById(R.id.expense_amount);
        expense_note = (TextView) findViewById(R.id.expense_note);
        selectEmployee =(TextView) findViewById(R.id.employeeSelect);


       /* Intent i = getIntent();
        receive = i.getStringExtra("sendkey");
        selectEmployee.setText(receive);*/

        Intent i = getIntent();
        name = i.getStringExtra("sendName");
        selectEmployee.setText(name);

        btnemployee = (Button) findViewById(R.id.employeeBtn);
        btnemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(PaymentActivity.this, ContactListActivity.class);*/
                Intent intent = new Intent(PaymentActivity.this, EmployeeListActivity.class);
                startActivity(intent);
            }
        });

        /*spinner = findViewById(R.id.spinner3);
        String[] employee= {"Select One","Nur","Sani"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,employee);
        spinner.setAdapter(adapter);*/

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 1:
                        Intent intent = new Intent(PaymentActivity.this, ContactListActivity.class);
                        startActivity(intent);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/



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
        employeeText = (TextView) findViewById(R.id.employeeText);

        if(sharedPreference.getLanguage().equals("English"))
        {
            toolbar_title.setText("Salary");
            employeeText.setText("Employee");
            expenseAmountText.setText("Expense Amount");
            expenseNoteText.setText("Expense Note");
            dateText.setText("Select Date: ");
            saveButton.setText("Save");
            btnemployee.setText("Select Employee");
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

                PaymentModel paymentModel = new PaymentModel();
                boolean flag = false;

                if (!TextUtils.isEmpty(expenseAmount.getText().toString())){
                    paymentModel.setAmount(expenseAmount.getText().toString());
                }else {
                    flag = true;
                    if (expense_amount.getVisibility() == View.GONE){
                        expense_amount.setVisibility(View.VISIBLE);
                    }
                }

                if (!TextUtils.isEmpty(expenseNote.getText().toString())){
                    paymentModel.setNote(expenseNote.getText().toString());
                }else {
                    flag = true;
                    if (expense_note.getVisibility() == View.GONE){
                        expense_note.setVisibility(View.VISIBLE);
                    }
                }


                if (!flag){

                    paymentModel.setExpenseType("Salary");

                    if (imageName !=null && currentPhotoPath != null){
                        paymentModel.setImage(imageName);
                    }
                    if (!TextUtils.isEmpty(dateButton.getText().toString())){
                        paymentModel.setSalaryMonth(dateButton.getText().toString());
                    }else {
                        paymentModel.setSalaryMonth("");
                    }
                    if (!TextUtils.isEmpty(selectEmployee.getText().toString())){
                        paymentModel.setEmployeeName(selectEmployee.getText().toString());
                    }else {
                        paymentModel.setEmployeeName("");
                    }


                    paymentModel.setEmployeeId(sharedPreference.getUserId());
                    paymentModel.setSubscriberId(sharedPreference.getSubscriberId());
                    paymentModel.setStoreId(sharedPreference.getStoreId());


                    sendClientInfoToServer(getApplicationContext(), ApiConstants.SALARY, paymentModel);

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

                PopupMenu popupMenu = new PopupMenu(PaymentActivity.this, expenseImage);

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
                    PaymentActivity.this,
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

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            imagePathList.add(currentPhotoPath);
            setPic(expenseImage);
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
                Toast.makeText(PaymentActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
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

    private String prepareJson(PaymentModel paymentModel){
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(paymentModel);
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

            File file = new File(imagePathList.get(0));
            final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", file.getName(),reqBody);
            RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "salary");

            ApiServices api = RetroClient.getApiServices();
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

    private void sendClientInfoToServer(Context context, String url, PaymentModel paymentModel){
        VolleyMethods volleyMethods = new VolleyMethods();
        String reqBody = prepareJson(paymentModel);
        Log.d("_tag_", "json: "+reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJson(paymentModel), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_tag_", "response: "+result);


                if (!TextUtils.isEmpty(result) && "error".equals(result)){
                    Log.d("_tag_", "response: "+result);

                    Toasty.success(PaymentActivity.this,"Salary Created Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PaymentActivity.this, Expense.class);
                    startActivity(intent);


                }
                else {
                    Toasty.error(PaymentActivity.this,"Server Down",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   /* public void onDataSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        tvDate.setText(selectedDate);

    }*/
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