package com.example.priya.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Intent intent = getIntent();

        String message = intent.getStringExtra("message");
        String id = intent.getStringExtra("id");
        String pos = intent.getStringExtra("pos");
        String  mTwoPane = intent.getStringExtra("mTwoPane");

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Bundle bundle=new Bundle();

        bundle.putString("message", "" + message);
        bundle.putString("id", "" + id);
        bundle.putString("pos",pos);
        bundle.putString("mTwoPane",mTwoPane);
        Fragment newFragment = new MessageFragment();
        newFragment.setArguments(bundle);

        transaction.replace(R.id.PhoneFrameLayout, newFragment);

        transaction.commit();
    }
}
