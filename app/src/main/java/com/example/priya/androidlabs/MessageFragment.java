package com.example.priya.androidlabs;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

import static com.example.priya.androidlabs.ChatDatabaseHelper.KEY_ID;

public class MessageFragment extends Fragment {
    Boolean mTwoPane;
    TextView idView;
    TextView messageView;
    Button delete;
    SQLiteDatabase database;
    ChatDatabaseHelper helper;
    String pos;
    public MessageFragment(){

    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_message_fragment,container,false);

        View gui = view.findViewById(R.id.tabletFrame);

        if(gui != null){
            mTwoPane = true;
        }
        else{
            mTwoPane =false;
        }
        helper = new ChatDatabaseHelper(getActivity());

        messageView = view.findViewById(R.id.ShowMessage);
        messageView.setText(getArguments().getString("message"));

        idView = view.findViewById(R.id.ShowID);
        final String id = getArguments().getString("id");
        idView.setText(id);
        mTwoPane = Boolean.parseBoolean(getArguments().getString("mTwoPane"));
        if(!mTwoPane) {
            pos = getArguments().getString("pos");
        }
        delete = view.findViewById(R.id.MessageDelete);

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                database = helper.getWritableDatabase();
                database.execSQL("delete from " + ChatDatabaseHelper.TB_NAME + " where " + KEY_ID + "=" + id);
                database.close();
                if(!mTwoPane) {
                    getActivity().finish();
                }
                else {
                    ChatWindow.delete();
                }
            }
        });

        return view;
    }
}