package com.vinay.wizdem.mynote.Presenters;

import com.vinay.wizdem.mynote.Interfaces.RequestContract;

/**
 * Created by vinay_1 on 10/16/2017.
 */

public class NoteWidgetPresenter {

    public NoteWidgetPresenter(String note){

    }
    public void saveNoteRequest(RequestContract requestContract){

        requestContract.save_request();

    }
}
