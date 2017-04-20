package com.example.elherichihafsa.images;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.util.Random;

/**
 * Created by mawdoudbacar on 12/04/2017.
 */


/**
 * Class dedicated to bitmap effect
 */

public class Effect {

    /**
     * Function graying a bitmap
     *
     * @param bmp
     * @return Bitmap
     */
    public static Bitmap toGray(Bitmap bmp) {
        long start = System.currentTimeMillis();
        
        int h = bmp.getHeight();
        int w = bmp.getWidth();

        int[] pixels = new int[h * w];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);

        for (int i = 0; i < h * w; ++i) {

            // Gets the RGB color of pixel

            int r = Color.red(pixels[i]);
            int b = Color.blue(pixels[i]);
            int g = Color.green(pixels[i]);

            // Calculates the average of color
            int moy = (int) (0.3 * r + 0.59 * g + 0.11 * b);

            // Affectation to pixel
            pixels[i] = Color.rgb(moy, moy, moy);
        }

        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        
        return bmp;
    }

    /**
     * Function applying a Red, blue or green colored filter to the image
     * @param bmp
     * @param red
     * @param green
     * @param blue
     * @return Bitmap
     */
    public static Bitmap colorFilter(Bitmap bmp, double red, double green, double blue) {
        long start = System.currentTimeMillis();

        // image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Bitmap result = Bitmap.createBitmap(width, height, bmp.getConfig());

        // color information
        int R, G, B;

        int[] pixels = new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);

        // scan through all pixels
        for(int i = 0; i < pixels.length; ++i) {
            // apply filtering on each channel R, G, B
            R = (int)(Color.red(pixels[i]) * red);
            G = (int)(Color.green(pixels[i]) * green);
            B = (int)(Color.blue(pixels[i]) * blue);
            pixels[i] = Color.rgb(R,G,B);
        }

        result.setPixels(pixels,0,width,0,0,width,height);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
            
        return result;
    }

    /**
     * Function inverting the bitmap's colors
     *
     * @param bmp
     * @return Bitmap
     *
     */
    public static Bitmap invert(Bitmap bmp) {
        long start = System.currentTimeMillis();

        int w = bmp.getWidth();
        int h = bmp.getHeight();

        int[] pixels = new int[h * w];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);

        for (int i = 0; i < pixels.length; i++) {
            int R = 255 - Color.red(pixels[i]);
            int G = 255 - Color.green(pixels[i]);
            int B = 255 - Color.blue(pixels[i]);

            pixels[i] = Color.rgb(R, G, B);
        }

        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
            
        return bmp;
    }

    /**
     * Function applying a random color filter to the image
     *
     * @param bmp
     * @return Bitmap
     */
    public static Bitmap toColorize(Bitmap bmp) {
        long start = System.currentTimeMillis();

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int[] pixels = new int[width * height];

        // random variable
        Random ran = new Random();

        // possibility for hue [0..360]
        int nbr = ran.nextInt(360);
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < height * width; ++i) {
            int p = pixels[i];
            int r = Color.red(p);
            int g = Color.green(p);
            int b = Color.blue(p);

            float[] hsv = new float[3];

            // Space changing
            Color.RGBToHSV(r, g, b, hsv);
            hsv[0] = nbr;
            hsv[1] = 1.0f;

            pixels[i] = Color.HSVToColor(hsv);
        }

        bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
            
        return bmp;
    }

    /**
     * Function modifies the saturation of the image
     * @param bmp
     * @param s defines the value we want to increase or diminish the saturation by
     * @return
     */
    public static Bitmap saturation(Bitmap bmp, float s) {
        long start = System.currentTimeMillis();

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int[] pixels = new int[width * height];

        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < height * width; ++i) {

            int r = Color.red(pixels[i]);
            int g = Color.green(pixels[i]);
            int b = Color.blue(pixels[i]);

            float[] hsv = new float[3];

            // Space changing
            Color.RGBToHSV(r, g, b, hsv);
            hsv[1] += s;

            if (hsv[1] > 1) {
                hsv[1] = 1;
            }else if (hsv[1] < 0) {
                hsv[1] = 0;
            }

            pixels[i] = Color.HSVToColor(hsv);
        }

        bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        
        return bmp;
    }

    /**
     * Function modifying the brightness of the image
     * @param bmp
     * @param value defines the value we want to increase or diminish the brightness by
     * @return Bitmap
     */
    public static Bitmap brightness(Bitmap bmp, int value) {
        long start = System.currentTimeMillis();

        // image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        // color information
        int A, R, G, B;
        int pixel;


        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        // scan through all pixels
        for (int i = 0; i < width * height; ++i) {

            // get pixel color
            pixel = pixels[i];
            A = Color.alpha(pixel);
            R = Color.red(pixel);
            G = Color.green(pixel);
            B = Color.blue(pixel);

            // increase/decrease each channel
            R += value;
            if (R > 255) {
                R = 255;
            } else if (R < 0) {
                R = 0;
            }

            G += value;
            if (G > 255) {
                G = 255;
            } else if (G < 0) {
                G = 0;
            }

            B += value;
            if (B > 255) {
                B = 255;
            } else if (B < 0) {
                B = 0;
            }

            pixels[i] = Color.rgb(R, G, B);

        }

        bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        
        return bmp;

    }

    /**
     * Function modifying the contrast of the image
     *
     * link :  https://xjaphx.wordpress.com/2011/06/21/image-processing-contrast-image-on-the-fly/
     *
     * @param bmp
     * @param value defines the value we want to increase or diminish the contrast by
     * @return bmp
     */

    public static Bitmap contrast(Bitmap bmp,double value){
        long start = System.currentTimeMillis();

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int []pixels = new int[width*height];

        double contrast = Math.pow((100+value)/100,2);

        bmp.getPixels(pixels,0,width,0,0,width,height);

        for (int i=0; i < pixels.length;i++) {

            int R = Color.red(pixels[i]);
            int G = Color.green(pixels[i]);
            int B = Color.blue(pixels[i]);

            R = (int) (((((R/255.0)-0.5)* contrast) + 0.5) * 255.0);
            if (R<0) { R=0;}
            else if (R>255) { R = 255;}


            G = (int) (((((G/255.0)-0.5)* contrast) + 0.5) * 255.0);
            if (G<0) { G=0;}
            else if (G>255) { G = 255;}

            B = (int) (((((B/255.0)-0.5)* contrast) + 0.5) * 255.0);
            if (B<0) { B=0;}
            else if (B>255) { B = 255;}

            pixels[i] = Color.rgb(R,G,B);

        }

        bmp.setPixels(pixels,0,width,0,0,width,height);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        
        return bmp;
    }

    /**
     * @param bmp
     * @return Bitmap
     *
     */
    public static Bitmap sketch(Bitmap bmp) {
        long start = System.currentTimeMillis();

        int type = 6;
        int threshold = 20;

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Bitmap bmp2 = Effect.toGray(bmp);
        Bitmap result = Bitmap.createBitmap(width, height, bmp.getConfig());

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[3][3];

        for(int y = 0; y < height - 2; ++y) {
            for(int x = 0; x < width - 2; ++x) {

                //      get pixel matrix
                for(int i = 0; i < 3; ++i) {
                    for(int j = 0; j < 3; ++j) {
                        pixels[i][j] = bmp2.getPixel(x + i, y + j);
                    }
                }

                // get alpha of center pixel
                A = Color.alpha(pixels[1][1]);

                // init color sum
                sumR = sumG = sumB = 0;
                sumR = (type*Color.red(pixels[1][1])) - Color.red(pixels[0][0]) - Color.red(pixels[0][2]) - Color.red(pixels[2][0]) - Color.red(pixels[2][2]);
                sumG = (type*Color.green(pixels[1][1])) - Color.green(pixels[0][0]) - Color.green(pixels[0][2]) - Color.green(pixels[2][0]) - Color.green(pixels[2][2]);
                sumB = (type*Color.blue(pixels[1][1])) - Color.blue(pixels[0][0]) - Color.blue(pixels[0][2]) - Color.blue(pixels[2][0]) - Color.blue(pixels[2][2]);

                // get final Red
                R = (int)(sumR  + threshold);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                // get final Green
                G = (int)(sumG  + threshold);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                // get final Blue
                B = (int)(sumB  + threshold);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        return result;
    }


    /**
     * Function to set a sepia effect on a bitmap
     *
     * @param bmp
     * @return Bitmap
     */

    public static Bitmap sepia(Bitmap bmp) {
        long start = System.currentTimeMillis();

        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int R, G, B;

        int[] pixels = new int[h * w];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);

        for (int i = 0; i < pixels.length; i++) {

            R = Color.red(pixels[i]);
            G = Color.green(pixels[i]);
            B = Color.blue(pixels[i]);
            B = G = R = (int) (0.3 * R + 0.59 * G + 0.11 * B);

            // Apply the intensity level needed to get filter sepia in each canal
            R += 94;
            if (R > 255) {
                R = 255;
            }

            G += 38;
            if (G > 255) {
                G = 255;
            }

            B += 18;
            if (B > 255) {
                B = 255;
            }
            pixels[i] = Color.rgb(R, G, B);
        }

        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        
        return bmp;
    }


    /**
     * function decreasing the color depth of the image giving it a cartoon effect
     *
     * link :  https://xjaphx.wordpress.com/2011/06/21/image-processing-decreasing-color-depth/
     *
     * @param bmp
     * @param value is the value we want to decrease the color depth by
     * @return Bitmap
     */
    public static Bitmap decreaseColorDepth(Bitmap bmp, int value) {
        long start = System.currentTimeMillis();

        // get image size
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int  R, G, B;

        int[] pixels = new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);

        // scan through all pixels
        for(int i = 0; i < pixels.length; ++i) {
            // get pixel color
            R = Color.red(pixels[i]);
            G = Color.green(pixels[i]);
            B = Color.blue(pixels[i]);

            // round-off color offset
            R = ((R + (value / 2)) - ((R + (value / 2)) % value) - 1);
            if(R < 0) { R = 0; }
            G = ((G + (value / 2)) - ((G + (value / 2)) % value) - 1);
            if(G < 0) { G = 0; }
            B = ((B + (value / 2)) - ((B + (value / 2)) % value) - 1);
            if(B < 0) { B = 0; }

            pixels[i] = Color.rgb(R,G,B);
        }

        bmp.setPixels(pixels,0,width,0,0,width,height);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        
        return bmp;
    }


}
