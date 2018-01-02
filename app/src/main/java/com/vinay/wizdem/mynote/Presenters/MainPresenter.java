package com.vinay.wizdem.mynote.Presenters;

import android.content.Context;

import com.vinay.wizdem.mynote.DataBase.NoteReaderDbHelper;
import com.vinay.wizdem.mynote.Interfaces.MainPresenterInterface;

/**
 * Created by vinay_1 on 12/31/2017.
 */

public class MainPresenter implements PresenterLifeCycle {
    private MainPresenterInterface main;
    private NoteReaderDbHelper dbHelper;
    private Context context;
    //need model object here

   public MainPresenter(Context context){
        this.context = context;
        this.main = (MainPresenterInterface)context;
       // this.main = main;
    }

    @Override
    public void onCreate() {
        // ? invoke model object here
      //  dbHelper = new NoteReaderDbHelper()
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void onButtonSaveSelected(String text){
        main.displaySavedNote(text);
    }
}
