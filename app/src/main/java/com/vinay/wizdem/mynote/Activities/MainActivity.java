package com.vinay.wizdem.mynote.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vinay.wizdem.mynote.R;
import com.vinay.wizdem.mynote.Services.FloatingNoteService;

public class MainActivity extends AppCompatActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Settings.canDrawOverlays(this)){
            initializeView();
        }else requestOverlayPermission(); //not working properly

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v("TEST_BACK","Back pressed happen");
    }

    //Checking Overlay permission settings (explicitly granting permissions)
    private void requestOverlayPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);

        }
    }

    //call back not working
    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION){
            if(Settings.canDrawOverlays(this)){
                initializeView();
            }else{
                requestOverlayPermission();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    // Initializing service for floating widget
    private void initializeView() {
        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, FloatingNoteService.class));
                finish();
            }
        });
    }
}
