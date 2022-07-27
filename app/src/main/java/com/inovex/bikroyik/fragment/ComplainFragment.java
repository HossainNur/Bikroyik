package com.inovex.bikroyik.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.Complain;
import com.inovex.bikroyik.adapter.ComplainAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ComplainFragment extends Fragment {

    AppDatabaseHelper appDatabaseHelper;
    ArrayList<HashMap<String, String>> retailers = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> complainList = new ArrayList<HashMap<String, String>>();
    List<String> retailerId = new ArrayList<String>();
    List<String> retailerName = new ArrayList<String>();
    ArrayList<Complain> complainLists = new ArrayList<Complain>();
    RecyclerView complain_recyclerView;
    String retailerId_str = "";
    FloatingActionButton addComplain;
    ComplainAdapter complainAdapter;
    ProgressDialog progressDialog;
    public ComplainFragment() {

    }
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_complain, container, false);

        onBackPressed(view);
        context = getContext();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait.....");
        addComplain = view.findViewById(R.id.fabAddComplain);
        complain_recyclerView = view.findViewById(R.id.complain_recycler);
        retailerId.add("");
        retailerName.add(" --- Select Retailer Name ---");
        appDatabaseHelper = new AppDatabaseHelper(context);

        retailers = appDatabaseHelper.getRetailerData();


        populateRetailersData();
        populateComplainData();
        complainAdapter = new ComplainAdapter(complainLists,context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        complain_recyclerView.setLayoutManager(mLayoutManager);
        complain_recyclerView.setItemAnimator(new DefaultItemAnimator());
        complain_recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL) );
        complain_recyclerView.setAdapter(complainAdapter);

        addComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComplains();
            }
        });







        



        return view;
    }

    private void populateComplainData() {
        if (complainLists != null){
            complainLists.clear();
        }


        complainList = appDatabaseHelper.getComplain();

        for(int i = 0; i < complainList.size(); i++){
            HashMap<String, String> complain = new HashMap<String, String>();
            complain = complainList.get(i);

            String id = complain.get(AppDatabaseHelper.COLUMN_COMPLAIN_RETAIL_ID);
            String name = "";
            for(int j = 0; j < retailerId.size(); j++){
                if(retailerId.get(j).equals(id)){
                    name = retailerName.get(j);
                    break;
                }
            }
           
            String title = complain.get(AppDatabaseHelper.COLUMN_COMPLAIN_TITLE);
            String notes = complain.get(AppDatabaseHelper.COLUMN_COMPLAIN_NOTES);

            Complain complain1 = new Complain(id,name,title,notes);


            complainLists.add(complain1);

        }


    }

    private void addComplains() {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_complain_dialog);
        final EditText title, details;
        Spinner retailSpinner;
        final TextView submit;
        title = dialog.findViewById(R.id.etComplainTitle);
        details = dialog.findViewById(R.id.etComplainDetails);
        submit = dialog.findViewById(R.id.btn_complain_done);
        retailSpinner = dialog.findViewById(R.id.spinner_retailer);
        

        ArrayAdapter<String> retailer_adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_layout,retailerName);
        retailer_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retailSpinner.setAdapter(retailer_adapter);

        retailSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == 0){
                    retailerId_str ="";
                } else {
                    retailerId_str = retailerId.get(i);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_str = title.getText().toString();
                String details_str = details.getText().toString();

                if (title_str.length() == 0 ){
                    title.setError("Please set Title");
                } else if( details_str.length() == 0) {
                    details.setError("Please, fill details box");
                } else if(retailerId_str.length() == 0){
                    submit.setError("Please, select Retailer");
                } else {
                    progressDialog.show();
                    submitComplain(title_str, details_str, retailerId_str);

                    dialog.dismiss();
                }

            }
        });

        dialog.show();

    }

    private void submitComplain(final String title_str, final String details_str, final String retailerId_str) {

        String URL = APIConstants.SAVE_COMPLAIN;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("workforce_save_complain", "onResponse: "+response);
                try {
                    String message = response.getString("message");
                    if(message.equalsIgnoreCase("Retail Complain added successful")){
                        Toast.makeText(getContext(), "Complain added successfully..", Toast.LENGTH_SHORT).show();
                        Log.d("workforce_complain_", "onResponse: "+retailerId_str+"   "+title_str+"  "+details_str);
                        appDatabaseHelper.insertComplain(retailerId_str,title_str,details_str);

                        populateComplainData();
                        complainAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {

            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                String body = null;
                try {
                    jsonObject.put(APIConstants.SAVE_COMPLAIN_RETAILER,retailerId_str);
                    jsonObject.put(APIConstants.SAVE_COMPLAIN_TITLE,title_str);
                    jsonObject.put(APIConstants.SAVE_COMPLAIN_DETAILS,details_str);
                    jsonObject.put(APIConstants.EMPLOYEE_JSON_KEY_ID,appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_ID));

                    body = jsonObject.toString();
                    Log.d("workforce_complain", "getBody: "+body);

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




    }

    private void populateRetailersData() {

        for (int i = 0; i < retailers.size(); i++){
            retailerId.add(retailers.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_ID));
            retailerName.add(retailers.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_NAME));
        }

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