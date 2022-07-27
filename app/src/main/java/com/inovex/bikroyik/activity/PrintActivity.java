package com.inovex.bikroyik.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;

import com.inovex.bikroyik.utils.PrinterCommands;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.DeviceList;

import java.io.OutputStream;

import static com.inovex.bikroyik.activity.MainActivity.btsocket;

public class PrintActivity  extends AppCompatActivity {


    EditText message;
    LinearLayout btnPrint;
    Bitmap bitmap;
    byte FONT_TYPE;
    //private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
    SharedPreferences sharedpreferences;
    BluetoothAdapter bluetoothAdapter = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);


        btnPrint = (LinearLayout) findViewById(R.id.btnPrint);

        bitmap = FinalOrder.bitmaps;

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printPhoto();

            }
        });



    }





    //print photo
    public void printPhoto() {

        Log.d("receipt", "printPhoto: ");

        try {

            Bitmap bmp = bitmap;
            if (bmp != null) {

                Bitmap scalledBitmap = scalledBitmap(bmp, 364);
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



            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", e.toString());
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
        startActivity(new Intent(getApplicationContext(),AddOrder.class));
        finish();
    }
}