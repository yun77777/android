package com.example.testapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;
//import android.widget.AdapterView.OnItemSelectedListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    EditText et_id;
    Button btn_test;
    Button btn_test2;
    Button btn_implicit;

    FragmentGallery fragment1;
    FragmentVideo fragment2;
    FragmentCamera fragment3;

    ImageView imageView;
    VideoView videoView;
    ActivityResultLauncher<Intent> launchSomeActivity;
    int requestCode;
    Uri photoUri;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    static final int REQUEST_VIDEO_CAPTURE = 3;
    static final int REQUEST_IMAGE_GALLERY = 4;
    String [] permissions={};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id=findViewById(R.id.et_id);
        btn_test=findViewById(R.id.btn_test);
        btn_test2=findViewById(R.id.btn_test2);
        btn_implicit=findViewById(R.id.btn_implicit);

        fragment1=new FragmentGallery();
        fragment2=new FragmentVideo();
        fragment3=new FragmentCamera();

        imageView=(ImageView) findViewById(R.id.imageView);

        //an alternative of deprecated startActivityForResult()
        launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Log.d("getResultCode", String.valueOf(result.getResultCode()));
                            Log.d("data", String.valueOf(data));

                            if(result.getResultCode() == RESULT_OK){
                                switch(requestCode){
                                    case REQUEST_IMAGE_GALLERY:
                                        break;
                                    case REQUEST_IMAGE_CAPTURE:
//                                        Bundle extras = data.getExtras();
//                                        Bitmap imageBitmap = (Bitmap) extras.get("data");
//                                        imageView.setImageBitmap(imageBitmap);

                                        Log.d("photoUri", String.valueOf(photoUri));
                                        imageView.setImageURI(photoUri);
                                        btn_test2.setText("zz");
                                        Log.d("imageView", String.valueOf(imageView));

                                        galleryAddPic();
//                                FragmentMessage fragment=new FragmentMessage();
                                        Bundle bundle=new Bundle();
                                        FragmentGallery fragment=new FragmentGallery();

                                        if(fragment!=null){
                                            FragmentManager fragmentManager=getSupportFragmentManager();
                                            fragmentManager.beginTransaction().replace(R.id.fragment_layout, fragment).commit();
                                        }
                                        break;
                                    case REQUEST_VIDEO_CAPTURE:
                                        Uri videoUri = result.getData().getData();
                                        Log.d("videoUri", String.valueOf(photoUri));
                                        videoView.setVideoURI(videoUri);
                                        Log.d("videoView", String.valueOf(imageView));

                                        break;
                                }
                            }
                        }
                    }
                });

        //setting for the initial screen
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment1).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //setOnNavigationItemSelectedListener() is deprecated
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String [] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW","android.permission.CAMERA"};

                switch(item.getItemId()){
                    case R.id.tab_gallery://gallery
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permissions, REQUEST_IMAGE_GALLERY);
                        }
                        dispatchTakeGalleryIntent();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment1).commit();
                        return true;
                    case R.id.tab_video://video
//                        int requestCode = 200;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permissions, REQUEST_VIDEO_CAPTURE);
                        }
                        dispatchTakeVideoIntent();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment2).commit();
                        return true;
                    case R.id.tab_camera:
//                        int requestCode = 200;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(permissions, REQUEST_TAKE_PHOTO);
                        }
                        dispatchTakePictureIntent();

//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, fragment3).commit();

                        return true;
                }
                return false;
            }
        });


        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_id.setText("test");
            }
        });

        btn_test2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String str=et_id.getText().toString();

                Intent intent=new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("str",str);

                startActivity(intent);

//                finish();
            }
        });

        btn_implicit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String str=et_id.getText().toString();

                //implicit intent
                Intent intent=new Intent();
                intent.setAction("aaa");// aaa is an identifier for SecondActivity
                intent.putExtra("str",str);
                startActivity(intent);
            }
        });

    }

    // END onCreate()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            Log.d("imageView", String.valueOf(imageView));
        }
    }



    // TAKE PICTURE@
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                        getPackageName()+".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO); //deprecated
                Log.d("TAKE PHOTOURI:", String.valueOf(photoURI));
                Log.d("takePictureIntent:", String.valueOf(takePictureIntent));
                requestCode=REQUEST_IMAGE_CAPTURE;
                photoUri=photoURI;
                launchSomeActivity.launch(takePictureIntent);
//                setResult(requestCode, takePictureIntent);
//                finish();
            }
        }
    }


    // TAKE VIDEO@

    String currentPhotoPath;
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            launchSomeActivity.launch(takeVideoIntent);
//            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
// TAKE GALLERY IMAGE@

    private void dispatchTakeGalleryIntent() {
        Intent takeGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*, video/*");
        launchSomeActivity.launch(takeGalleryIntent);
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d("storageDir", String.valueOf(storageDir));
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Log.d("currentPhotoPath:",currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onStart() {
        Log.d("starrrrrrt","@");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d("stoppppp","@");
        super.onStop();
    }
}