package com.inovex.bikroyik.AppUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;

import com.inovex.bikroyik.utils.PrinterCommands;

import java.io.OutputStream;

public class PrintUtils {


    public void printBitmap(OutputStream outputStream, Bitmap bitmap) throws Exception {
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

    public int[][] getPixelsSlow(Bitmap bitmap) {
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

    public byte[] collectSlice(int i, int i2, int[][] iArr) {
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

    public int returnOneOrZeroForTrueOrFalse(boolean value) {
        if (value)
            return 1;
        else
            return 0;
    }

    public boolean shouldPrintColor(int i) {
        boolean z = false;
        if (((i >> 24) & 255) != 255) {
            return false;
        }
        if (((int) (((((double) ((i >> 16) & 255)) * 0.299d) + (((double) ((i >> 8) & 255)) * 0.587d)) + (((double) (i & 255)) * 0.114d))) < 127) {
            z = true;
        }
        return z;
    }


    public Bitmap scalledBitmap(Bitmap b, int scalledWidth) {
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


}

