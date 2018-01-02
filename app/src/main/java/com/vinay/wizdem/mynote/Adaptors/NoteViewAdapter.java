package com.vinay.wizdem.mynote.Adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinay.wizdem.mynote.R;

/**
 * Created by vinay_1 on 12/30/2017.
 */

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder> {

    public NoteViewAdapter(){

    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_layout, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewAdapter.NoteViewHolder holder, int position) {
        holder.note.setText("This is the new note text!!!");
        holder.time.setText("3:56 PM");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        public TextView note, time;
        public NoteViewHolder(View itemView) {
            super(itemView);
            note = (TextView)itemView.findViewById(R.id.note_data);
            time = (TextView)itemView.findViewById(R.id.note_time);
        }
    }
}
