package com.example.elherichihafsa.images;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by mawdoudbacar on 12/04/2017.
 */

/**
 * Class dedicated to scale effects
 */

public class ScaleEffect {

    /**
     * Function rotating the image based on a specified angle
     *
     * @param source
     * @param angle
     * @return Bitmap
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    /**
     *
     *  Function rotating the image based on a specified angle
     * @param bitmap
     * @param degrees
     * @return Bitmap
     *
     */
    public static Bitmap rotate(Bitmap bitmap, float degrees) {

        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmp;
    }

    /*
     * Function flipping the image upside down
     * @param bitmap
     * @param horizontal
     * @param vertical
     * @return Bitmap
     */
    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {

        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmp;
    }
}
