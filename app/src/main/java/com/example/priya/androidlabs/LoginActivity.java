package com.example.priya.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.priya.androidlabs.ListItemsActivity.REQUEST_IMAGE_CAPTURE;
import static junit.runner.BaseTestRunner.savePreferences;


public class LoginActivity extends Activity {

   protected static final String ACTIVITY_NAME = "LoginActivity";
   protected static final String Login_prefs = "Login_Activity";

   SharedPreferences pref;
   EditText email;
   Button button;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        email = (EditText)findViewById(R.id.EditLoginName) ;
        pref = getSharedPreferences(Login_prefs, Context.MODE_PRIVATE);
        email.setText(pref.getString("eKey", "email@domain.com"));


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View){
               SharedPreferences.Editor  ed = pref.edit();
               ed.putString("eKey", email.getText().toString());
               ed.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }
        } );
    }


    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    protected void 	onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }






}
