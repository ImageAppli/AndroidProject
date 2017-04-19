package com.example.elherichihafsa.images;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

import static com.example.elherichihafsa.images.R.id.Nav_menu;
import static com.example.elherichihafsa.images.R.id.image;


/**

 * MainActivity is the main class
 * MainActivity contains all variables and instances
 * MainActivity extends AppCompatActivity and implements View.OnTouchListener
 *
 * @see Convolution
 * @see HistogramEffect
 * @see ScaleEffect
 * @see Effect
 *
 * @authors Hafsa & Mawdoud
 * @version 2.0
 */

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {


    //bloc of declaration of all variables

    // Declaration of buttons, textViews and ImageView
    Button bBrightPlus, bBrightLess, bContrastPlus, bContrastLess, bSaturationLess , bSaturationPlus;
    TextView textBrightness, textContrast, textSaturation;
    ImageView myImageView;


    //variables about taking pictures from the gallery

    public static String SaveFolderName;
    static String Camera_Photo_ImagePath = "";
    static String Camera_Photo_ImageName = "";
    private static int RESULT_LOAD_IMG = 1;
    private static File f;
    private static int Take_Photo = 2;

    private static File gallery;


    // Variables concerning the implementation of the Zoom
    // 3 differents states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    private static final int CAMERA_REQUEST = 1888;
    private static int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    int mode = NONE;

    //PointF are variables used to know which are the points on the screen that user touch
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    // Initiating the original bitmap and a bitmap to save it
    private Bitmap bmp, bmpSave;

    // Variables concerning The Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    private View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImageView = (ImageView) findViewById(R.id.lenna);
        Bitmap b = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.lenna);
        bmp = b.copy(Bitmap.Config.ARGB_8888, true);
        bmpSave = b.copy(Bitmap.Config.ARGB_8888, true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textContrast = (TextView) findViewById(R.id.textViewContrast);
        textBrightness = (TextView) findViewById(R.id.textViewBright);
        textSaturation = (TextView) findViewById(R.id.textViewSaturation);

        bBrightPlus = (Button) findViewById(R.id.bBrightPlus);
        bBrightLess = (Button) findViewById(R.id.bBrightLess);
        bContrastPlus = (Button) findViewById(R.id.bContrastPlus);
        bContrastLess = (Button) findViewById(R.id.bContrastLess);
        bSaturationPlus = (Button) findViewById(R.id.bSaturationPlus);
        bSaturationLess = (Button) findViewById(R.id.bSaturationLess);


        bBrightPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = Effect.brightness(bmp, 5);
                myImageView.setImageBitmap(bmp);
            }
        });

        bBrightLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = Effect.brightness(bmp, -5);
                myImageView.setImageBitmap(bmp);
            }
        });

        bContrastPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = Effect.contrast(bmp, 5);
                myImageView.setImageBitmap(bmp);
            }
        });

        bContrastLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = Effect.contrast(bmp, -5);
                myImageView.setImageBitmap(bmp);
            }
        });

        bSaturationPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = Effect.saturation(bmp,0.05f);
                myImageView.setImageBitmap(bmp);
            }
        });

        bSaturationLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = Effect.saturation(bmp, -0.05f);
                myImageView.setImageBitmap(bmp);
            }
        });
        myImageView.setOnTouchListener((View.OnTouchListener) this);

        NavigationView nv = (NavigationView) findViewById(R.id.NavigationView);
        final View headerLayout = nv.getHeaderView(0);



        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            public boolean onNavigationItemSelected(MenuItem item) {

                if(mToggle.onOptionsItemSelected(item)){
                    return true;
                }
                item.setChecked(false);
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {

                    case (R.id.nav_camera):
                        photoCamera();
                        return true;

                    case (R.id.nav_gallery):
                        photoGallery(v);
                        return true;

                    case (R.id.nav_quit):
                        finish();
                        return true;

                    case (R.id.nav_save):
                        save();
                        return true;

                    case (R.id.nav_reset):
                        reset();
                        return true;

                    case R.id.nav_gray:
                        bmp = Effect.toGray(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_sepia:
                        bmp = Effect.sepia(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_invert:
                        bmp = Effect.invert(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_red:
                        bmp = Effect.colorFilter(bmp,1,0,0);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_green:
                        bmp = Effect.colorFilter(bmp,0,1,0);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_Blue:
                        bmp = Effect.colorFilter(bmp,0,0,1);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_random:
                        bmp = Effect.toColorize(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_cartoon:
                        bmp = Effect.decreaseColorDepth(bmp,64);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_sketch:
                        bmp = Effect.sketch(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_contrast:
                        bmp = HistogramEffect.contrast(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_egalbw:
                        bmp = HistogramEffect.equalHistogramBlackAndWhite(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_egalc:
                        bmp = HistogramEffect.equalHistogram(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_moyenneur:
                        bmp = Convolution.Moyenneur(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_moyenneur5x5:
                        bmp = Convolution.Moyenneur5x5(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_flip:
                        bmp = ScaleEffect.flip(bmp,true,true);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_rotate:
                        bmp = ScaleEffect.rotate(bmp,90f);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_Gauss3x3:
                        bmp = Convolution.GaussianBlur3x3(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_Gauss5x5:
                        bmp = Convolution.GaussianBlur5x5(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_median:
                        bmp = Convolution.median(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_sharpen:
                        bmp = Convolution.Sharpen(bmp);
                        myImageView.setImageBitmap(bmp);
                        return true;

                    case R.id.nav_test:
                        return true;
                }
                return true;

            }
        });


        // Permissions to access device to get image from gallery
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

    }


    /**
     * Function photoCamera allows the use of the camera
     * The function verifies if the permission is granted, if not the permission is asked
     * The picture is saved in a file named "App Cam" in the smartphone
     * The function requires API 23 to ask for the permission
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void photoCamera() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            // creation of folder to put the image take with camera
            SaveFolderName = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/App Cam";
            gallery = new File(SaveFolderName);
            if (!gallery.exists())
                gallery.mkdirs();

            // saving the picture
            Camera_Photo_ImageName = "Photo" + ".jpg";
            Camera_Photo_ImagePath = SaveFolderName + "/" + "PictureApp" + ".jpg";
            System.err.println(" Camera_Photo_ImagePath  " + Camera_Photo_ImagePath);
            f = new File(Camera_Photo_ImagePath);
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                    MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)), Take_Photo);
            System.err.println("f" + f);

        } else {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA
                    }, CAMERA_REQUEST);
        }
    }

    /**
     * Function to upload an image from the gallery
     * Requires API 23 to ask for the permission
     * If the permission is not allowed, the user has to give it to grant the application access to
     * the gallery
     * @param v defines a view
     *
     * @see MainActivity#loadImagefromGallery(View)
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void photoGallery(View v) {


        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            loadImagefromGallery(v);
        }else {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    }, REQUEST_EXTERNAL_STORAGE);
        }

    }


    /**
     * The function allows the app to upload an image from the gallery
     * It relies on the use of an intent
     * @param view defines a view
     *
     */

    public void loadImagefromGallery(View view) {

        // Creation of intent to get the image from the gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start of the intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    /**
     * This function returns the result of an activity. The two activities possible are
     * uploading an image from the gallery or taking pictures using the camera
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK &&
                    null != data) { // This part treats the code that allows the upload of an image from the gallery


                // Upload of the image
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Uri selectedImage = data.getData();

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                // Setting the cursor on the first column
                cursor.moveToFirst();

                // Affectation of the image to the view after decoding it, and saving the image in bmpSave
                // to be able to reset the bitmap
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                bmp = BitmapFactory.decodeFile(picturePath);
                bmp = bmp.copy(Bitmap.Config.ARGB_8888,true);
                bmpSave = bmp.copy(Bitmap.Config.ARGB_8888,true);
                myImageView.setImageBitmap(bmp);


            } else if (requestCode == Take_Photo) { // This part treats the code that allows taking
                                                    // pictures using the camera
                String filePath = null;

                filePath = Camera_Photo_ImagePath;
                if (filePath != null) {
                    bmp = (new_decode(new File(filePath)));

                    ExifInterface ei = new ExifInterface(Camera_Photo_ImagePath);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                    switch (orientation) { // This part makes sure the image put in the ImageView is well oriented

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            bmp = ScaleEffect.rotateImage(bmp, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            bmp = ScaleEffect.rotateImage(bmp, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            bmp = ScaleEffect.rotateImage(bmp, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:

                        default:
                            break;
                    }

                    // Resize the image so that it fits the dimensions of the ImageView
                    int newHeight = (int) (bmp.getHeight() * (512.0 / bmp.getWidth()));
                    Bitmap putImage = Bitmap.createScaledBitmap(bmp, 512, newHeight, true);

                    // Saving the image in bmpSave to be able to reset the bitmap
                    bmpSave = putImage.copy(Bitmap.Config.ARGB_8888,true);
                    myImageView.setImageBitmap(putImage);

                } else {
                    bmp = null;
                }
            } else {
                Toast.makeText(this, "No image was selected",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }


    /**
     *  Function decoding a file containing an image taken from camera
     *  Function found on stackOverFlow
     * @param f
     * @return Bitmap
     *
     */


    public Bitmap new_decode(File f) {

        int targetW = myImageView.getWidth();
        int targetH = myImageView.getHeight();

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // Finds the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scaleFactor = Math.min(width_tmp / targetW, height_tmp / targetH);

        // decode with inSampleSize
        try {
            o.inJustDecodeBounds = false;
            o.inSampleSize = scaleFactor;
            o.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            myImageView.setImageBitmap(bitmap);
            return bitmap;

        } catch (OutOfMemoryError e) {
            // TODO: handle exception
            e.printStackTrace();
            System.gc();
            return null;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void reset() {
        bmp = bmpSave.copy(Bitmap.Config.ARGB_8888, true);
        myImageView.setImageBitmap(bmp);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void save() {
        // Verification of permissions
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Bitmap bitmap = ((BitmapDrawable) myImageView.getDrawable()).getBitmap();

            ContentResolver cr = getContentResolver();
            String title = "myBitmap";
            String description = "Modified Bitmap";
            String savedURL = MediaStore.Images.Media.insertImage(cr, bitmap, title, description);

            Toast.makeText(MainActivity.this, savedURL, Toast.LENGTH_LONG).show();
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, REQUEST_EXTERNAL_STORAGE);
        }
    }


    /**
     * Implementation of the zoom
     * Function found on stackOverFlow : http://stackoverflow.com/questions/31502314/how-to-zoom-with-two-fingers-on-imageview-in-android
     * @param v
     * @param event
     * @return boolean
     *
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    // create the transformation in the matrix  of points
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                } else if (mode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }


    /**
     * Method: spacing
     * Description: checks the spacing between the two fingers on touch
     * @param event
     * @return float
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        float F = (x * x + y * y) * (x * x + y * y);
        return F;
    }


    /**
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * @param point
     * @param event
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Show an event in the LogCat view, for debugging
     * @param event
     */
    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }

}
