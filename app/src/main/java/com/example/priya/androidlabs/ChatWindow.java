package com.example.priya.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatWindow extends Activity {

     ListView listView;
     EditText editText;
     Button button;
     static ArrayList<String> arrayList = new ArrayList<>();
     ChatDatabaseHelper helper;
     static SQLiteDatabase database;
     ContentValues contentValues;
     static boolean mTwoPane;
    protected static final String ACTIVITY_NAME = "ChatWindow";

    FrameLayout tabletFrame;
    FragmentTransaction transaction;
    static ChatAdapter chatAdapter;
    static FragmentManager manager;
    static Fragment newFragment;
    @Override
    protected void onResume() {
        super.onResume();

        delete();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.chatView);
        editText = (EditText) findViewById(R.id.textChat);
        button = (Button) findViewById(R.id.sendChat);
        tabletFrame = (FrameLayout) findViewById(R.id.tabletFrame);
        helper = new ChatDatabaseHelper(this);
        database = helper.getWritableDatabase();
        contentValues = new ContentValues();


        if(tabletFrame == null){
            mTwoPane = false;
        }
        else {
            mTwoPane = true;
        }

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

        chatAdapter = new ChatAdapter(this);
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
        manager= getFragmentManager();
        transaction = manager.beginTransaction();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                database = helper.getWritableDatabase();
                Cursor cursor = database.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TB_NAME, null);
                cursor.moveToPosition((int) l);
                Log.i("Position", "" + l);

                String  id = cursor.getString(0);
                String message = cursor.getString(1);

                if(mTwoPane) {

                    transaction = manager.beginTransaction();
                    Bundle bundle=new Bundle();

                    bundle.putString("message", "" + message);
                    bundle.putString("id", "" + id);
                    bundle.putString("mTwoPane",mTwoPane+"");
                    newFragment = new MessageFragment();
                    newFragment.setArguments(bundle);

                    transaction.replace(R.id.tabletFrame, newFragment);
                    //transaction.addToBackStack(null);

                    transaction.commit();
                }
                else{
                    Intent intent = new Intent(ChatWindow.this,MessageDetails.class);
                    intent.putExtra("message","" + message);
                    intent.putExtra("id",id);
                    intent.putExtra("pos",l);
                    intent.putExtra("mTwoPane",mTwoPane+"");
                    startActivityForResult(intent,10);
                }
            }
        });
    }

    public static void delete(){

        Cursor cursor = database.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TB_NAME, null);
        cursor.moveToFirst();
        arrayList.clear();

        while(!cursor.isAfterLast() ) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }

        chatAdapter.notifyDataSetChanged();
        if(mTwoPane){
            if(newFragment != null)
                manager.beginTransaction().remove(newFragment).commit();
        }
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {

            super(ctx, 0);
        }

        public int getCount() {

            return arrayList.size();
        }

        @Override
        public String getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            TextView message;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                message = (TextView) result.findViewById(R.id.textView);
            } else {
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















