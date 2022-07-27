package com.inovex.bikroyik.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.AppUtil;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.Multipart.NetworkClient;
import com.inovex.bikroyik.Multipart.UploadApis;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.Expense;
import com.inovex.bikroyik.adapter.ExpenseListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;
import static com.inovex.bikroyik.activity.LoginActivity.DIST_ID;
import static com.inovex.bikroyik.activity.SplashActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.SplashActivity.USER_ID;
import static com.inovex.bikroyik.activity.SplashActivity.expenseType;


/**
 * Created by HP on 8/15/2018.
 */


public class ExpensesFragment extends Fragment {


    private List<Expense> expenseList = new ArrayList<>();
    RecyclerView expenseRecyclerView;
    ExpenseListAdapter expenseListAdapter;
    //TextView tvAddTaskList;
    FloatingActionButton fabAddExpense;
    LinearLayout lllSyncExpenses;
    ProgressDialog progressDialog;
    Context mContext;
    String expense_type_string = "",imageName="";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    File image = null;
    Uri uri;
    ArrayList<String> imagePathList = new ArrayList<String>();
    ImageView expense_image;
    ArrayList<MultipartBody.Part> multipartBodyList = new ArrayList<>();
    String date = "";
    SharedPreferences sharedPreferences;
    String empId, distId;
    Bitmap bitmap;




    public ExpensesFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        onBackPressed(view);
        mContext = getContext();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait.....");


        //define size of the layout
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;



        expenseRecyclerView = (RecyclerView) view.findViewById(R.id.expenseList_recycler);
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        empId = sharedPreferences.getString(USER_ID,null);
        distId = sharedPreferences.getString(DIST_ID,null);

        expenseListAdapter = new ExpenseListAdapter(expenseList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        expenseRecyclerView.setLayoutManager(mLayoutManager);
        expenseRecyclerView.setItemAnimator(new DefaultItemAnimator());
        expenseRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        expenseRecyclerView.setAdapter(expenseListAdapter);

        // tvAddTaskList = view.findViewById(R.id.tvAddTaskList);
        lllSyncExpenses = view.findViewById(R.id.llSyncExpenseList);
        fabAddExpense = view.findViewById(R.id.fabAddExpense);

        //callExpenseAPI();

        prepareExpenseListData();


        lllSyncExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                callTimerThread("expense sync successful");


            }
        });

        fabAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final TextView tvAddExpenseDate;
                final EditText etExpenseAmount;
                TextView tvCancelNewExpenseDialog;
                final TextView tvDoneAddExpenseDialog;
                Spinner expense_type;
                final EditText etExpenseDetails;
                final int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);



                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_expense_dialog);
                tvAddExpenseDate = dialog.findViewById(R.id.tvAddExpenseSelectDate);
                etExpenseDetails = dialog.findViewById(R.id.etExpenseDetails);
                tvCancelNewExpenseDialog = dialog.findViewById(R.id.tvCancelNewTaskDialog);
                tvDoneAddExpenseDialog = dialog.findViewById(R.id.tvDoneAddExpenseDialog);
                etExpenseAmount = dialog.findViewById(R.id.etExpenseAmount);
                expense_type = dialog.findViewById(R.id.expense_type_spinner);
                expense_image = dialog.findViewById(R.id.add_expense_image);


                dialog.getWindow().setLayout((int)(width*0.95), (int)(height*0.7));


                ArrayList<String> expense_type_list = expenseType;
                Log.d("workforce", "onClick: "+expense_type_list);

                ArrayAdapter <String> expense_type_adapter = new ArrayAdapter<String>(getContext(),
                        R.layout.spinner_layout,expenseType);
                expense_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                expense_type.setAdapter(expense_type_adapter);

                expense_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        if(position != 0){

                            expense_type_string = expenseType.get(position);

                        }
                        else expense_type_string = "";
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        expense_type_string = "";
                    }
                });

                expense_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        captureImage();
                    }
                });



                tvCancelNewExpenseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                tvDoneAddExpenseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String expenseAmount = etExpenseAmount.getText().toString();
                        String expenseDetails = etExpenseDetails.getText().toString();
                        if (expenseAmount.length() > 0  && expenseDetails.length() > 0 && expense_type_string.length() > 0 && date.length() > 0 && bitmap != null) {

                            Long tsLong = System.currentTimeMillis() / 1000;
                            String ts = tsLong.toString();
                            String status = "pending";
                            if (AppUtil.isNetworkAvailable(getContext())) {
                            } else {
                                status = "in queue";
                            }

                            /*new AppDatabaseHelper(getContext()).insertExpenseData(expense_type_string,expenseAmount,expenseDetails,empId,status,"0",imageName,date);
                            Expense expense = new Expense(expense_type_string,date,expenseDetails,expenseAmount,"0",status);
                            expenseList.add(expense);
                            expenseListAdapter.notifyDataSetChanged();*/
                            sendImage(dialog, expense_type_string,date,expenseDetails,expenseAmount,"0",status);
                            dialog.dismiss();

                        } else {
                            tvDoneAddExpenseDialog.setError("Please fill everything...");
                        }
                    }
                });

                tvAddExpenseDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                date = dayOfMonth + "-" + (month + 1) + "-" + year;
                                tvAddExpenseDate.setText("Date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);

                        datePickerDialog.show();

                    }
                });

                final Window window = dialog.getWindow();
                window.setLayout((int)(width*0.95), (int) (height*0.85));
                dialog.show();

            }
        });
        return view;
    }

    private void captureImage() {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageName = "JPEG_"+timeStamp+"_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
        }


    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    private void changeAllExpenseListStatus() {
        for (int x = 0; x < expenseList.size(); x++) {
            Expense expense = expenseList.get(x);
            expense.setExpenseStatus("sent");
        }
        expenseListAdapter.notifyDataSetChanged();

    }

    private void prepareExpenseListData() {



        ArrayList<HashMap<String, String>> expenses = new AppDatabaseHelper(getContext()).getExpanseData();
        Log.d("workforce_expanse", "prepareExpenseListData: "+expenses);

        for(int i = 0; i < expenses.size(); i++){
            String type = expenses.get(i).get(AppDatabaseHelper.COLUMN_EXPENSE_TYPE);
            String date = expenses.get(i).get(AppDatabaseHelper.COLUMN_EXPENSE_DATE);
            String amount = expenses.get(i).get(AppDatabaseHelper.COLUMN_EXPENSE_AMOUNT);
            String approved = expenses.get(i).get(AppDatabaseHelper.COLUMN_EXPENSE_APPROVED_AMOUNT);
            String status = expenses.get(i).get(AppDatabaseHelper.COLUMN_EXPENSE_STATUS);
            String note = expenses.get(i).get(AppDatabaseHelper.COLUMN_EXPENSE_NOTE);

            Expense expense = new Expense(type,date,note,amount,approved,status);
            expenseList.add(expense);

        }

        expenseListAdapter.notifyDataSetChanged();
    }


    private void callTimerThread(final String message) {


        final int interval = 3000; // 3 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                changeAllExpenseListStatus();
                progressDialog.dismiss();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            //uri = data.getData();

            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap = imageBitmap;
            expense_image.setImageBitmap(imageBitmap);
            uri = getImageUri(getContext(), imageBitmap);



        }
    }

    private Uri getImageUri(Context applicationContext, Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), imageBitmap, imageName, null);
        return Uri.parse(path);
    }

    private void sendImage(final Dialog dialog, final String expense_type_string, final String date, final String expenseDetails, final String expenseAmount, final String approved, final String status) {
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
            public void onResponse(Call call, retrofit2.Response response) {
                Log.d("workforce", "onResponse: "+ response);

                saveDataByAPI(dialog,expense_type_string,expenseAmount,expenseDetails,empId,status,approved,imageName,date);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("workforce", "onResponse: "+ t);
            }
        });


    }

    private void saveDataByAPI(final Dialog dialog, final String expense_type_string, final String expenseAmount,
                               final String expenseDetails, final String empId, final String status, final String approved, final String imageName, final String date) {

        String URL = APIConstants.EXPENSE_SAVE_API+distId;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("workforce", "onResponse: "+response);

                new AppDatabaseHelper(getContext()).insertExpenseData(expense_type_string,expenseAmount,expenseDetails,empId,status,approved,imageName,date);
                Expense expense = new Expense(expense_type_string,date,expenseDetails,expenseAmount,approved,status);
                expenseList.add(expense);
                expenseListAdapter.notifyDataSetChanged();


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_AMOUNT,expenseAmount);
                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_APPROVED_AMOUNT,approved);
                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_BY,empId);
                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_NOTE,expenseDetails);
                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_ATTACHMENT,imageName);
                    jsonObject.put(APIConstants.EXPENSE_SAVE_API_EXPENSE_TYPE,expense_type_string);

                    body = jsonObject.toString();
                    Log.d("workforce_expanse", "getBody: "+body);

                }catch (JSONException e){
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

    }


    private void onBackPressed(View view){

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("_back_", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("_back_", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    SessionManager sessionManager = new SessionManager(getContext());
                    Fragment fragment = null;
                    if (sessionManager.getEmployeeCategory().equals("SR")){
                        fragment = new HomeFragmentSR();

                    }else if (sessionManager.getEmployeeCategory().equalsIgnoreCase("DE")){
                        fragment = new HomeFragmentSR();
                    }
                    Constants.moveHomeFragment(fragment, getActivity(), getContext());
                    return true;
                }
                return false;
            }
        });
    }
}

