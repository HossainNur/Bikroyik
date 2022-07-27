package com.inovex.bikroyik.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.utils.PrinterCommands;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.DeviceList;
import com.inovex.bikroyik.adapter.FinalOrderAdapter;
import com.inovex.bikroyik.adapter.ProductOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.inovex.bikroyik.activity.MainActivity.btsocket;

public class FinalOrder extends Activity {

    String details = "";
    String orderId ;
    String retailerAddress;
    String distributorId;
    String distributorName;
    String market_id ;
    String phone ;
    String total ;
    String retailId;
    String retailName;
    String marketName;
    String contact;
    String discount;
    String grandTotal;
    String paymentMethod;
    String advancedPaid;
    String deliveryDate;
    String employeeID;
    String orders;

    ArrayList<ProductOrder> productOrderArrayList = new ArrayList<>();

    TextView name, address, owner,delivery, order, totals, discounts,grandTotals, advanced,due, contactPhone, helpline;
    FinalOrderAdapter finalOrderAdapter;
    RecyclerView recyclerView;
    LinearLayout print;
    public static Bitmap bitmaps;
    public Bitmap bitmapImage;
    public static byte[] bmp;
    private static OutputStream outputStream;
    Bitmap bitmap;
    AppDatabaseHelper appDatabaseHelper;
    HashMap<String, String> employeeInfo = new HashMap<String, String>();






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);

        name = findViewById(R.id.retailer_name);
        address = findViewById(R.id.tv_storeContact);
        delivery = findViewById(R.id.tv_date);
        order =findViewById(R.id.order_id);
        recyclerView = findViewById(R.id.final_recycler);
        details = getIntent().getStringExtra("details");
        totals = findViewById(R.id.tv_Total_Price_without_discount);
        discounts = findViewById(R.id.tv_Total_Discount);
        grandTotals = findViewById(R.id.tv_Total_Price);
        advanced = findViewById(R.id.tv_totalPaid);
        due = findViewById(R.id.tv_Due);
        owner = findViewById(R.id.retail_owner);
        contactPhone = findViewById(R.id.tv_userContact);
        print= findViewById(R.id.print_receipt);
        helpline = findViewById(R.id.tv_helpline);

        appDatabaseHelper = new AppDatabaseHelper(this);

        employeeInfo = appDatabaseHelper.getEmployeeInfo();
        String reporting_mobile = employeeInfo.get(AppDatabaseHelper.COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE);
        helpline.setText(reporting_mobile);






        try {
            JSONObject orderJSONObject = new JSONObject(details);

            orderId = orderJSONObject.getString(APIConstants.API_KEY_ORDER_ID);
            retailId = orderJSONObject.getString(APIConstants.API_KEY_ORDER_RETAILER_ID);
            retailName = orderJSONObject.getString(APIConstants.API_KEY_ORDER_RETAILER_NAME);
            retailerAddress = orderJSONObject.getString(APIConstants.API_KEY_ORDER_RETAILER_ADDRESS);
            distributorId = orderJSONObject.getString(APIConstants.API_KEY_ORDER_DISTRIBUTOR_ID);
            distributorName = orderJSONObject.getString(APIConstants.API_KEY_ORDER_RETAILER_DISTRIBUTOR_NAME);
            market_id = orderJSONObject.getString(APIConstants.API_KEY_ORDER_MARKET_ID);
            marketName = orderJSONObject.getString(APIConstants.API_KEY_ORDER_MARKET_NAME);
            contact = orderJSONObject.getString(APIConstants.API_KEY_ORDER_RETAILER_PHONE);
            total = orderJSONObject.getString(APIConstants.API_KEY_ORDER_TOTAL);
            grandTotal = orderJSONObject.getString(APIConstants.API_KEY_ORDER_GRAND_TOTAL);
            discount = orderJSONObject.getString(APIConstants.API_KEY_ORDER_DISCOUNT);
            advancedPaid = orderJSONObject.getString(APIConstants.API_KEY_ORDER_ADVANCED_PAYMENT);
            deliveryDate = orderJSONObject.getString(APIConstants.API_KEY_ORDER_DELIVERY_DATE);
            employeeID = orderJSONObject.getString(APIConstants.API_KEY_ORDER_EMPLOYEE_ID);
            //orders = orderJSONObject.getString(APIConstants.API_KEY_ORDER_DETAILS);




            JSONArray orderJSONArray = orderJSONObject.getJSONArray(APIConstants.API_KEY_ORDER_DETAILS);
            for (int i  = 0;  i < orderJSONArray.length(); i++){
                String productId = orderJSONArray.getJSONObject(i).getString(APIConstants.API_KEY_ORDER_PRODUCT_ID);
                String productName = orderJSONArray.getJSONObject(i).getString(APIConstants.API_KEY_ORDER_PRODUCT_NAME);
                String productPrice = orderJSONArray.getJSONObject(i).getString(APIConstants.API_KEY_ORDER_PRODUCT_PRICE);
                String productQuantity = orderJSONArray.getJSONObject(i).getString(APIConstants.API_KEY_ORDER_PRODUCT_QUANTITY);

                HashMap<String, String> targetMap = new HashMap<String, String>();
                targetMap = appDatabaseHelper.getProductInfoForTarget(productId);
                String price = (targetMap.get(AppDatabaseHelper.COLUMN_TARGET_PRODUCT_SALE_VALUE));
                String qty = (targetMap.get(AppDatabaseHelper.COLUMN_TARGET_PRODUCT_SALE_QTY));

                //productQuantity = String.valueOf(Integer.parseInt(productQuantity)+ Integer.parseInt(qty));
                //productPrice = String.valueOf(Double.parseDouble(productPrice)+ Double.parseDouble(price));
                Log.d("workforce_target", "onCreate: "+productQuantity+productPrice);
                //appDatabaseHelper.UpdateTargetTable(productId,productQuantity, productPrice);

                ProductOrder productOrder = new ProductOrder(productId,productName,productQuantity,productPrice);
                productOrderArrayList.add(productOrder);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(this);

        HashMap<String, String> retailerInfo = appDatabaseHelper.getRetailerInfo(retailId);
        String owners = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_OWNER);
        String phones = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_PHONE);

        name.setText(retailName);
        address.setText(retailerAddress);
        owner.setText(owners);
        contactPhone.setText(phones);
        delivery.setText(deliveryDate);
        order.setText(orderId);
        totals.setText(total+" ৳");
        discounts.setText(discount+" ৳");
        grandTotals.setText(grandTotal+" ৳");
        advanced.setText(advancedPaid+" ৳");

        due.setText(String.valueOf(Double.parseDouble(grandTotal) - Double.parseDouble(advancedPaid))+" ৳");


        finalOrderAdapter = new FinalOrderAdapter(productOrderArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(finalOrderAdapter);

        screenshot();



        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PrintActivity.class));
                finish();
                printPhoto();

            }
        });

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


    private void screenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        View v = getWindow().getDecorView().getRootView();
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

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print.setVisibility(View.VISIBLE);

        } catch (Throwable e) {
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

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceList.getSocket();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
            Intent intent  = new Intent(getApplicationContext(), ProductsDirectoryActivity.class);
            startActivity(intent);
            finish();
    }
}
