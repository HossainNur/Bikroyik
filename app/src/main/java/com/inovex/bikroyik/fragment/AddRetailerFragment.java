package com.inovex.bikroyik.fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.DeviceLocation;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.Multipart.NetworkClient;
import com.inovex.bikroyik.Multipart.UploadApis;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.MainActivity;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.services.GPSTracker;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.inovex.bikroyik.activity.MainActivity.retailerType;
import static com.inovex.bikroyik.activity.SplashActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.SplashActivity.USER_ID;


public class AddRetailerFragment extends Fragment {


    public GPSTracker gps;
    public Button location/*, captureImage*/,submit;
    public EditText retailer_location, store_name,retail_owner, retail_address, phone_no, national_id;
    public ImageView imageView;
    public String latitude = null, longitude = null, imageString;
    public byte[] byteArray;
    public AppDatabaseHelper appDatabaseHelper;
    ArrayList<String> market_name = new ArrayList<String>();
    ArrayList<String> imagePathList = new ArrayList<String>();
    ArrayList<HashMap<String, String>> retailerList = new ArrayList<>();
    public static  ArrayList<HashMap<String, String>> addedRetailerList = new ArrayList<HashMap<String, String>>();
    public static  ArrayList< String> addedRetailerListCopy = new ArrayList<>();

    ArrayList<MultipartBody.Part> multipartBodyList = new ArrayList<>();
    private ProgressDialog progressDialog;
    String password, employeeId;
    Spinner retailer_type_spinner;
    JSONArray items;


    HashMap<String, String> marketDetails = new HashMap<String, String>();
    String distributor_id;
    String marketID = null;
    String marketName = null;
    String distributorName, imageName,employeeID;
    File image = null;

    Uri uri;
    String retailer_type = "";


    private DatabaseSQLite sqLite;

    Context mContext;
    public MaterialSpinner market_id;

    //keep track of camera capture intent
    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        employeeID = sharedPreferences.getString(USER_ID,null);
        //Log.d("workforce_add_retail", "onCreate: "+employeeID);

        SessionManager sessionManager = new SessionManager(getContext());



        if(appDatabaseHelper == null){
            appDatabaseHelper = new AppDatabaseHelper(mContext);
        }
        gps = new GPSTracker(mContext);
        retailerList = appDatabaseHelper.getRetailerData();
        distributor_id = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_DISTRIBUTOR_ID);
        distributorName = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_DISTRIBUTOR_NAME);
        getMarketDetails();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_retailer, container, false);

        sqLite = new DatabaseSQLite(getContext());

        location = view.findViewById(R.id.btn_get_location);
        retailer_location = view.findViewById(R.id.add_retailer_location);
        imageView = view.findViewById(R.id.retailer_image);
        submit = view.findViewById(R.id.submit_new_retailer);
        store_name = view.findViewById(R.id.tv_store_name);
        retail_owner = view.findViewById(R.id.retail_owner);
        retail_address = view.findViewById(R.id.retail_address);
        phone_no = view.findViewById(R.id.phone_no);
        national_id = view.findViewById(R.id.national_id);
        market_id = view.findViewById(R.id.market_id);
        retailer_type_spinner = view.findViewById(R.id.retailer_type_spinner);

        /* Spinner*/

        ArrayAdapter spinner = new ArrayAdapter(mContext, R.layout.spinner_layout, market_name);
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        market_id.setAdapter(spinner);
        market_id.setDropdownMaxHeight(450);

        ArrayAdapter <String> retailer_type_adapter = new ArrayAdapter<String>(mContext,
                R.layout.spinner_layout,retailerType);
        retailer_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retailer_type_spinner.setAdapter(retailer_type_adapter);
        retailer_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0) {
                    retailer_type = retailerType.get(position);
                } else{
                    retailer_type = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        market_id.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position == 0) {
                    marketID = null;
                }
                else {
                    marketName = market_name.get(position);
                    marketID = marketDetails.get(marketName);
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               GetLocation(getContext());

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                addRetailer();

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageName = "JPEG_"+timeStamp+"_";
            File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                image = File.createTempFile(
                        imageName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageName = image.getName();
            imagePathList.add(imageName);
            Log.d("workforce", "captureImage: "+imageName);
            imageView.setImageBitmap(imageBitmap);
            uri = getImageUri(mContext, imageBitmap);

        }
    }

    private void setUserInterface(String userId, String featureName, String featureDrawableName,
                                  boolean isInHomePage, int positionInHomepage){

        sqLite.insertFeatures(userId, featureName, featureDrawableName, isInHomePage, positionInHomepage);
    }

    private void addRetailer() {
        final String retailName = store_name.getText().toString().trim();
        final String  retailOwner = retail_owner.getText().toString().trim();
        final String  retailAddress = retail_address.getText().toString().trim();
        final String  phoneNo = phone_no.getText().toString().trim();
        final String  nationalId = national_id.getText().toString().trim();
        if (phoneNo.length() != 11){
            phone_no.setError("Please Enter Your 11 Digit Phone Number");
            progressDialog.dismiss();
        }else if (nationalId.length() < 5){
            phone_no.setError("Invalid");
            progressDialog.dismiss();
        }else if(marketID == null || longitude == null || latitude == null || retailer_type.length() < 1 || retailName.length() < 2 ||
                retailOwner.length() < 2 || retailAddress.length() < 2) {
            Log.d("workforce", "onResponse: "+ marketID+" "+longitude+" "+latitude);
            progressDialog.dismiss();
            Toast.makeText(mContext,"Please, fill all the blanks with proper data.", Toast.LENGTH_SHORT).show();
        } else {
            if (Constants.isNetworkAvailable(getContext())){
                sendImage(retailName,retailOwner, retailAddress, phoneNo,nationalId);
            }else {
                Toast.makeText(mContext,"No internet connection!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }

    }

    private void sendImage(final String retailName, final String retailOwner, final String retailAddress, final String phoneNo, final String nationalId) {
        if (uri != null){
            File file = new File(getRealPathFromURI(uri));
            RequestBody reqBody = RequestBody.create(MediaType.parse("image/*"),file);
            MultipartBody.Part partImage = MultipartBody.Part.createFormData("images",file.getName(),reqBody);
            multipartBodyList.add(partImage);

            RequestBody data = RequestBody.create(MediaType.parse("text/plain"),imageName);


            Retrofit retrofit = new NetworkClient().getRetrofit();

            UploadApis uploadApis = retrofit.create(UploadApis.class);
            Call call = uploadApis.uploadImage(partImage,data);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    Log.d("workforce", "onResponse: "+ response);
                    sendRetailerData(retailName,retailOwner, retailAddress, phoneNo,nationalId);
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("workforce", "onResponse: "+ t);

                }
            });
        }else {
            Toast.makeText(getContext(), "please take a photo!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }

    private void sendRetailerData(final String retailName, final String retailOwner, final String retailAddress, final String phoneNo, final String nationalId) {

        String URL = APIConstants.ADD_RETAILER_API;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.d("workforce_add_retail", "onResponse: "+response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Log.d("route response err", "route  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("route response", "route response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("route response", "route response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("route response", "route response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("route response", "route response: timeout error");
                }

                Log.d("route response", "route responseError:" + error.toString());
                error.printStackTrace();

            }
        }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                String body = null;
                try {
                    jsonObject.put(APIConstants.API_KEY_ADD_RETAILER_SUBMITTED_BY,employeeID);
                    Log.d("workforce", "getBody: "+jsonObject+" aaaa "+ employeeID);
                    jsonObject.put(APIConstants.API_KEY_RETAILER_NAME,retailName);
                    jsonObject.put(APIConstants.API_KEY_RETAILER_ADDRESS,retailAddress);
                    jsonObject.put(APIConstants.API_KEY_RETAILER_OWNER,retailOwner);
                    jsonObject.put(APIConstants.API_KEY_RETAILER_PHONE,phoneNo);
                    jsonObject.put(APIConstants.API_KEY_ADD_RETAILER_LAT,latitude);
                    jsonObject.put(APIConstants.API_KEY_ADD_RETAILER_LONG,longitude);
                    jsonObject.put(APIConstants.API_KEY_ADD_RETAILER_DISTRIBUTOR_ID,distributor_id);
                    jsonObject.put(APIConstants.API_KEY_ADD_RETAILER_NATIONAL_ID,nationalId);
                    jsonObject.put(APIConstants.API_KEY_ADD_RETAILER_MARKET_ID,marketID);
                    jsonObject.put("retailerType",retailer_type);
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put(APIConstants.API_KEY_RETAILER_FILE_NAME,imageName);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(jsonObject1);
                    jsonObject.put(APIConstants.API_KEY_ADD_RETAILER_IMAGE,jsonArray);


                    body = jsonObject.toString();
                    Log.d("workforce", "getBody: "+body);

                }catch (JSONException e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


                return body.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<String, String>();
                header.put("Content-Type", "application/json");
                return super.getHeaders();
            }
        };

        requestQueue.add(jsonObjectRequest);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(mContext,"Successfully Done", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra("activity", "re-write");
        startActivity(intent);
    }


    private Uri getImageUri(Context applicationContext, Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), imageBitmap, imageName, null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentUri) {
        if (contentUri != null){
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            cursor.close();
            return result;
        }
        return null;
    }

    private void captureImage() {




        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
        }



    }



    private void getMarketDetails() {

        for(int i = 0; i < retailerList.size(); i++) {
            String name = retailerList.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_NAME);
            String id = retailerList.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_ID);
            marketDetails.put(name,id);
        }
        market_name.add("--- Select Market Name ---");
        Iterator iterator = marketDetails.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry me2 = (Map.Entry) iterator.next();
            market_name.add(me2.getKey().toString());
        }
        Log.d("workforce_add_retail", "onCreate: "+market_name);



    }

    public void GetLocation(Context mContext) {

        String locationAddress;
        DeviceLocation deviceLocation = new DeviceLocation();
        Location location = deviceLocation.myCurrentLocation(mContext);

        if(location != null){
            Log.d("_arf_", "AddRetailerFragment --> location : "+location.toString());
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            locationAddress = latitude + ", "+ longitude;
            retailer_location.setText(locationAddress);
        }
    }
}