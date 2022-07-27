package com.inovex.bikroyik.UI.activity;

import static com.inovex.bikroyik.UI.activity.MainActivity.btsocket;

import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.FinalOrderAdapter;

import com.inovex.bikroyik.adapter.ProductOrder;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.utils.Constants;
import com.inovex.bikroyik.utils.PrinterCommands;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrderPrintActivity extends AppCompatActivity {

    String details = "";

    ArrayList<ProductOrder> productOrderArrayList = new ArrayList<>();

    TextView tv_store_name, tv_storeContact, tv_date, tv_orderId, tv_total,
            discounts, grandTotals, tv_totalPaid, tv_totalDue, tv_userContact,
            tv_helpline, tv_totalTax, tv_totalPrice_InTittle, tv_user_name;
    RecyclerView recyclerView;
    Button btn_newSale;
    CardView card_footer, final_scroll;
    ConstraintLayout cons_header_parent;
    LinearLayout ll_tittle, share_receipt;

    private boolean hasSavedScreenShoot = true;

    public static BluetoothSocket bt;
    ImageView iv_close;
    LinearLayout print;
    DatabaseSQLite databaseSQLite;
    public static Bitmap bitmaps;
    public Bitmap bitmapImage;
    public static byte[] bmp;
    private static OutputStream outputStream;
    Bitmap bitmap;
    AppDatabaseHelper appDatabaseHelper;
    HashMap<String, String> employeeInfo = new HashMap<String, String>();


    SharedPreference sharedPreference;
    private OrderActivityViewModel orderActivityViewModel;
    View v;

    ConstraintLayout.LayoutParams params;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);
        init();

        String orderId = sharedPreference.getOrderIdForPrint();
        databaseSQLite = new DatabaseSQLite(getApplicationContext());


        orderActivityViewModel = new ViewModelProvider(this).get(OrderActivityViewModel.class);
        orderActivityViewModel.init(getApplicationContext(), orderId);


        orderActivityViewModel.getOrderedProductList().observe(this, new Observer<OrderJsonModel>() {
            @Override
            public void onChanged(OrderJsonModel orderJsonModel) {

                if (orderJsonModel != null){
                    tv_totalPrice_InTittle.setText(orderJsonModel.getGrandTotal()+" ৳");
                    tv_store_name.setText(sharedPreference.getStoreName());
                    if (!TextUtils.isEmpty(sharedPreference.getStoreContact())){
                        tv_storeContact.setText(sharedPreference.getStoreContact());
                    }

                    if (!TextUtils.isEmpty(sharedPreference.getClientContact())){
                        tv_userContact.setText(sharedPreference.getClientContact());
                    }
                    if (!TextUtils.isEmpty(sharedPreference.getClientName())){
                        tv_user_name.setText(sharedPreference.getClientName());
                    }
                    tv_date.setText(Constants.getDateFromTimestamp(System.currentTimeMillis()));
                    tv_orderId.setText(orderId);
                    tv_total.setText(orderJsonModel.getTotal()+" ৳");
                    discounts.setText(orderJsonModel.getTotalDiscount()+" ৳");
                    grandTotals.setText(orderJsonModel.getGrandTotal()+" ৳");
                    tv_totalTax.setText(String.valueOf(orderJsonModel.getTotalTax())+" ৳");
                    tv_totalPaid.setText(orderJsonModel.getGrandTotal()+" ৳");

                    if (!TextUtils.isEmpty(sharedPreference.getHelpline())){

                        tv_helpline.setText(sharedPreference.getHelpline());
                    }


                    tv_totalDue.setText(String.valueOf(sharedPreference.getDueAmount())+" ৳");
                    setAdapter(getApplicationContext(), orderJsonModel.getOrderDetails());


                    /*int value = databaseSQLite.deleteOrderData(orderJsonModel.getOrderId());
                    if (value > 0) {
                        Toast.makeText(getApplicationContext(), "OrderData is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "OrderData is not deleted", Toast.LENGTH_SHORT).show();
                    }

                    int productOrderValue = databaseSQLite.deleteOrderProductData(orderJsonModel.getOrderId());
                    if (productOrderValue > 0) {
                        Toast.makeText(getApplicationContext(), "OrderProduct Data is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "OrderProduct is not deleted", Toast.LENGTH_SHORT).show();
                    }*/


                }
            }
        });







        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });





        btn_newSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderPrintActivity.this, OrderActivity.class));
                //startActivity(new Intent(OrderPrintActivity.this, OrderItemActivity.class));
                finish();
            }
        });







//        appDatabaseHelper = new AppDatabaseHelper(this);
//
//        employeeInfo = appDatabaseHelper.getEmployeeInfo();
//        String reporting_mobile = employeeInfo.get(AppDatabaseHelper.COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE);
        tv_helpline.setText(sharedPreference.getHelpline());




        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btsocket == null){
                    connectPrinter();
                }else {

                    hideVisibility();
                    screenshot();

                    printPhoto();
                    Intent intent = new Intent(OrderPrintActivity.this, OrderPrintActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        });

        share_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreference.isUnsuccessfulScreenShoot()){
                    Toast.makeText(getApplicationContext(), "file doesn't created successfully!", Toast.LENGTH_SHORT).show();
                }else {
                    sendFile();
                }

            }
        });


        deleteAllData();
    }




    private void init(){
        tv_store_name = findViewById(R.id.tv_store_name);
        tv_storeContact = findViewById(R.id.tv_storeContact);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_date = findViewById(R.id.tv_date);
        tv_orderId =findViewById(R.id.order_id);
        recyclerView = findViewById(R.id.final_recycler);
        details = getIntent().getStringExtra("details");
        tv_total = findViewById(R.id.tv_Total_Price_without_discount);
        discounts = findViewById(R.id.tv_Total_Discount);
        grandTotals = findViewById(R.id.tv_Total_Price);
        tv_totalPaid = findViewById(R.id.tv_totalPaid);
        tv_totalDue = findViewById(R.id.tv_Due);
        tv_userContact = findViewById(R.id.tv_userContact);
        print= findViewById(R.id.print_receipt);
        tv_helpline = findViewById(R.id.tv_helpline);
        tv_totalTax = findViewById(R.id.tv_Total_tax);
        tv_totalPrice_InTittle = findViewById(R.id.tv_totalPrice_InTittle);
        iv_close = findViewById(R.id.iv_close_print);
        btn_newSale = findViewById(R.id.btn_newSale);
        card_footer = findViewById(R.id.card_footer);
        ll_tittle = findViewById(R.id.ll_tittle);
        final_scroll = findViewById(R.id.final_scroll);
        share_receipt = findViewById(R.id.share_receipt);
        cons_header_parent = findViewById(R.id.cons_header_parent);

        params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);


        sharedPreference = SharedPreference.getInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!sharedPreference.getHasScreenShootOfReceipt()){

            if (!sharedPreference.isUnsuccessfulScreenShoot()){
                hideVisibility();
                screenshotForReceipt();

                Intent intent = new Intent(OrderPrintActivity.this, OrderPrintActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }

    private void screenshotForReceipt() {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        v = findViewById(R.id.final_scroll).getRootView();
        v.setDrawingCacheEnabled(true);

        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.buildDrawingCache(true);
        Log.d("print", "screenshot: "+v.getDrawingCache());
        //Uri uri = null;
        try {

            MainActivity.receipt_photoBitmap = Bitmap.createBitmap(v.getDrawingCache());

            v.setDrawingCacheEnabled(false);
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//            bitmaps = bitmap;
//            bitmapImage = bitmap;

            saveImage(MainActivity.receipt_photoBitmap);

        } catch (Throwable e) {
            sharedPreference.setUnsuccessfulScreenShoot(true);
            e.printStackTrace();
            Log.d("print", "screenshot: "+e);

        }
    }

    private void setAdapter(Context context, List<OrderedProductModel> orderedProductModelList){
        FinalOrderAdapter finalOrderAdapter = new FinalOrderAdapter(context, orderedProductModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(finalOrderAdapter);
    }

    private void connectPrinter() {
        Gson gson = new Gson();
        String json = getSharedPreferences("Printer", MODE_PRIVATE).getString("name",null);
        MainActivity.btsocket = gson.fromJson(json, BluetoothSocket.class);
        if(MainActivity.btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);

            if (btsocket != null ){
                bt = btsocket;
            }

        } else{
            Toast.makeText(getApplicationContext(), "Already connected.",Toast.LENGTH_SHORT).show();
            Log.d("workforce", "connectPrinter: "+ MainActivity.btsocket);
        }

    }

    private void printPhoto() {

        try {
            if(bitmap != null){
                Bitmap scalledBitmap = scalledBitmap(bitmap, 364);
                Bitmap grayscaledBitmap = toGrayscale(scalledBitmap);
                scalledBitmap.recycle();
                scalledBitmap = null;
                Bitmap bmpMonochrome = toMonochrome(grayscaledBitmap);
                grayscaledBitmap.recycle();
                grayscaledBitmap = null;
                if (btsocket == null) {
                    Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
                    this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
                } else {
                    outputStream  = btsocket.getOutputStream();
                    printBitmap(outputStream,bmpMonochrome);

                }

            }
            else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", e.toString());
        }

    }

    private ConstraintLayout.LayoutParams setLayoutParamsMatchParent(){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) final_scroll.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;

        return params;
    }

    private ConstraintLayout.LayoutParams setLayoutParamsWrapContent(){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) final_scroll.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        params.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;

        return params;
    }

    private void showVisibility(){
//        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) final_scroll.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;

        final_scroll.setLayoutParams(params);
        cons_header_parent.setVisibility(View.VISIBLE);
        card_footer.setVisibility(View.VISIBLE);
    }

    private void hideVisibility(){
        cons_header_parent.setVisibility(View.GONE);
        card_footer.setVisibility(View.GONE);

// Changes the height and width to the specified *pixels*
        params.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;

        final_scroll.setLayoutParams(params);

    }

    private void screenshot() {


        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        v = findViewById(R.id.final_scroll).getRootView();
        v.setDrawingCacheEnabled(true);

        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.buildDrawingCache(true);
        Log.d("print", "screenshot: "+v.getDrawingCache());
        //Uri uri = null;
        try {

            bitmap = Bitmap.createBitmap(v.getDrawingCache());

            v.setDrawingCacheEnabled(false);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            bitmaps = bitmap;
            bitmapImage = bitmap;



        } catch (Throwable e) {
            hasSavedScreenShoot = false;
            e.printStackTrace();
            Log.d("print", "screenshot: "+e);

        }



    }

    private void printBitmap(OutputStream outputStream, Bitmap bitmap) throws Exception {
        int[][] pixelsSlow = getPixelsSlow(bitmap);
        outputStream.write(PrinterCommands.SET_LINE_SPACING_24);
        for (int i = 0; i < pixelsSlow.length; i += 24) {
            outputStream.write(PrinterCommands.SELECT_BIT_IMAGE_MODE);
            outputStream.write(new byte[]{(byte) (pixelsSlow[i].length & 255), (byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & pixelsSlow[i].length) >> 8)});
            for (int i2 = 0; i2 < pixelsSlow[i].length; i2++) {
                outputStream.write(collectSlice(i, i2, pixelsSlow));
            }
            outputStream.write(PrinterCommands.FEED_LINE);
        }
        outputStream.write(PrinterCommands.SET_LINE_SPACING_30);

    }

    private int[][] getPixelsSlow(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[][] iArr = (int[][]) java.lang.reflect.Array.newInstance(int.class, new int[]{height, width});
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int pixel = bitmap.getPixel(i2, i) & 255;
                iArr[i][i2] = (((((bitmap.getPixel(i2, i) >> 16) & 255) << 16) | ViewCompat.MEASURED_STATE_MASK) | (((bitmap.getPixel(i2, i) >> 8) & 255) << 8)) | pixel;
            }
        }
        return iArr;
    }

    private byte[] collectSlice(int i, int i2, int[][] iArr) {
        byte[] bArr = new byte[]{(byte) 0, (byte) 0, (byte) 0};
        int i3 = i;
        int i4 = 0;
        while (i3 < i + 24 && i4 < 3) {
            byte b = (byte) 0;
            for (int i5 = 0; i5 < 8; i5++) {
                int i6 = i3 + i5;
                if (i6 < iArr.length) {
                    b = (byte) (b | ((byte) (returnOneOrZeroForTrueOrFalse(shouldPrintColor(iArr[i6][i2])) << (7 - i5))));
                }
            }
            bArr[i4] = b;
            i3 += 8;
            i4++;
        }

        return bArr;
    }

    private int returnOneOrZeroForTrueOrFalse(boolean value) {
        if (value)
            return 1;
        else
            return 0;
    }

    private boolean shouldPrintColor(int i) {
        boolean z = false;
        if (((i >> 24) & 255) != 255) {
            return false;
        }
        if (((int) (((((double) ((i >> 16) & 255)) * 0.299d) + (((double) ((i >> 8) & 255)) * 0.587d)) + (((double) (i & 255)) * 0.114d))) < 127) {
            z = true;
        }
        return z;
    }


    private Bitmap scalledBitmap(Bitmap b, int scalledWidth) {
        int imageHeight = b.getHeight();
        int imageWight = b.getWidth();
        int scalledHeight = Math.round(((float) scalledWidth) * ((float) imageHeight / imageWight));
        return Bitmap.createScaledBitmap(b, scalledWidth, scalledHeight, false);
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public Bitmap toMonochrome(Bitmap bitmap) {
        Bitmap bmpMonochrome = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpMonochrome);
        ColorMatrix ma = new ColorMatrix();
        ma.setSaturation(0);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(ma));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bmpMonochrome;
    }

    private void saveImage(Bitmap finalBitmap) {


        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+
                File.separator+"bikroyik_invoice.jpg");


        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

            out.flush();
            out.close();

            sharedPreference.setHasScreenshootOfReceipt(true);
            startActivity(new Intent(OrderPrintActivity.this, OrderPrintActivity.class));
            finish();

        } catch (Exception e) {
            sharedPreference.setUnsuccessfulScreenShoot(true);
            e.printStackTrace();
        }

    }

    private void sendFile(){
        File appSpecificExternalDir = new File(getApplicationContext().
                getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+ File.separator+"bikroyik_invoice.jpg");

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = appSpecificExternalDir.getName().substring(appSpecificExternalDir.getName().lastIndexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType(type);

        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getApplicationContext(),
                getApplicationContext().getPackageName() + ".provider",
                appSpecificExternalDir));
        startActivity(Intent.createChooser(intent, "Share using"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No file Viewer Installed", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceList.getSocket();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent  = new Intent(getApplicationContext(), OrderActivity.class);
       // Intent intent  = new Intent(getApplicationContext(), OrderItemActivity.class);
        startActivity(intent);
        finish();
    }



    private void deleteAllData() {
        OrderJsonModel orderJsonModel = new OrderJsonModel();

        int value = databaseSQLite.deleteOrderData(orderJsonModel.getOrderId());
        if (value > 0) {
            Toast.makeText(getApplicationContext(), "OrderData is deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "OrderData is not deleted", Toast.LENGTH_SHORT).show();
        }

        int productOrderValue = databaseSQLite.deleteOrderProductData(orderJsonModel.getOrderId());
        if (productOrderValue > 0) {
            Toast.makeText(getApplicationContext(), "OrderProduct Data is deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "OrderProduct is not deleted", Toast.LENGTH_SHORT).show();
        }
    }



}
