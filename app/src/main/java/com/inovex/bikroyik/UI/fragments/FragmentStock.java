package com.inovex.bikroyik.UI.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonIOException;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.NewProductGridAdapter;
import com.inovex.bikroyik.UI.adapter.ProductListAdapter;
import com.inovex.bikroyik.UI.adapter.ProductListGridAdapter;
import com.inovex.bikroyik.UI.adapter.StockGridAdapter;
import com.inovex.bikroyik.activity.HomeScreenMR;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.repositories.ProductRepository;
import com.inovex.bikroyik.interfaces.CallbackOrderItem;
import com.inovex.bikroyik.interfaces.ProductItemCallback;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;
import com.inovex.bikroyik.viewmodel.SalesActivityViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FragmentStock extends Fragment  {
    private ImageView btn_search, btn_scanner;

    private RecyclerView productList_recycler, productList_recycler_grid;
    private MaterialSpinner categoryList_spinner;
    private Spinner PartSpinner;
    List<String> productNameList = new ArrayList<>();

    private SearchView searchView;
    private AutoCompleteTextView autoCompleteTextView;
    private MenuItem menuItem;
    private EditText editText;


    private ProductListAdapter mAdapter;
    private ProductListGridAdapter mAdapterGrid;
    private NewProductGridAdapter newAdapterGrid;
    private StockGridAdapter stockAdapterGrid;
    private ProductRepository salesOrderRepository;

    private List<ProductModel> productModelList = new ArrayList<>();
    private ArrayList<ProductModel> arrayList = new ArrayList<ProductModel>();

    private List<ProductModel> productItems = new ArrayList<>();
    private List<ProductModel> productItemsSearch = new ArrayList<>();
    public MutableLiveData<List<ProductModel>> mProductDate_MutableLiveData = new MutableLiveData<>();

    private SalesActivityViewModel mSalesViewModel;
    private OrderActivityViewModel orderActivityViewModel;
    public static int currentCategorySelectedPosition =0;

    List<String> categoryList = new ArrayList<>();
    SharedPreference sharedPreference;
    Context mContext;

    CallbackOrderItem callbackOrderItem;
    ProductItemCallback callback;
    //    private CallbackString callbackString;
    private int currentSpinnerPosition = 0;


    HomeScreenMR.TopViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;

    boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock, container, false);


        mContext = getContext();
        init(view);

        sharedPreference = SharedPreference.getInstance(getContext());
        mSalesViewModel = new ViewModelProvider(requireActivity()).get(SalesActivityViewModel.class);
        mSalesViewModel.init(mContext);

        orderActivityViewModel = new ViewModelProvider(requireActivity()).get(OrderActivityViewModel.class);
        orderActivityViewModel.init(mContext, sharedPreference.getCurrentOrderId());
        orderActivityViewModel.setProductCategorySelectedPosition(0);


//        callbackString = new CallbackString() {
//            @Override
//            public void callback(String barcode) {
//                orderActivityViewModel.setBarcodeLiveData(barcode);
//            }
//        };

        callbackOrderItem = new CallbackOrderItem() {
            @Override
            public void onCallback(Context context, ProductModel productModel, int quantity) {
                orderActivityViewModel.makeOrder(context, productModel, quantity);
//                mOrderViewModel.queryForAllOrderData();
            }
        };


        callback = new ProductItemCallback() {
            @Override
            public void success(ProductModel productModel, int itemSize) {
                int currentQuantity = productModel.getOnHand() - itemSize;
                mSalesViewModel.updateProductData(productModel.getProductId(), currentQuantity);
            }
        };

        mSalesViewModel.getProductData().observe(getActivity(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModelList) {
                setAdapter(getContext(), callback, callbackOrderItem);
            }
        });




        mSalesViewModel.isProductButtonClicked().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (flag){
                    setAdapter(getContext(), callback, callbackOrderItem);
                }
                flag = true;

            }
        });

        mSalesViewModel.getAllCategory().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                strings.add(0,"All Items");
                categoryList = strings;
                categorySpinner(categoryList);
            }
        });



        mSalesViewModel.getAllProductName().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                // strings.add(0,"All Items");
                productNameList = strings;
                //categorySpinner(categoryList);
                productSpinner(productNameList);
            }
        });



        /*myArraylist = new ArrayList<>();
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,myArraylist);
        //loadData();


        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                *//*Toast.makeText(getContext(),stockAdapterGrid.getItemCount(),Toast.LENGTH_SHORT).show();*//*
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        return view;
    }


    /*public void loadData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for(int i=0; i <jsonArray.length();i++)
                            {
                                JSONObject receive = jsonArray.getJSONObject(i);
                                myArraylist.add(receive.getString("productName"));
                            }
                            autoCompleteTextView.setThreshold(1);
                            autoCompleteTextView.setAdapter(adapter);

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Server Error",Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }*/




    private void init(View view){
        categoryList_spinner = (MaterialSpinner) view.findViewById(R.id.spinner_sales);
        //btn_scanner = (ImageView) view.findViewById(R.id.btn_scan_iv);
        //btn_search = (ImageView) view.findViewById(R.id.btn_search_iv);
        productList_recycler = (RecyclerView) view.findViewById(R.id.productList_recycler_products);
        productList_recycler_grid = (RecyclerView) view.findViewById(R.id.productList_recycler_products_grid);
        PartSpinner=(Spinner)view.findViewById(R.id.spinnerId);
        //autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextViewId);
        //editText = (EditText) view.findViewById(R.id.editTextStock);


    }

    private void productSpinner(List<String> category){

        ArrayAdapter spinner = new ArrayAdapter(getContext(), R.layout.spinner_layout, category);
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PartSpinner.setAdapter(spinner);
        // PartSpinner.setDropdownMaxHeight(450);


        PartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    currentCategorySelectedPosition = position;
                    orderActivityViewModel.setProductCategorySelectedPosition(position);
                    mSalesViewModel.getProductName(productNameList.get(position));
                }
                else {
                    currentCategorySelectedPosition = position;
                    orderActivityViewModel.setProductCategorySelectedPosition(position);
                    mSalesViewModel.getProductName(productNameList.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void categorySpinner(List<String> category){
        ArrayAdapter spinner = new ArrayAdapter(getContext(), R.layout.spinner_layout, category);
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList_spinner.setAdapter(spinner);
        categoryList_spinner.setDropdownMaxHeight(450);


        categoryList_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position == 0) {
                    currentCategorySelectedPosition = position;
                    orderActivityViewModel.setProductCategorySelectedPosition(position);
                    mSalesViewModel.queryForGetAllProductData(getContext());
                }
                else {
                    currentCategorySelectedPosition = position;
                    orderActivityViewModel.setProductCategorySelectedPosition(position);
                    mSalesViewModel.getProductByCategory(categoryList.get(position));
                }
            }
        });
    }

    /*private void linearViewLayoutAdapter(Context context, ProductItemCallback callback,
                                         CallbackOrderItem callbackOrderItem){
        if (productList_recycler.getVisibility() != View.VISIBLE){
            productList_recycler_grid.setVisibility(View.GONE);
            productList_recycler.setVisibility(View.VISIBLE);
        }

        mAdapter = ProductListAdapter.getInstance();
        mAdapter.init(context, mSalesViewModel.mProductDate_MutableLiveData.getValue(), sharedPreference.getIsListView(), callback, callbackOrderItem);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(context);
        productList_recycler.setLayoutManager(linearLayoutManager);
        productList_recycler.setAdapter(mAdapter);
    }*/

    private void gridViewLayoutAdapter(Context context, ProductItemCallback callback,
                                       int totalNumberOfGrid, CallbackOrderItem callbackOrderItem){
        if (productList_recycler_grid.getVisibility() != View.VISIBLE){
            productList_recycler.setVisibility(View.GONE);
            productList_recycler_grid.setVisibility(View.VISIBLE);
        }

        /*mAdapterGrid = ProductListGridAdapter.getInstance();
        mAdapterGrid.init(context, mSalesViewModel.mProductDate_MutableLiveData.getValue(), sharedPreference.getIsListView(), callback, callbackOrderItem);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(context, totalNumberOfGrid);
        productList_recycler_grid.setLayoutManager(gridLayoutManager);
        productList_recycler_grid.setAdapter(mAdapterGrid);*/

        /*newAdapterGrid = NewProductGridAdapter.getInstance();
        newAdapterGrid.init(context, mSalesViewModel.mProductDate_MutableLiveData.getValue(), sharedPreference.getIsListView(), callback, callbackOrderItem);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(context, totalNumberOfGrid);
        productList_recycler_grid.setLayoutManager(gridLayoutManager);
        productList_recycler_grid.setAdapter(newAdapterGrid);*/

        stockAdapterGrid = StockGridAdapter.getInstance();
        stockAdapterGrid.init(context, mSalesViewModel.mProductDate_MutableLiveData.getValue(), sharedPreference.getIsListView(), callback, callbackOrderItem);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(context, totalNumberOfGrid);
        productList_recycler_grid.setLayoutManager(gridLayoutManager);
        productList_recycler_grid.setAdapter(stockAdapterGrid);
    }

    private void setAdapter(Context context, ProductItemCallback callback, CallbackOrderItem callbackOrderItem){
        /*if (sharedPreference.getIsListView()){
            //listView adapter
            //linearViewLayoutAdapter(context,callback, callbackOrderItem);

        }else {*/
            //gridView adapter
            gridViewLayoutAdapter(context, callback, 3, callbackOrderItem);
        //}
    }

}