
package com.example.priya.androidlabs;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class WeatherForecast extends Activity {

    protected static final String STRING_URL ="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    protected static final String ACTIVITY_NAME = "WeatherForecast";

    private ProgressBar progressBar;
    private ImageView WeatherImageView;
    private TextView CurrentTempView;
    private TextView MinTempView;
    private TextView MaxTempView;
    private TextView WindSpeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        WeatherImageView = (ImageView) findViewById(R.id.WeatherImage);
        CurrentTempView = (TextView) findViewById(R.id.CurrentTempView);
        MinTempView = (TextView) findViewById(R.id.MinTempView);
        MaxTempView = (TextView) findViewById(R.id.MaxTempView);
        WindSpeedView = (TextView) findViewById(R.id.WindSpeedView);

        new ForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {
        String CurrentTemperature;
        String MaxTemperature;
        String MinTemperature;
        String WindSpeed;
        Bitmap WeatherImage;

        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... args) {

            Log.i(ACTIVITY_NAME, "In doInBackground");

            try {

                URL url = new URL(STRING_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    if (parser.getName().equals("temperature")) {
                        String val = parser.getAttributeValue(null, "value");
                        if (val != null) {
                            CurrentTemperature = val;
                            publishProgress(25);
                            Log.i("temp", CurrentTemperature);
                        }
                        val = parser.getAttributeValue(null, "max");
                        if (val != null) {
                            MaxTemperature = val;
                            publishProgress(50);
                            Log.i("temp", MaxTemperature);
                        }
                        val = parser.getAttributeValue(null, "min");
                        if (val != null) {
                            MinTemperature = val;
                            publishProgress(75);
                            Log.i("temp", MinTemperature);
                        }
                        val = parser.getAttributeValue(null, "speed");
                        if (val != null) {
                            WindSpeed = val;
                            Log.i("temp", WindSpeed);
                        }

                    }
                    if (parser.getName().equals("speed")) {
                        WindSpeed = parser.getAttributeValue(null, "value");

                    }
                    if (parser.getName().equals("weather")) {
                        String iconName  = parser.getAttributeValue(null, "icon");
                        String imagefile = iconName + ".png";
                        Log.i("img", imagefile);

                        HttpURLConnection connection = null;
                        try {
                            connection = (HttpURLConnection) new URL("http://openweathermap.org/img/w/" + WeatherImage + ".png").openConnection();
                            connection.connect();
                            int responseCode = connection.getResponseCode();
                            if (responseCode == 200) {

                                if (fileExistance(WeatherImage + ".png")) {
                                    Log.e("File"," found");
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(WeatherImage + ".png");
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Bitmap bm = BitmapFactory.decodeStream(fis);
                                    WeatherImageView.setImageBitmap(bm);
                                } else {
                                    Log.e("File"," not found");

                                    Bitmap image = BitmapFactory.decodeStream(conn.getInputStream());
                                    FileOutputStream outputStream = openFileOutput(WeatherImage + ".png", Context.MODE_PRIVATE);
                                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                    WeatherImageView.setImageBitmap(image);

                                }
//                                Bitmap bm = BitmapFactory.decodeStream(connection.getInputStream());

                            } else
                                return null;
                        } catch (Exception e) {
                            return null;
                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }

                }

                conn.disconnect();
            }  catch(FileNotFoundException e){
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            catch (XmlPullParserException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());

            }
            catch (IOException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            return null;
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            if(file.exists()){
                Log.i("File Path:",file.getAbsolutePath());
            }
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer ... value){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            CurrentTempView.setText(CurrentTempView.getText()+ CurrentTemperature + "C");
            MaxTempView.setText(MaxTempView.getText() + MaxTemperature +  "C" );
            MinTempView.setText(MinTempView.getText() +  MinTemperature + "C");
            WindSpeedView.setText(WindSpeedView.getText() + WindSpeed );
            WeatherImageView.setImageBitmap(WeatherImage);


        }


    }

}
