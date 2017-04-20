package com.example.elherichihafsa.images;

import android.graphics.Bitmap;
import android.graphics.Color;


/**
 * Class treats functions using convolutions
 */

public class Convolution {

    /**
     * The value of the central pixel is replaced by the average of the value of the pixels surrounding it
     * The size of the kernel depends on the intensity of the noise and the size of the significant
     * details of the image being processed.
     * Here the kernel is 3x3.
     * @param bmp
     * @return Bitmap
     *
     */

    public static Bitmap Moyenneur(Bitmap bmp) {
        long start = System.currentTimeMillis();

        int SIZE = 3;

        int[][] Matrix = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                Matrix[i][j] = 1;
            }
        }

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int sumR, sumG, sumB = 0;
        int[] pixels = new int [width*height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int x = 1; x < width - 1; ++x) {
            for (int y = 1; y < height - 1; ++y) {

                sumR = sumG = sumB = 0;

                int index=0;

                for (int u = -1; u <= 1; ++u) {
                    for (int v = -1; v <= 1; ++v) {
                        index = (y+v)*width +(x+u);
                        sumR += Color.red(pixels[index]) * Matrix[u + 1][v + 1];
                        sumG += Color.green(pixels[index]) * Matrix[u + 1][v + 1];
                        sumB += Color.blue(pixels[index]) * Matrix[u + 1][v + 1];
                    }
                }

                sumR = sumR / 9;

                sumG = sumG / 9;

                sumB = sumB / 9;

                pixels[index] =  Color.rgb(sumR, sumG, sumB);

            }
        }

        bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        return bmp;
    }


    /**
     * The value of the central pixel is replaced by the average of the value of the pixels surrounding it
     * The size of the kernel depends on the intensity of the noise and the size of the significant
     * details of the image being processed.
     * Here the kernel is 5x5.
     * @param bmp
     * @return Bitmap
     *
     */

    public static Bitmap Moyenneur5x5(Bitmap bmp) {
        long start = System.currentTimeMillis();

        int SIZE = 5;

        int[][] Matrix = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                Matrix[i][j] = 1;
            }
        }

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int sumR, sumG, sumB = 0;
        int[] pixels = new int [width*height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int x = 2; x < width - 2; ++x) {
            for (int y = 2; y < height - 2; ++y) {

                sumR = sumG = sumB = 0;

                int index=0;

                for (int u = -2; u <= 2; ++u) {
                    for (int v = -2; v <= 2; ++v) {
                        index = (y+v)*width +(x+u);
                        sumR += Color.red(pixels[index]) * Matrix[u + 2][v + 2];
                        sumG += Color.green(pixels[index]) * Matrix[u + 2][v + 2];
                        sumB += Color.blue(pixels[index]) * Matrix[u + 2][v + 2];
                    }
                }

                sumR = sumR / 25;

                sumG = sumG / 25;

                sumB = sumB / 25;

                pixels[index] =  Color.rgb(sumR, sumG, sumB);

            }
        }

        bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        return bmp;
    }

    /**
     * This filter acts on each pixel of the active selection or layer, adjusting its value to the
     * average of the values ​​of the pixels present in a radius defined.
     *
     * @param bmp
     * @return Bitmap
     */
    public static Bitmap GaussianBlur3x3(Bitmap bmp) {

        long start = System.currentTimeMillis();

        int[][] Matrix = new int[][] {
                {1,2,1},
                {2,4,2},
                {1,2,1}
        };

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int sumR, sumG, sumB = 0;

        int[] pixels = new int [width*height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int x = 1; x < width - 1; ++x) {
            for (int y = 1; y < height - 1; ++y) {

                sumR = sumG = sumB = 0;
                int index=0;


                for (int u = -1; u <= 1; ++u) {
                    for (int v = -1; v <= 1; ++v) {

                        index = (y+v)*width +(x+u);
                        sumR += Color.red(pixels[index]) * Matrix[u + 1][v + 1];
                        sumG += Color.green(pixels[index]) * Matrix[u + 1][v + 1];
                        sumB += Color.blue(pixels[index]) * Matrix[u + 1][v + 1];
                    }
                }


                sumR = sumR / 16;

                sumG = sumG / 16;

                sumB = sumB / 16;


                pixels[index] =  Color.rgb(sumR, sumG, sumB);

            }
        }

        bmp.setPixels(pixels, 0, width, 0, 0, width, height);

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        return bmp;

    }

    /**
     * This filter acts on each pixel of the active selection or layer, adjusting its value to the
     * average of the values ​​of the pixels present in a radius defined.
     *
     * @param bmp
     * @return Bitmap
     */
    public static Bitmap GaussianBlur5x5(Bitmap bmp) {
        
        long start = System.currentTimeMillis();

        int[][] Matrix = new int[][] {
                {1,2,3,2,1},
                {2,6,8,6,2},
                {3,8,10,8,3},
                {2,6,8,6,2},
                {1,2,3,2,1}
        };

        int width = bmp.getWidth();
        int height = bmp.getHeight();

        int sumR, sumG, sumB = 0;

        int[] pixels = new int [width*height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);

        for (int x = 2; x < width - 2; ++x) {
            for (int y = 2; y < height - 2; ++y) {

                sumR = sumG = sumB = 0;
                int index=0;


                for (int u = -2; u <= 2; ++u) {
                    for (int v = -2; v <= 2; ++v) {

                        index = (y+v)*width +(x+u);
                        sumR += Color.red(pixels[index]) * Matrix[u + 2][v + 2];
                        sumG += Color.green(pixels[index]) * Matrix[u + 2][v + 2];
                        sumB += Color.blue(pixels[index]) * Matrix[u + 2][v + 2];
                    }
                }


                sumR = sumR / 98;

                sumG = sumG / 98;

                sumB = sumB / 98;


                pixels[index] =  Color.rgb(sumR, sumG, sumB);

            }
        }

        bmp.setPixels(pixels, 0, width, 0, 0, width, height);

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        return bmp;

    }

    /**
     * Function used to sharpen the given bitmap wich makes it clearer
     * @param src
     * @return bmp
     */
    public static Bitmap Sharpen(Bitmap src) {
        long start = System.currentTimeMillis();

        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        int[][] Matrix = new int[][] {
                {0,-2  ,0},
                {-2,10 ,-2},
                {0,-2  ,0}
        };

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[3][3];

        for(int y = 0; y < height - 2; ++y) {
            for(int x = 0; x < width - 2; ++x) {

                // get pixel matrix
                for(int i = 0; i < 3; ++i) {
                    for(int j = 0; j < 3; ++j) {
                        pixels[i][j] = src.getPixel(x + i, y + j);
                    }
                }

                // get alpha of center pixel
                A = Color.alpha(pixels[1][1]);

                // init color sum
                sumR = sumG = sumB = 0;

                // get sum of RGB on matrix
                for(int i = 0; i < 3; ++i) {
                    for(int j = 0; j < 3; ++j) {
                        sumR += (Color.red(pixels[i][j]) * Matrix[i][j]);
                        sumG += (Color.green(pixels[i][j]) * Matrix[i][j]);
                        sumB += (Color.blue(pixels[i][j]) * Matrix[i][j]);
                    }
                }

                // get final Red
                R = (int)(sumR / 2 + 1  );
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                // get final Green
                G = (int)(sumG / 2 +1);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                // get final Blue
                B = (int)(sumB / 2 + 1);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // apply new pixel
                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        return result;
    }

    /**
     * Function used to sort a tab
     *
     * @param tab
     * @return int[]
     */
    public static int[] sort(int[] tab){
        int k= 0;
        for(int i = 0;i<tab.length-1;i++){
            for(int j=i+1;j<tab.length;j++){
                if(tab[j]<tab[i]){
                    tab[i] = k;
                    tab[i] = tab[j];
                    tab[j] = k;
                }
            }
        }
        return tab;
    }

    /**
     * The median filter is a non-linear digital filter, used for noise reduction
     *
     * @param bmp
     * @see #sort(int[])
     * @return Bitmap
     *
     */
    public static Bitmap median(Bitmap bmp){
        long start = System.currentTimeMillis();

        int w = bmp.getWidth();
        int h = bmp.getHeight();

        int[] tab = new int [9];
        int[] SortedTab ;
        int[] pixels = new int[w*h];

        bmp.getPixels(pixels, 0, w, 0, 0, w, h);

        int index = 0;
        // iteration through pixels
        for(int y = 1; y < h-1; ++y) {
            for(int x = 1; x < w-1; ++x) {
                for(int i=-1;i<2;i++){
                    for(int j=-1;j<2;j++){
                        index = (y+j)*w +(x+i);
                        for(int u=0;u<tab.length;u++){
                            tab[u] = pixels[index];
                        }
                        SortedTab = sort(tab);
                        int med = SortedTab[(SortedTab.length)/2];
                        pixels[index] = med;
                    }
                }
            }
        }
        bmp.setPixels(pixels, 0, w, 0, 0, w, h);
        
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        return bmp;
    }

}
