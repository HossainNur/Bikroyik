package com.inovex.bikroyik.UI.activity;

import static com.inovex.bikroyik.utils.Constants.CAMERA_REQUEST;
import static com.inovex.bikroyik.utils.Constants.MY_CAMERA_PERMISSION_CODE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.imageUpload.ApiServices;
import com.inovex.bikroyik.data.imageUpload.ResponseApiModel;
import com.inovex.bikroyik.data.imageUpload.RetroClient;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.DepositModel;
import com.inovex.bikroyik.data.model.DueModel;
import com.inovex.bikroyik.data.model.PaymentMethod;
import com.inovex.bikroyik.data.model.PaymentTypeForOrderJson;
import com.inovex.bikroyik.data.model.response_model.MobilePaymentModel;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.model.DuePaymentWay;
import com.inovex.bikroyik.utils.ApiConstants;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DueListActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 101;

    private EditText dueAmount, paidAmount, dueDescription, depositAmount;
    private TextView dueName, dueMobileNumber, textView, smapleText;
    private ImageView backButton;
    RelativeLayout depositLayout;
    LinearLayout dueLayout, dueTotalLayout, depositTotalLayout;
    private Spinner spinner;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, saveButton, dueBtnId, btnDeposit, radioBtnId;
    private ImageView expenseImage;
    private List<MobilePaymentModel> mobilePaymentModelList = new ArrayList<>();
    private PaymentTypeForOrderJson paymentTypeForOrderJson = new PaymentTypeForOrderJson();
    private List<String> mobileBankList = new ArrayList<>();
    private RadioGroup radioGroup, rg1;
    private RadioButton radioButton;
    ArrayList<String> imagePathList;

    String imageName = null;
    String currentPhotoPath = "";
    String name, mobileNumber, id;
    MaterialSpinner spinner_mobileBanking;
    VolleyMethods volleyMethods;
    private SharedPreference sharedPreference;
    SharedPreference sharedPreferenceClass;
    private RequestQueue mQueue;
    List<DuePaymentWay> due = new ArrayList<>();
    private static ProgressDialog mProgressDialog;

    private ArrayList<PaymentMethod> paymentMethods;
    private ArrayList<String> names = new ArrayList<String>();

    boolean dueFlag = true;
    boolean depositFlag = true;
    private TextView toolbar_title, classSelection, nameId, transaction,
            mobileNumberId, payAmount, dueAmountId, details, textViewId, depositText;
    private RadioButton radioCustomer, radioSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_due_list);

        ProgressDialog csprogress = new ProgressDialog(DueListActivity.this);

        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        /*context = getApplicationContext();
        //sendLoginRequestToServer(getApplicationContext());
        sharedPreferenceClass = SharedPreference.getInstance(context);*/

        depositLayout = (RelativeLayout) findViewById(R.id.depositLayout);

        dueLayout = (LinearLayout) findViewById(R.id.dueLayout);
        dueTotalLayout = (LinearLayout) findViewById(R.id.totalDueAmountId);
        depositTotalLayout = (LinearLayout) findViewById(R.id.depositTotalLayout);


        dueAmount = (EditText) findViewById(R.id.dueAmount);
        paidAmount = (EditText) findViewById(R.id.paidAmount);


        depositAmount = (EditText) findViewById(R.id.depositAmount);
        dueDescription = (EditText) findViewById(R.id.dueDescription);


        dueName = (TextView) findViewById(R.id.dueName);
        dueMobileNumber = (TextView) findViewById(R.id.dueMobileNumber);
        depositText = (TextView) findViewById(R.id.depositText);

        dueBtnId = (Button) findViewById(R.id.dueBtnId);
        btnDeposit = (Button) findViewById(R.id.btnDeposit);
        //spinner_mobileBanking = (MaterialSpinner) findViewById(R.id.spinner_mobileBanking_due);
        //smapleText = findViewById(R.id.smapleText);
        //radioBtnId = (Button) findViewById(R.id.radioId);
        spinner = findViewById(R.id.spinner3);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        expenseImage = (ImageView) findViewById(R.id.add_expense_image);
        saveButton = (Button) findViewById(R.id.savebtnId);
        imagePathList = new ArrayList<>();

        toolbar_title = findViewById(R.id.toolbar_title);
        classSelection = findViewById(R.id.classSelection);
        nameId = findViewById(R.id.nameId);
        mobileNumberId = findViewById(R.id.mobileNumber);
        payAmount = findViewById(R.id.payAmount);
        dueAmountId = findViewById(R.id.dueAmountId);
        details = findViewById(R.id.details);
        transaction = findViewById(R.id.transaction);
        textViewId = findViewById(R.id.textView);
        radioCustomer = findViewById(R.id.radioCustomer);
        radioSupplier = findViewById(R.id.radioSupplier);


        Intent i = getIntent();
        name = i.getStringExtra("sendName");
        mobileNumber = i.getStringExtra("sendNumber");
        id = i.getStringExtra("sendId");
        dueName.setText(name);
        dueMobileNumber.setText(mobileNumber);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        radioGroup.check(R.id.radioCustomer);

        //String radioValue = (String) radioButton.getText();
        //String spinnerText = spinner.getSelectedItem().toString();


        //RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroupId);
        //final String radioValue = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

        retrieveJSON();

        if (sharedPreference.getLanguage().equals("English")) {
            toolbar_title.setText("Due/Deposit");
            classSelection.setText("Select Class");
            radioCustomer.setText("customer");
            radioSupplier.setText("supplier");
            dueBtnId.setText("Due");
            btnDeposit.setText("Deposit");
            nameId.setText("Name");
            mobileNumberId.setText("Mobile Number");
            payAmount.setText("Pay Amount");
            dueAmountId.setText("Due Amount");
            depositText.setText("Deposit Amount");
            details.setText("Details");
            transaction.setText("Transaction Type");
            textViewId.setText("Select Date: ");
            saveButton.setText("Save");
            paidAmount.setHint("Pay Amount");
            dueAmount.setHint("Due Amount");
            depositAmount.setHint("Deposit Amount");
            dueDescription.setHint("Details");


        }


        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dueBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dueBtnId.setBackground(getDrawable(R.drawable.button_in_contact_list));
                dueBtnId.setTextColor(getResources().getColor(R.color.white));

                btnDeposit.setBackground(getDrawable(R.drawable.button_backgroud));
                btnDeposit.setTextColor(getResources().getColor(R.color.black));


                if (dueLayout.getVisibility() == View.GONE) {
                    dueLayout.setVisibility(View.VISIBLE);
                }
                if (dueTotalLayout.getVisibility() == View.GONE) {
                    dueTotalLayout.setVisibility(View.VISIBLE);
                }
                if (depositTotalLayout.getVisibility() == View.VISIBLE) {
                    depositTotalLayout.setVisibility(View.GONE);
                }

                dueFlag = false;

            }
        });

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnDeposit.setBackground(getDrawable(R.drawable.button_in_contact_list));
                btnDeposit.setTextColor(getResources().getColor(R.color.white));

                dueBtnId.setBackground(getDrawable(R.drawable.button_backgroud));
                dueBtnId.setTextColor(getResources().getColor(R.color.black));

                if (dueLayout.getVisibility() == View.VISIBLE) {
                    dueLayout.setVisibility(View.GONE);
                }

                if (dueTotalLayout.getVisibility() == View.VISIBLE) {
                    dueTotalLayout.setVisibility(View.GONE);
                }
                if (depositTotalLayout.getVisibility() == View.GONE) {
                    depositTotalLayout.setVisibility(View.VISIBLE);
                }


                depositFlag = false;

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!dueFlag) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    DueModel dueModel = new DueModel();
                    boolean flag = false;

                    if (!TextUtils.isEmpty(dueName.getText().toString())) {
                        dueModel.setName(dueName.getText().toString());
                    }

                    if (!TextUtils.isEmpty(dueMobileNumber.getText().toString())) {
                        dueModel.setMobile(dueMobileNumber.getText().toString());
                    }


                    // find the radiobutton by returned id
                    if (!flag) {

                        if (imageName != null && currentPhotoPath != null) {
                            dueModel.setImage(imageName);
                        }

                    /*if (!TextUtils.isEmpty(depositAmount.getText().toString())){
                        dueModel.setDeposit(Double.valueOf(depositAmount.getText().toString()));
                    }else {
                        dueModel.setDeposit(Double.valueOf(0));
                    }*/
                        if (!TextUtils.isEmpty(dueAmount.getText().toString())) {
                            dueModel.setDue_amount(Double.valueOf(dueAmount.getText().toString()));
                        } else {
                            dueModel.setDue_amount(Double.valueOf(0));
                        }
                        if (!TextUtils.isEmpty(paidAmount.getText().toString())) {
                            dueModel.setPaid_amount(Double.valueOf(paidAmount.getText().toString()));
                        } else {
                            dueModel.setPaid_amount(Double.valueOf(0));
                        }

                        if (!TextUtils.isEmpty(dateButton.getText().toString())) {
                            dueModel.setDepositDate(dateButton.getText().toString());
                        } else {
                            dueModel.setDepositDate("");
                        }
                        if (!TextUtils.isEmpty(dueDescription.getText().toString())) {
                            dueModel.setNote(dueDescription.getText().toString());
                        } else {
                            dueModel.setNote("");
                        }
                    /*if (!TextUtils.isEmpty(spinner.toString())){
                        dueModel.setMobile_bank(spinner.toString());
                    }else {
                        dueModel.setDescription("");
                    }*/


                        radioButton = (RadioButton) findViewById(selectedId);
                        if (!TextUtils.isEmpty(radioButton.getText().toString())
                                && radioButton.getText().toString().equals("কাস্টমার")) {
                            dueModel.setType("customer");
                        } else {
                            dueModel.setType("supplier");
                        }
                        String text = spinner.getSelectedItem().toString();
                        if (!TextUtils.isEmpty(text.toString())) {
                            dueModel.setPayment_method(text.toString());
                        } else {
                            dueModel.setPayment_method("");
                        }


                    /*if (!TextUtils.isEmpty(dueMobileNumber.getText().toString())){
                        dueModel.setMobile(dueMobileNumber.getText().toString());
                    }*/

                        dueModel.setStoreId(sharedPreference.getStoreId());
                        dueModel.setSubscriberId(sharedPreference.getSubscriberId());
                        dueModel.setUserId(sharedPreference.getUserId());
                        dueModel.setId(id);

                        Log.d("subscriber", sharedPreference.getSubscriberId());
                        Log.d("store", sharedPreference.getStoreId());

                        //depositModel.setId(sharedPreference.getId());

//                    Toast.makeText(NewContact.this,
//                            radioButton.getText(), Toast.LENGTH_SHORT).show();

                        sendClientInfoToServer(getApplicationContext(), ApiConstants.DUE_PAYMENT, dueModel);

                        if (imagePathList != null) {
                            imageUpload();
                        }

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


                ///deposit

                else if (!depositFlag) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    DepositModel depositModel = new DepositModel();
                    boolean flag = false;

                    /*if (!TextUtils.isEmpty(dueName.getText().toString())){
                        dueModel.setName(dueName.getText().toString());
                    }

                    if (!TextUtils.isEmpty(dueMobileNumber.getText().toString())){
                        dueModel.setMobile(dueMobileNumber.getText().toString());
                    }*/


                    // find the radiobutton by returned id
                    if (!flag) {

                        if (imageName != null && currentPhotoPath != null) {
                            depositModel.setImage(imageName);
                        }

                    /*if (!TextUtils.isEmpty(depositAmount.getText().toString())){
                        dueModel.setDeposit(Double.valueOf(depositAmount.getText().toString()));
                    }else {
                        dueModel.setDeposit(Double.valueOf(0));
                    }*/
                        if (!TextUtils.isEmpty(depositAmount.getText().toString())) {
                            depositModel.setDeposit(Double.valueOf(depositAmount.getText().toString()));
                        } else {
                            depositModel.setDeposit(Double.valueOf(0));
                        }
                       /* if (!TextUtils.isEmpty(paidAmount.getText().toString())){
                            dueModel.setPaid_amount(Double.valueOf(paidAmount.getText().toString()));
                        }else {
                            dueModel.setPaid_amount(Double.valueOf(0));
                        }*/

                        if (!TextUtils.isEmpty(dateButton.getText().toString())) {
                            depositModel.setDepositDate(dateButton.getText().toString());
                        } else {
                            depositModel.setDepositDate("");
                        }
                        if (!TextUtils.isEmpty(dueDescription.getText().toString())) {
                            depositModel.setNote(dueDescription.getText().toString());
                        } else {
                            depositModel.setNote("");
                        }
                    /*if (!TextUtils.isEmpty(spinner.toString())){
                        dueModel.setMobile_bank(spinner.toString());
                    }else {
                        dueModel.setDescription("");
                    }*/


                        radioButton = (RadioButton) findViewById(selectedId);
                        if (!TextUtils.isEmpty(radioButton.getText().toString())
                                && radioButton.getText().toString().equals("কাস্টমার")) {
                            depositModel.setType("customer");
                        } else {
                            depositModel.setType("supplier");
                        }
                        String text = spinner.getSelectedItem().toString();
                        if (!TextUtils.isEmpty(text.toString())) {
                            depositModel.setDeposit_type(text.toString());
                        } else {
                            depositModel.setDeposit_type("");
                        }


                    /*if (!TextUtils.isEmpty(dueMobileNumber.getText().toString())){
                        dueModel.setMobile(dueMobileNumber.getText().toString());
                    }*/

                        depositModel.setStoreId(sharedPreference.getStoreId());
                        depositModel.setSubscriberId(sharedPreference.getSubscriberId());
                        depositModel.setUserId(sharedPreference.getUserId());
                        depositModel.setId(id);

                        //depositModel.setId(sharedPreference.getId());

//                    Toast.makeText(NewContact.this,
//                            radioButton.getText(), Toast.LENGTH_SHORT).show();

                        sendClientInfoToServer2(getApplicationContext(), ApiConstants.DEPOSIT_PAYMENT, depositModel);

                        if (imagePathList != null) {
                            imageUpload();
                        }

                    }

                    csprogress.setMessage("Loading...");
                    csprogress.show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            csprogress.dismiss();
                        }
                    }, 1000);
                } else {
                    Toasty.warning(DueListActivity.this, "Please Enter Select Due or Deposit ", Toast.LENGTH_LONG).show();
                }


            }


        });


        expenseImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(DueListActivity.this, expenseImage);

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

    private void cameraAction() {
        int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    DueListActivity.this,
                    new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void chooseImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
            } else {
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
                Log.d("image>>", "photoFile : " + photoFile);
              /*  Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.navigationdrawerfinal.fileprovider", // Over here
                        photoFile);*/

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.inovex.bikroyik.provider",
                        photoFile);
                //android.support.FILE_PROVIDER_PATHS
                Log.d("image>>", "PhotoUri : " + photoURI);
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
        Log.d("image>>", "image Name : " + imageName);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            imagePathList.add(currentPhotoPath);
            setPic(expenseImage);
        } else if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                //these two line is important for show image by bitmap library
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                profile_image.setImageBitmap(selectedImage);


                currentPhotoPath = getRealPathFromURI(imageUri);
                if (currentPhotoPath != null) {
                    imagePathList.add(currentPhotoPath);
                    setPic(expenseImage);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(DueListActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void setPic(ImageView imgView) {
        // Get the dimensions of the View
        if (imgView != null) {
            int targetW = imgView.getWidth();
            int targetH = imgView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            if (targetW > 0 && targetH > 0) {
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

    private String prepareJson(DueModel dueModel) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(dueModel);
    }

    private String prepareJson2(DepositModel depositModel) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(depositModel);
    }

    private void imageUpload() {
        ArrayList<MultipartBody.Part> multipartBodyList = new ArrayList<>();


        if (imagePathList != null && imagePathList.size() > 0) {
//            for(String filePath : imagePathList){
//                File file = new File(filePath);
//                final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),file);
//                partImage = MultipartBody.Part.createFormData("images", file.getName(),reqBody);
//                multipartBodyList.add(partImage);
//            }

            File file = new File(imagePathList.get(0));
            final RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), file);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", file.getName(), reqBody);

            ApiServices api = RetroClient.getApiServices();
            RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "bill");
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

    private void sendClientInfoToServer(Context context, String url, DueModel dueModel) {
        VolleyMethods volleyMethods = new VolleyMethods();
        String reqBody = prepareJson(dueModel);
        Log.d("_tag_", "json: " + reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJson(dueModel), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_tag_", "response: " + result);

                Toasty.success(DueListActivity.this, "Due Payment added Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DueListActivity.this, BakirKhata.class);
                startActivity(intent);
                if (!TextUtils.isEmpty(result) && "error".equals(result)) {
                    Log.d("_tag_", "response: " + result);

                    Toast toast = Toast.makeText(DueListActivity.this, "Server Error", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                }

            }
        });
    }


    private void sendClientInfoToServer2(Context context, String url, DepositModel depositModel) {
        VolleyMethods volleyMethods = new VolleyMethods();
        String reqBody = prepareJson2(depositModel);
        Log.d("_tag_", "json: " + reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJson2(depositModel), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_tag_", "response: " + result);

                Toasty.success(DueListActivity.this, "Deposit added Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DueListActivity.this, BakirKhata.class);
                startActivity(intent);
                if (!TextUtils.isEmpty(result) && "error".equals(result)) {
                    Log.d("_tag_", "response: " + result);

                    Toast toast = Toast.makeText(DueListActivity.this, "Server Error", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                }

            }
        });
    }


    private void retrieveJSON() {

        String URLstring = ApiConstants.MOBILE_BANKING_PAYMENTS + sharedPreference.getSubscriberId();
        ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            JSONObject obj = new JSONObject(response);
                            if (obj.optString("status").equals("success")) {

                                paymentMethods = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    PaymentMethod paymentMethod = new PaymentMethod();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    paymentMethod.setPaymentType(dataobj.getString("paymentType"));

                                    paymentMethods.add(paymentMethod);

                                }

                                for (int i = 0; i < paymentMethods.size(); i++) {
                                    names.add(paymentMethods.get(i).getPaymentType().toString());

                                }

                                names.add("Cash");
                                names.add("Card");



                                /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.sample_view,R.id.sampleTextId, names);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                spinner.setAdapter(spinnerArrayAdapter);*/

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DueListActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                                spinner.setAdapter(adapter);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
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


    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

}



