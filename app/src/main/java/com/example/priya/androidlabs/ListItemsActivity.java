package com.example.priya.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton image;
    Switch TSwitch;
    CheckBox BoxCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate");

        image = (ImageButton) findViewById(R.id.IB);


       TSwitch = (Switch) findViewById(R.id.ST);
       TSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CharSequence on = "Switch is On", off = "Switch is Off";
                int durationShort = Toast.LENGTH_SHORT, durationLong = Toast.LENGTH_LONG;

                if(isChecked) {
                    Toast toast = Toast.makeText(getApplicationContext(),on,durationShort);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),off,durationLong);
                    toast.show();
                }
            }
        });

      BoxCheck = (CheckBox) findViewById(R.id.CB);
      BoxCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

        @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked) {
                 AlertDialog.Builder builder= new AlertDialog.Builder(ListItemsActivity.this);
                 builder.setTitle(R.string.dialog_title)
                        .setMessage(R.string.dialog_Message)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

         @Override
            public void onClick(DialogInterface dialog, int which) {
              // User Clicked OK Button
              Intent intent = new Intent();
              intent.putExtra("Response", "Here is my response");
              setResult(Activity.RESULT_OK,intent);
              finish();
            }
         })
             .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
         @Override
             public void onClick(DialogInterface dialog, int which) {
           // User Clicked Cancel Button
            }
          })
             .show();
           }
        }
    });
  }
  public void takePicture (View view){
              Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              if (intent.resolveActivity(getPackageManager()) != null) {
                  startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
              }

  }

    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume");

    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause");

    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop");

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }
}

