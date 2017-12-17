package com.vinay.wizdem.mynote.Services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vinay.wizdem.mynote.Activities.NoteWidgetActivity;
import com.vinay.wizdem.mynote.R;

import static com.vinay.wizdem.mynote.R.id.expanded_view;

public class FloatingNoteService extends Service {

    private View mFloatingView;
    private WindowManager mWindowManager;
    public FloatingNoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(Settings.canDrawOverlays(this)){
            //inflate floating view.
            mFloatingView = LayoutInflater.from(this).inflate(R.layout.floating_note_widget, null);

            //add view to the window.
            final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                   // WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT
            );

            //Specify the view position
            params.gravity = Gravity.TOP | Gravity.LEFT;       //initial view added to top left
            params.x = 0;
            params.y = 100;

            //add view to the window
            mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
            try{
                mWindowManager.addView(mFloatingView, params);

            }catch (Exception e){
                e.printStackTrace();
            }

            //The root element of the collapsed view layout
            final View collapsed_view = mFloatingView.findViewById(R.id.collapse_view);
            //The root element of the expanded view layout
            final View expanded_view = mFloatingView.findViewById(R.id.expanded_view);

            //set the service close button
            ImageView closeWidget = (ImageView) mFloatingView.findViewById(R.id.close_widget);
            closeWidget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopSelf();
                }
            });

            //setting view while expanded save and close button functionality

            //setting close button expand to collapse
            Button expand_collapse = (Button)mFloatingView.findViewById(R.id.expand_collapse);

            expand_collapse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    collapsed_view.setVisibility(View.VISIBLE);
                    expanded_view.setVisibility(View.GONE);
                }
            });

            //Drag & move floating view on touch
            mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:

                            //remember the initial position.
                            initialX = params.x;
                            initialY = params.y;

                            //get the touch location
                            initialTouchX = motionEvent.getRawX();
                            initialTouchY = motionEvent.getRawY();
                            return true;
                        case MotionEvent.ACTION_UP:
                            int Xdiff = (int) (motionEvent.getRawX() - initialTouchX);
                            int Ydiff = (int) (motionEvent.getRawY() - initialTouchY);

                            //the check for Xdiff <10 && Ydiff<10 because sometimes elements move a little while clicking.
                            //So that is click event.
                            if (Xdiff < 10 && Ydiff < 10) {
                                if (isViewCollapsed()) {
                                    //When user clicks on the image view of the collapsed layout,
                                    //visibility of the collapsed layout will be changed to "View.GONE"
                                    //and expanded view will become visible.
                                    collapsed_view.setVisibility(View.GONE);
                                    expanded_view.setVisibility(View.VISIBLE);
                                    /*Intent intent = new Intent(FloatingNoteService.this, NoteWidgetActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);*/
                                    //stopSelf();

                                }
                            }
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            //Calculate the X and Y coordinates of the view.
                            params.x = initialX + (int) (motionEvent.getRawX() - initialTouchX);
                            params.y = initialY + (int) (motionEvent.getRawY() - initialTouchY);

                            // Update the layout with new X & Y coordinates
                            mWindowManager.updateViewLayout(mFloatingView, params);
                            return true;
                    }

                    return false;
                }
            });
        } else {
            Toast.makeText(this,"No Overlay permission",Toast.LENGTH_SHORT).show();
            stopSelf();
        }

    }

    /**
     * Detect if the floating view is collapsed or expanded.
     *
     * @return true if the floating view is collapsed.
     */
    private boolean isViewCollapsed(){
        return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null)mWindowManager.removeView(mFloatingView);
    }

}
