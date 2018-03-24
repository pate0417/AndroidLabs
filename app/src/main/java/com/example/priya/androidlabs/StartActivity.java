package com.example.priya.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";
    Button button2;
    Button WeatherForecast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,50);
            }
        });

        Log.i(ACTIVITY_NAME,"In onCreate()");
        Button WeatherForecast = (Button) findViewById(R.id.WeatherForecast);
        WeatherForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked WeatherForecast");
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(intent);
            }
        });

    Button buttonChat = (Button) findViewById(R.id.StartChat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            Intent intent = new Intent(StartActivity.this, ChatWindow.class);
            startActivity(intent);
        }
    });

        Log.i(ACTIVITY_NAME,"In onCreate()");
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==50){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if(resultCode == Activity.RESULT_OK) {
            Log.i(ACTIVITY_NAME, "Returned Message is Result Ok");
        }
        String messagePassed = data.getStringExtra("Response");
        Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_LONG);
        toast.show();
        }



    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }
    @Override
    protected void 	onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

}
