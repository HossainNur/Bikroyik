package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.utils.ApiConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class DashBoardActivity extends AppCompatActivity {

    TextView todaySale,todayDeposit,todayDue,todayExpense,yearSell,yearDeposit,yearDue,yearExpense,monthSell,
            monthDeposit,monthDue,monthExpense,totalSell,totalDeposit,totalDue,totalExpense,totalBill,
            currentMonth,currentYear,todayExpenseTotal;
    private ImageView backButton;
    SharedPreference sharedPreferenceClass;
    private Context context;
    private SharedPreference sharedPreference;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dash_board);

        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        toolbar_title = findViewById(R.id.toolbar_title);
        totalBill = findViewById(R.id.totalBill);
        currentMonth = findViewById(R.id.currentMonth);
        currentYear = findViewById(R.id.currentYear);
        todayExpenseTotal = findViewById(R.id.todayExpenseTotal);
        todaySale = findViewById(R.id.todaySale);
        todayDeposit = findViewById(R.id.todayDeposit);
        todayDue = findViewById(R.id.todayDue);
        todayExpense = findViewById(R.id.todayExpense);
        yearSell = findViewById(R.id.yearSell);
        yearDeposit = findViewById(R.id.yearDeposit);
        yearDue = findViewById(R.id.yearDue);
        yearExpense = findViewById(R.id.yearExpense);
        monthSell = findViewById(R.id.monthSell);
        monthDeposit = findViewById(R.id.monthDeposit);
        monthDue = findViewById(R.id.monthDue);
        monthExpense = findViewById(R.id.monthExpense);
        totalSell = findViewById(R.id.totalSell);
        totalDeposit = findViewById(R.id.totalDeposit);
        totalDue = findViewById(R.id.totalDue);
        totalExpense = findViewById(R.id.totalExpense);
        backButton = (ImageView) findViewById(R.id.btn_imageBack);


        if(sharedPreference.getLanguage().equals("English"))
        {
            toolbar_title.setText("Dashboard");
            todayExpenseTotal.setText("Today ");
            currentYear.setText("Current Year");
            currentMonth.setText("Current Month");
            totalBill.setText("Total");

            todaySale.setText("Total Sale : ");
            todayDeposit.setText("Total Deposit : ");
            todayDue.setText("Total Due : ");
            todayExpense.setText("Total Expense : ");
            yearSell.setText("Total Sale : ");
            yearDeposit.setText("Total Deposit : ");
            yearDue.setText("Total Due : ");
            yearExpense.setText("Total Expense : ");
            monthSell.setText("Total Sale : ");
            monthDeposit.setText("Total Deposit : ");
            monthDue.setText("Total Due : ");
            monthExpense.setText("Total Expense : ");
            totalSell.setText("Total Sale : ");
            totalDeposit.setText("Total Deposit : ");
            totalDue.setText("Total Due : ");
            totalExpense.setText("Total Expense : ");

        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        context = getApplicationContext();
        sharedPreferenceClass = SharedPreference.getInstance(context);

        getServer();


    }

    private void getServer() {


        String url = ApiConstants.SALES_SUMMERY+sharedPreferenceClass.getSubscriberId()+
                "/"+sharedPreferenceClass.getStoreId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String todaySaleValue = jsonObject.getString("todaySales");
                            Double totalS = Double.valueOf(todaySaleValue);
                            todaySale.append(Math.round(totalS)+"৳");


                            String todayDepositValue = jsonObject.getString("todayDeposit");
                            if(todayDepositValue.equals("null"))
                            {
                                todayDeposit.append("0৳");
                            }
                            else{
                                Double todayDepositAmount =Double.valueOf(todayDepositValue);
                                todayDeposit.append(+Math.round(todayDepositAmount)+"৳");
                            }


                            String todayDueValue = jsonObject.getString("todayDue");
                            if(todayDueValue.equals("null"))
                            {
                                todayDue.append("0৳");
                            }
                            else{
                                Double todayDueAmount =Double.valueOf(todayDueValue);
                                todayDue.append(+Math.round(todayDueAmount)+"৳");
                            }


                            String todayExpenseValue = jsonObject.getString("todayExpense");
                            if(todayExpenseValue.equals("null"))
                            {
                                todayExpense.append("0৳");
                            }
                            else{
                                Double todayExpenseAmount =Double.valueOf(todayExpenseValue);
                                todayExpense.append(Math.round(todayExpenseAmount)+"৳");
                            }


                            String yearSaleValue = jsonObject.getString("yearSales");
                            Double yearSaleAmount= Double.valueOf(yearSaleValue);
                            yearSell.append(Math.round(yearSaleAmount)+"৳");

                            String yearExpenseValue = jsonObject.getString("totalYearExpense");
                            if(yearExpenseValue.equals("null"))
                            {
                                yearExpense.append("0৳");
                            }
                            else{
                                Double yearExpenseAmount =Double.valueOf(yearExpenseValue);
                                yearExpense.append(Math.round(yearExpenseAmount)+"৳");
                            }

                            String yearDueValue = jsonObject.getString("totalYearDue");
                            if(yearDueValue.equals("null"))
                            {
                                yearDue.append("0৳");
                            }
                            else{
                                Double yearDueAmount =Double.valueOf(yearDueValue);
                                yearDue.append(Math.round(yearDueAmount)+"৳");
                            }

                            String yearDepositValue = jsonObject.getString("totalYearDeposit");
                            if(yearDepositValue.equals("null"))
                            {
                                yearDeposit.append("0৳");
                            }
                            else{
                                Double yearDepositAmount =Double.valueOf(yearDepositValue);
                                yearDeposit.append(Math.round(yearDepositAmount)+"৳");
                            }





                            String monthSaleValue = jsonObject.getString("monthSales");
                            Double monthSaleAmount= Double.valueOf(monthSaleValue);
                            monthSell.append(Math.round(monthSaleAmount)+"৳");


                            String monthExpenseValue = jsonObject.getString("totalMonthExpense");
                            if(monthExpenseValue.equals("null"))
                            {
                                monthExpense.append("0৳");
                            }
                            else{
                                Double monthExpenseAmount =Double.valueOf(monthExpenseValue);
                                monthExpense.append(Math.round(monthExpenseAmount)+"৳");
                            }

                            String monthDueValue = jsonObject.getString("totalMonthDue");
                            if(monthDueValue.equals("null"))
                            {
                                monthDue.append("0৳");
                            }
                            else{
                                Double monthDueAmount =Double.valueOf(monthDueValue);
                                monthDue.append(Math.round(monthDueAmount)+"৳");
                            }

                            String monthDepositValue = jsonObject.getString("totalMonthDeposit");
                            if(monthDepositValue.equals("null"))
                            {
                                monthDeposit.append("0৳");
                            }
                            else{
                                Double monthDepositAmount =Double.valueOf(monthDepositValue);
                                monthDeposit.append(Math.round(monthDepositAmount)+"৳");
                            }



                            String totalSaleValue = jsonObject.getString("totalSales");
                            if(totalSaleValue.equals("null"))
                            {
                                totalSell.append("0৳");
                            }
                            else
                            {
                                Double totalSaleAmount= Double.valueOf(totalSaleValue);
                                totalSell.append(Math.round(totalSaleAmount)+"৳");
                            }




                            String totalExpenseValue = jsonObject.getString("totalExpense");
                            if(totalExpenseValue.equals("null"))
                            {
                                totalExpense.append("0৳");
                            }
                            else{
                                Double totalExpenseAmount =Double.valueOf(totalExpenseValue);
                                totalExpense.append(Math.round(totalExpenseAmount)+"৳");
                            }

                            String totalDueValue = jsonObject.getString("totalDue");
                            if(totalDueValue.equals("null"))
                            {
                                totalDue.append("0৳");
                            }
                            else{
                                Double totalDueAmount =Double.valueOf(totalDueValue);
                                totalDue.append(Math.round(totalDueAmount)+"৳");
                            }

                            String totalDepositValue = jsonObject.getString("totalDeposit");
                            if(totalDepositValue.equals("null"))
                            {
                                totalDeposit.append("0৳");
                            }
                            else{
                                Double totalDepositAmount =Double.valueOf(totalDepositValue);
                                totalDeposit.append(Math.round(totalDepositAmount)+"৳");
                            }


                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashBoardActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }


}