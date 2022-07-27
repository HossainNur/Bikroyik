//package com.inovex.paiker.popup;
//
//import android.app.Activity;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.content.CursorLoader;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkError;
//import com.android.volley.ParseError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.ServerError;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.inovex.paiker.AppUtils.APIConstants;
//import com.inovex.paiker.AppUtils.AppDatabaseHelper;
//import com.inovex.paiker.AppUtils.AppUtil;
//import com.inovex.paiker.Multipart.NetworkClient;
//import com.inovex.paiker.Multipart.UploadApis;
//import com.inovex.paiker.R;
//import com.inovex.paiker.adapter.Expense;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Retrofit;
//
//import static com.inovex.paiker.activity.SplashActivity.expenseType;
//
//public class ExpenseDialogPopup extends Activity {
//    TextView tvAddExpenseDate, tvCancelNewExpenseDialog, tvDoneAddExpenseDialog;
//    EditText etExpenseDetails, etExpenseAmount;
//    ImageView expense_image;
//    Spinner expense_type;
//
//    int mYear, mMonth, mDay, mHour, mMinute;
//    String date = "";
//
//
//    ArrayList<MultipartBody.Part> multipartBodyList = new ArrayList<>();
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.add_expense_dialog);
//
//        final Calendar c = Calendar.getInstance();
//        mYear = c.get(Calendar.YEAR);
//        mMonth = c.get(Calendar.MONTH);
//        mDay = c.get(Calendar.DAY_OF_MONTH);
//
//        //define size of the layout
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//
//        int width = displayMetrics.widthPixels;
//        int height = displayMetrics.heightPixels;
//
//        getWindow().setLayout((int)(width*0.95), (int)(height*0.7));
//
//        tvAddExpenseDate =(TextView) findViewById(R.id.tvAddExpenseSelectDate);
//        etExpenseDetails =(EditText) findViewById(R.id.etExpenseDetails);
//        tvCancelNewExpenseDialog =(TextView) findViewById(R.id.tvCancelNewTaskDialog);
//        tvDoneAddExpenseDialog =(TextView) findViewById(R.id.tvDoneAddExpenseDialog);
//        etExpenseAmount =(EditText) findViewById(R.id.etExpenseAmount);
//        expense_type = findViewById(R.id.expense_type_spinner);
//        expense_image =(ImageView) findViewById(R.id.add_expense_image);
//
//
//
//        ArrayList<String> expense_type_list = expenseType;
//        Log.d("workforce", "onClick: "+expense_type_list);
//
//        ArrayAdapter<String> expense_type_adapter = new ArrayAdapter<String>(getContext(),
//                R.layout.spinner_layout,expenseType);
//        expense_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        expense_type.setAdapter(expense_type_adapter);
//
//        expense_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                if(position != 0){
//
//                    expense_type_string = expenseType.get(position);
//
//                }
//                else expense_type_string = "";
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                expense_type_string = "";
//            }
//        });
//
//        expense_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                captureImage();
//            }
//        });
//
//
//
//        tvCancelNewExpenseDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//        tvDoneAddExpenseDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String expenseAmount = etExpenseAmount.getText().toString();
//                String expenseDetails = etExpenseDetails.getText().toString();
//                if (expenseAmount.length() > 0  && expenseDetails.length() > 0 && expense_type_string.length() > 0 && date.length() > 0 && bitmap != null) {
//
//                    Long tsLong = System.currentTimeMillis() / 1000;
//                    String ts = tsLong.toString();
//                    String status = "pending";
//                    if (AppUtil.isNetworkAvailable(getContext())) {
//                    } else {
//                        status = "in queue";
//                    }
//
//                            /*new AppDatabaseHelper(getContext()).insertExpenseData(expense_type_string,expenseAmount,expenseDetails,empId,status,"0",imageName,date);
//                            Expense expense = new Expense(expense_type_string,date,expenseDetails,expenseAmount,"0",status);
//                            expenseList.add(expense);
//                            expenseListAdapter.notifyDataSetChanged();*/
//                    sendImage(dialog, expense_type_string,date,expenseDetails,expenseAmount,"0",status);
//
//                } else {
//                    tvDoneAddExpenseDialog.setError("Please fill everything...");
//                }
//            }
//        });
//
//        tvAddExpenseDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                        date = dayOfMonth + "-" + (month + 1) + "-" + year;
//                        tvAddExpenseDate.setText("Date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
//                    }
//                }, mYear, mMonth, mDay);
//
//                datePickerDialog.show();
//
//            }
//        });
//
//    }
//
//
//    private String getRealPathFromURI(Uri contentUri) {
//        if (contentUri != null){
//            String[] proj = {MediaStore.Images.Media.DATA};
//            CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
//            Cursor cursor = loader.loadInBackground();
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            String result = cursor.getString(column_index);
//            cursor.close();
//            return result;
//        }else {
//            Toast.makeText(getApplicationContext(), "Please Take a photo!", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//
//    }
//
//    private void sendImage(final String expense_type_string, final String date, final String expenseDetails, final String expenseAmount, final String approved, final String status) {
//        File file = new File(getRealPathFromURI(uri));
//        RequestBody reqBody = RequestBody.create(MediaType.parse("image/*"),file);
//        MultipartBody.Part partImage = MultipartBody.Part.createFormData("images",file.getName(),reqBody);
//        multipartBodyList.add(partImage);
//
//        RequestBody data = RequestBody.create(MediaType.parse("text/plain"),imageName);
//
//
//        Retrofit retrofit = new NetworkClient().getRetrofit();
//
//        UploadApis uploadApis = retrofit.create(UploadApis.class);
//        Call call = uploadApis.uploadImage(partImage,data);
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, retrofit2.Response response) {
//                Log.d("workforce", "onResponse: "+ response);
//
//                saveDataByAPI(dialog,expense_type_string,expenseAmount,expenseDetails,empId,status,approved,imageName,date);
//
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Log.d("workforce", "onResponse: "+ t);
//            }
//        });
//
//
//    }
//
//    private void saveDataByAPI(final String expense_type_string, final String expenseAmount,
//                               final String expenseDetails, final String empId, final String status, final String approved, final String imageName, final String date) {
//
//        String URL = APIConstants.EXPENSE_SAVE_API+distId;
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new com.android.volley.Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("workforce", "onResponse: "+response);
//
//                new AppDatabaseHelper(getApplicationContext()).insertExpenseData(expense_type_string,expenseAmount,expenseDetails,empId,status,approved,imageName,date);
//                Expense expense = new Expense(expense_type_string,date,expenseDetails,expenseAmount,approved,status);
//                expenseList.add(expense);
//                expenseListAdapter.notifyDataSetChanged();
//
//
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                if (error instanceof NetworkError) {
//                    Log.d("route response err", "route  response: Network error");
//                } else if (error instanceof ServerError) {
//                    Log.d("route response", "route response: Server error");
//                } else if (error instanceof AuthFailureError) {
//                    Log.d("route response", "route response: Auth. error");
//                } else if (error instanceof ParseError) {
//                    Log.d("route response", "route response: Parse error");
//                } else if (error instanceof TimeoutError) {
//                    Log.d("route response", "route response: timeout error");
//                }
//
//                Log.d("route response", "route responseError:" + error.toString());
//                error.printStackTrace();
//
//            }
//        }) {
//            @Override
//            public byte[] getBody() {
//                JSONObject jsonObject = new JSONObject();
//                String body = null;
//                try {
//                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_AMOUNT,expenseAmount);
//                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_APPROVED_AMOUNT,approved);
//                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_BY,empId);
//                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_NOTE,expenseDetails);
//                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_ATTACHMENT,imageName);
//                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_TYPE,expense_type_string);
//
//                    body = jsonObject.toString();
//                    Log.d("workforce_expanse", "getBody: "+body);
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//
//
//                return body.getBytes(StandardCharsets.UTF_8);
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new LinkedHashMap<String, String>();
//                header.put("Content-Type", "application/json");
//                return super.getHeaders();
//            }
//        };
//
//        requestQueue.add(jsonObjectRequest);
//
//    }
//
//
//}
