package com.inovex.bikroyik.UI.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.DueClientAdapter;
import com.inovex.bikroyik.UI.adapter.NewProductGridAdapter;
import com.inovex.bikroyik.UI.adapter.OrderHistoryAdapter;
import com.inovex.bikroyik.UI.adapter.OrderListAdapter;
import com.inovex.bikroyik.UI.adapter.ProductListAdapter;
import com.inovex.bikroyik.UI.adapter.ProductListGridAdapter;
import com.inovex.bikroyik.activity.HomeScreenMR;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.model.EmployeeListModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.interfaces.CallbackOrderItem;
import com.inovex.bikroyik.interfaces.ProductItemCallback;
import com.inovex.bikroyik.model.DueData;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;
import com.inovex.bikroyik.viewmodel.SalesActivityViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;


public class ProductFragment extends Fragment {
    private ImageView btn_search, btn_scanner;
    private EditText et_search_contact;

    private RecyclerView productList_recycler, productList_recycler_grid;
    private MaterialSpinner categoryList_spinner;

    private SearchView searchView;
    NewProductGridAdapter newAdapterGrid;
    private EditText editText;
    private Spinner PartSpinner;

    private List<ProductModel> gProductModelList;
    private List<ProductModel> productModelList = new ArrayList<ProductModel>();
    private List<ProductModel> productModelListFilter;
    private List<ProductModel> productModelListSearch;
    ArrayList<ProductModel> arrayList = new ArrayList<>();

    private OrderListAdapter orderListAdapter;
    private ProductListAdapter mAdapter;
    private ProductListGridAdapter mAdapterGrid;


    private SalesActivityViewModel mSalesViewModel;
    private OrderActivityViewModel orderActivityViewModel;
    public static int currentCategorySelectedPosition =0;

    String selectedCategory;
    List<String> categoryList = new ArrayList<>();
    List<String> productNameList = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.fragment_product3, container, false);


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
//            mOrderViewModel.queryForAllOrderData();
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
                //productSpinner(categoryList);
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

        btn_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderActivityViewModel.setOnScannerButtonClickListener(true);
            }
        });

        return view;
    }

    public void onClick(View v)
    {

    }


    private void init(View view){
        categoryList_spinner = (MaterialSpinner) view.findViewById(R.id.spinner_sales);
        btn_scanner = (ImageView) view.findViewById(R.id.btn_scan);
        productList_recycler = (RecyclerView) view.findViewById(R.id.productList_recycler_products);
        productList_recycler_grid = (RecyclerView) view.findViewById(R.id.productList_recycler_products_grid);
        PartSpinner=(Spinner) view.findViewById(R.id.spinnerId);
        //textview=view.findViewById(R.id.testView);


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



    private void linearViewLayoutAdapter(Context context, ProductItemCallback callback,
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
    }

    private void gridViewLayoutAdapter(Context context, ProductItemCallback callback,
                                       int totalNumberOfGrid, CallbackOrderItem callbackOrderItem){
        if (productList_recycler_grid.getVisibility() != View.VISIBLE){
            productList_recycler.setVisibility(View.GONE);
            productList_recycler_grid.setVisibility(View.VISIBLE);
        }

        mAdapterGrid = ProductListGridAdapter.getInstance();
        mAdapterGrid.init(context, mSalesViewModel.mProductDate_MutableLiveData.getValue(), sharedPreference.getIsListView(), callback, callbackOrderItem);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(context, totalNumberOfGrid);
        productList_recycler_grid.setLayoutManager(gridLayoutManager);
        productList_recycler_grid.setAdapter(mAdapterGrid);


    }

    private void setAdapter(Context context, ProductItemCallback callback, CallbackOrderItem callbackOrderItem){
        if (sharedPreference.getIsListView()){
            //listView adapter
            linearViewLayoutAdapter(context,callback, callbackOrderItem);

        }else {
            //gridView adapter
            gridViewLayoutAdapter(context, callback, 3, callbackOrderItem);
        }
    }


}