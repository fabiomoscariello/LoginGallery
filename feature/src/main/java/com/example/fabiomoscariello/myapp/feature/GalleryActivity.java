package com.example.fabiomoscariello.myapp.feature;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fabiomoscariello.myapp.feature.RecycleView.MyRecycleViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private Button selectButton;
    private Button resetButton;
    private RecyclerView recyclerView;
    private MyRecycleViewAdapter recycleViewAdapter;
    private ArrayList<Uri> uriArrayList;


    private static final String TAG = "GalleryActivity ";
    private Context context;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CODE_IMAGE = 100;
    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT";
    private static final int MAX_NUMBER_REQUEST_PERMISSIONS = 2;
    private static final List<String> sPermissions = Arrays.asList(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    );
    private int PermissionRequestCount;
    private MyRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);
        context = GalleryActivity.this;
        Log.d(TAG, TAG + "onCreate");
        selectButton = findViewById(R.id.select_button_id);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, TAG + "selectButton cliccato");
                Intent chooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(chooseIntent, REQUEST_CODE_IMAGE);
            }
        });
        resetButton = findViewById(R.id.reset_button_id);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, TAG + "resetButton cliccato");
                uriArrayList.clear();
            }
        });
    }

    public static Intent newIntent(Context packageContext, String username, String password) {
        Log.d(TAG, "GalleryActivity intent creato");
        Intent i = new Intent(packageContext, GalleryActivity.class);
        i.putExtra("username", username);
        i.putExtra("password", password);
        return i;
    }

    //controlla tutti i permessi
    private boolean checkAllPermissions() {
        Log.d(TAG, TAG + "checkAllPermissions");
        boolean hasPermissions = true;
        for (String permission : sPermissions) {
            hasPermissions &=
                    ContextCompat.checkSelfPermission(
                            this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return hasPermissions;
    }

    //richiede i permessi
    private void requestPermissionsIfNecessary() {
        Log.d(TAG, TAG + "requestPermissionsifNecessary");
        if (!checkAllPermissions()) {
            if (PermissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                PermissionRequestCount += 1;
                ActivityCompat.requestPermissions(
                        this,
                        sPermissions.toArray(new String[0]),
                        REQUEST_CODE_PERMISSIONS);
            } else {
                findViewById(R.id.select_button_id).setEnabled(false);
            }
        }
    }

    //Controlla i permessi se ne manca qualcuno
    @Override
    public void onRequestPermissionsResult(

            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        Log.d(TAG, TAG + "onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            requestPermissionsIfNecessary(); // no-op if permissions are granted already.
        }
    }

    //Primo Step
    /*public void checkLogin(){
        username=getIntent().getStringExtra("username");
        password=getIntent().getStringExtra("password");
        Log.d(TAG,TAG+"checkLogin");
        if(username.equals("User")&& password.equals("biberon")){
            risultato.setText("OK");
        }else{
            risultato.setText("Fail");
        }
    }
*/
    //Scelta Immagine
    private void handleImageRequestResult(Intent data) {
        Uri imageUri = null;
        if (data.getClipData() != null) {
            imageUri = data.getClipData().getItemAt(0).getUri();
        } else if (data.getData() != null) {
            imageUri = data.getData();

        }

        if (imageUri == null) {
            //Log.e(TAG, "Invalid input image Uri.");
            return;
        }
        uriArrayList.add(imageUri);
        adapter = new MyRecycleViewAdapter(this, uriArrayList);
        recyclerView.setAdapter(adapter);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, TAG + "onActivityResult");
        Log.d(TAG, "requestCode" + requestCode);
        Log.d(TAG, "resultCode" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_IMAGE:
                    Log.d(TAG, "requestCodeImage" + REQUEST_CODE_IMAGE);
                    handleImageRequestResult(data);
                    break;
                default:
                    Log.d(TAG, "requestCode sconosciuto" + requestCode);
            }
        } else {
            Log.e(TAG, String.format("Unexpected Result code %s", resultCode));
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, TAG + "onBackPressed");
        finish();
    }
}
