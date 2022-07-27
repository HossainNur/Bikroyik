package com.inovex.bikroyik.UI.activity;

import static android.media.CamcorderProfile.get;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.google.androidgamesdk.gametextinput.Listener;
import com.inovex.bikroyik.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.fragment.FragmentDrawer;
import com.inovex.bikroyik.UI.activity.Expense;
import com.inovex.bikroyik.utils.ApiConstants;

/**
 * Created by HP on 11/10/2018.
 */

public class Expense  extends AppCompatActivity{


    private CardView paymentCardView,othersCardView,rentCardView,billCardView,buyCardView;
    private Context context;
    SharedPreference sharedPreferenceClass;
    private ImageView backButton;
    private TextView date,totalExpenseAmount,rentValue,rentType,purchaseValue,purchaseType,billValue,billType,salaryType,salaryValue,othersType,othersValue;
    private RequestQueue mQueue;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat month_date = new SimpleDateFormat("dd - MMMM - yyyy");
    SimpleDateFormat expense = new SimpleDateFormat("yyyy-MM-dd");
    String month=month_date.format(cal.getTime());
    String expense_date =expense.format(cal.getTime());
    private SharedPreference sharedPreference;
    private TextView toolbar_title,totalExpenseValue,expensesList,textViewId,
                    salaryTextview,purchaseTextview,billTextview,rentTextView,othersTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_expense);

        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        toolbar_title = findViewById(R.id.toolbar_title);
        totalExpenseValue = findViewById(R.id.totalExpenseValue);
        expensesList = findViewById(R.id.expensesList);
        textViewId = findViewById(R.id.textviewId);
        othersTextview = findViewById(R.id.othersTextview);
        rentTextView = findViewById(R.id.rentTextView);
        billTextview = findViewById(R.id.billTextview);
        purchaseTextview = findViewById(R.id.purchaseTextview);
        salaryTextview = findViewById(R.id.salaryTextview);
        rentType = findViewById(R.id.textViewRentType);
        purchaseType = findViewById(R.id.purchaseTypeId);
        billType = findViewById(R.id.billType);
        salaryType = findViewById(R.id.expenseTv);
        othersType = findViewById(R.id.textViewOthersType);

        if(sharedPreference.getLanguage().equals("English"))
        {
            toolbar_title.setText("Expense");
            totalExpenseValue.setText("Total Expense");
            expensesList.setText("Expenses");
            textViewId.setText("Add Expense");
            salaryTextview.setText("Salary");
            purchaseTextview.setText("Purchase");
            rentTextView.setText("Rent");
            othersTextview.setText("Others");
            billTextview.setText("Bill");
            othersType.setText("Others");
            salaryType.setText("Salary");
            billType.setText("Bill");
            purchaseType.setText("Purchase");
            rentType.setText("Rent");
        }

        totalExpenseAmount = (TextView) findViewById(R.id.totalExpenseAmount);
        buyCardView = findViewById(R.id.buyCardViewId);
        billCardView = findViewById(R.id.billCardViewId);
        rentCardView = findViewById(R.id.rentCardViewId);
        othersCardView = findViewById(R.id.othersCardViewId);
        paymentCardView = findViewById(R.id.paymentCardViewId);
        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        date = (TextView) findViewById(R.id.dateId);
        sharedPreferenceClass = SharedPreference.getInstance(context);
        rentType = (TextView) findViewById(R.id.textViewRentType);
        rentValue = (TextView) findViewById(R.id.textViewRentValue);
        purchaseType =(TextView) findViewById(R.id.purchaseTypeId);
        purchaseValue =(TextView) findViewById(R.id.purchaseValueId);
        billType = (TextView) findViewById(R.id.billType);
        billValue = (TextView) findViewById(R.id.billAmount);
        salaryType = (TextView) findViewById(R.id.salaryType);
        salaryValue = (TextView) findViewById(R.id.salaryAmount);
        othersType = (TextView) findViewById(R.id.textViewOthersType);
        othersValue = (TextView) findViewById(R.id.textViewOthersValue);



        mQueue = Volley.newRequestQueue(this);
        String url = ApiConstants.GET_EXPENSE+sharedPreferenceClass.getSubscriberId()+
                "/"+sharedPreferenceClass.getStoreId()+"/"+expense_date;

        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("monthExpenses");

                                String totalMonthExpense = response.getString("totalMonthExpense");
                                Double totalMonthExpenseValue = Double.valueOf(totalMonthExpense);
                                totalExpenseAmount.setText("৳:  "+Math.round(totalMonthExpenseValue));


                                String totalMonthSalary = response.getString("totalMonthSalary");
                                salaryValue.setText("৳:  "+totalMonthSalary);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject employee = jsonArray.getJSONObject(i);


                                    int totalExpense=  employee.getInt("totalExpense");
                                    String expense_type = employee.getString("expense_type");


                                    if(expense_type.equals("Rent"))
                                    {
                                       // rentType.setText(expense_type);
                                        rentValue.setText("৳: "+String.valueOf(totalExpense));
                                    }

                                    else if(expense_type.equals("Purchase"))
                                    {
                                        //purchaseType.setText(expense_type);
                                        purchaseValue.setText("৳: "+String.valueOf(totalExpense));
                                    }

                                    else if(expense_type.equals("Bill"))
                                    {
                                       // billType.setText(expense_type);
                                        billValue.setText("৳: "+String.valueOf(totalExpense));
                                    }
                                    else if(expense_type.equals("Others"))
                                    {
                                        //othersType.setText(expense_type);
                                        othersValue.setText("৳: "+String.valueOf(totalExpense));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);
        }catch (Exception error)
        {
            Log.d("key","server down");
        }

        date.setText(month);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


        buyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCard();
            }

        });

        billCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billCard();
            }

        });

        rentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rentCard();
            }

        });

        othersCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                othersCard();
            }

        });

        paymentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentCard();
            }

        });
    }

    public void buyCard()
    {
        //Toast.makeText(getApplicationContext(),"card",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Expense.this, BuyActivity.class);
        startActivity(intent);
    }

    public void billCard()
    {
        //Toast.makeText(getApplicationContext(),"card",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Expense.this, BillActivity.class);
        startActivity(intent);
    }

    public void rentCard()
    {

        Intent intent = new Intent(Expense.this, RentActivity.class);
        startActivity(intent);
    }

    public void othersCard()
    {
        //Toast.makeText(getApplicationContext(),"card",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Expense.this, OthersActivity.class);
        startActivity(intent);
    }

    public void paymentCard()
    {
        //Toast.makeText(getApplicationContext(),"card",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Expense.this, PaymentActivity.class);
        startActivity(intent);
    }

    


}
