package com.vinay.wizdem.mynote.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vinay.wizdem.mynote.Interfaces.RequestContract;
import com.vinay.wizdem.mynote.Presenters.NoteWidgetPresenter;
import com.vinay.wizdem.mynote.R;

public class NoteWidgetActivity extends AppCompatActivity implements RequestContract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_floating_note);

        final EditText write_note = (EditText)findViewById(R.id.write_note);

        Button closebutton = (Button) findViewById(R.id.expand_collapse);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close service
                finish();
            }
        });

        Button save_note =(Button) findViewById(R.id.save_note);
        save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save note
                String note = write_note.getText().toString();
                if(!note.isEmpty()){
                    NoteWidgetPresenter presenter = new NoteWidgetPresenter(write_note.getText().toString());
                    presenter.saveNoteRequest(NoteWidgetActivity.this);
                }else Toast.makeText(getApplication(),"Empty note!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void save_request(String note) {
    }

    @Override
    public void save_request() {
        Toast.makeText(getApplication(),"Request has been saved..",Toast.LENGTH_SHORT).show();

    }
}
