package com.example.elherichihafsa.images;

import android.graphics.Bitmap;
import android.graphics.Color;


/**
 * Class dedicated to histogram effect
 */

public class HistogramEffect {

    /**
     * Function used to calculate the histogram of a bitmap.
     *
     * @param bmp
     * @return int[]
     */

    public static int[] histogram(Bitmap bmp) {

        int w = bmp.getWidth();
        int h = bmp.getHeight();

        int[] hist = new int[256];
        int[] pixels = new int[h*w];

        bmp.getPixels(pixels,0,w,0,0,w,h);

        // Create a tab with size 256 for each lvl of gray of bmp
        for (int x = 0; x < pixels.length; x++) {
            int R = Color.red(pixels[x]);
            int G = Color.green(pixels[x]);
            int B = Color.blue(pixels[x]);

            int gray = (int)(0.3*R+0.59*G+0.11*B);

            hist[gray] = hist[gray] + 1; // Increases the number of pixels having the same gray level
        }

        return hist;
    }

    /**
     * Calculates the  max and min values of histogram of bmp
     * @param bmp
     * @see #histogram(Bitmap)
     * @return int[] containing min and max values
     *
     */

    public static int[] dynamic(Bitmap bmp) {

        int[] hist = histogram(bmp);
        int[] D = new int[2];

        int min = 0;
        int max = 0;

        int maxi = hist[0];
        int mini = hist[0];

        for (int i = 0; i < hist.length; i++) {
            if (hist[i] > maxi) {
                max = i;
            } else if (hist[i] < mini) {
                min = i;
            }
        }

        D[0] = max;
        D[1] = min;
        return D;
    }

    /**
     * Function used to contrast the bitmap using the extension of dynamics
     * @param bmp
     * @see #dynamic(Bitmap)
     * @return Bitmap
     */

    public static Bitmap contrast(Bitmap bmp) {

        Effect.toGray(bmp);
        // Image size
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        int[] pixels = new int[h * w];
        int[] D = dynamic(bmp);

        bmp.getPixels(pixels, 0, w, 0, 0, w, h);

        // Applies linear extension of dynamics to the bitmap

        for (int i = 0; i < pixels.length; ++i) {
            int R = 255 * ((Color.red(pixels[i])) - D[1]) / (D[0] - D[1]);
            int G = 255 * ((Color.green(pixels[i])) - D[1]) / (D[0] - D[1]);
            int B = 255 * ((Color.blue(pixels[i])) - D[1]) / (D[0] - D[1]);
            pixels[i] = Color.rgb(R, G, B);
        }

        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
        return bmp;
    }

    /**
     * Improves the contrast of the bitmap by equalizing its histogram
     * @param bmp
     * @see #histogram(Bitmap)
     * @return Bitmap
     */
    public static Bitmap equalHistogram(Bitmap bmp) {

        Bitmap operation = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        int w = bmp.getWidth();
        int h = bmp.getHeight();

        int[] pixels = new int[h * w];

        // Calculate histogram of image
        int[] histo = histogram(bmp);
        int[] C = new int[histo.length];
        C[0] = histo[0];
        // Calculate histogram accumulated of image
        for (int i = 1; i < histo.length; i++) {
            C[i] = C[i - 1] + histo[i];
        }

        // Equalize histogram
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            int R = Color.red(pixels[i]);
            R = C[R] * 255 / pixels.length;
            int G = Color.green(pixels[i]);
            G = C[G] * 255 / pixels.length;
            int B = Color.blue(pixels[i]);
            B = C[B] * 255 / pixels.length;

            pixels[i] = Color.rgb(R, G, B);
        }

        operation.setPixels(pixels, 0, w, 0, 0, w, h);
       return operation;
    }


    /**
     * Improves the contrast of a grayed bitmap by equalizing its histogram
     * @param bmp
     * @see #histogram(Bitmap)
     * @return Bitmap
     *
     */
    public static Bitmap equalHistogramBlackAndWhite(Bitmap bmp) {
        Effect.toGray(bmp);
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        int[] pixels = new int[h * w];
        int[] histo = histogram(bmp);
        int[] C = new int[histo.length];
        C[0] = histo[0];

        for (int i = 1; i < histo.length; i++) {
            C[i] = C[i - 1] + histo[i];
        }


        bmp.getPixels(pixels, 0, w, 0, 0, w, h);
        for (int i = 0; i < pixels.length; i++) {
            int R = Color.red(pixels[i]);
            R = C[R] * 255 / pixels.length;
            int G = Color.green(pixels[i]);
            G = C[G] * 255 / pixels.length;
            int B = Color.blue(pixels[i]);
            B = C[B] * 255 / pixels.length;

            pixels[i] = Color.rgb(R, G, B);
        }

        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
        return bmp;
    }
}
