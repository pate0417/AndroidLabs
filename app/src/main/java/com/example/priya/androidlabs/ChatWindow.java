package com.example.priya.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatWindow extends Activity {

     ListView listView;
     EditText editText;
     Button button;
     ArrayList<String> arrayList = new ArrayList<>();
     ChatDatabaseHelper helper;
     SQLiteDatabase database;
     ContentValues contentValues;
    protected static final String ACTIVITY_NAME = "ChatWindow";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.chatView);
        editText = (EditText) findViewById(R.id.textChat);
        button = (Button) findViewById(R.id.sendChat);

        helper = new ChatDatabaseHelper(this);
        database = helper.getWritableDatabase();
        contentValues = new ContentValues();


        Cursor cursor = database.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TB_NAME, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }

        Log.i(ACTIVITY_NAME,"Cursor's column count =" + cursor.getColumnCount());

        for (int i=0; i<cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, cursor.getColumnName(i));}

        final ChatAdapter chatAdapter = new ChatAdapter(this);
        listView.setAdapter(chatAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.add(editText.getText().toString());
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE,editText.getText().toString());
                database.insert(ChatDatabaseHelper.TB_NAME,"",contentValues);
                chatAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx) {

            super(ctx, 0);
        }

        public int getCount(){

            return arrayList.size();
        }

        public String getItem (int position){

            return arrayList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            TextView message;
            if(position%2==0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                message = (TextView) result.findViewById(R.id.textView);
            }
            else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                message = (TextView) result.findViewById(R.id.message_text);
            }
            message.setText(getItem(position));
            return result;
        }
    }

      public void onDestroy(){
        super.onDestroy();
          helper.close();
          Log.i(ACTIVITY_NAME, "In onDestroy");

      }



}















