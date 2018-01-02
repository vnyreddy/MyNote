package com.vinay.wizdem.mynote.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vinay.wizdem.mynote.Adaptors.NoteViewAdapter;
import com.vinay.wizdem.mynote.Interfaces.MainPresenterInterface;
import com.vinay.wizdem.mynote.Presenters.MainPresenter;
import com.vinay.wizdem.mynote.R;
import com.vinay.wizdem.mynote.Services.FloatingNoteService;

public class MainActivity extends AppCompatActivity implements MainPresenterInterface {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 0;
    private RecyclerView recyclerView;
    private NoteViewAdapter noteViewAdapter;
    private MainPresenter presenter;
    private EditText noteText;
    private ImageButton saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        saveNoteButton = (ImageButton)findViewById(R.id.buttonSaveNote);
        noteText = (EditText)findViewById(R.id.noteText);
        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    saveNoteButton.setEnabled(false);
                } else {
                    saveNoteButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Adaptor initialization
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        noteViewAdapter = new NoteViewAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, true );
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(noteViewAdapter);

        if(Settings.canDrawOverlays(this)){
            initializeView();
        }else requestOverlayPermission(); //not working properly

        presenter.onCreate();
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


    public void onClickSaveNote(View view){
        Log.i("ONCLICK_SAVE", "onClick Executed");
        presenter.onButtonSaveSelected(noteText.getText().toString());
    }
    //call back for display note
    @Override
    public void displaySavedNote(String text) {
        Log.i("DISPLAY", "save note confirmation");
        Toast.makeText(getApplicationContext(),"Note Saved.."+text,Toast.LENGTH_SHORT).show();
    }
}
