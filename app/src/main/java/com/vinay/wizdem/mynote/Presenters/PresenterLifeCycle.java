package com.vinay.wizdem.mynote.Presenters;

/**
 * Created by vinay_1 on 12/31/2017.
 */

public interface PresenterLifeCycle {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
}
