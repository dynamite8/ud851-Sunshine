package com.example.android.sunshine.sync;

//  COMPLETED (1) Create a class called SunshineSyncTask
//  COMPLETED (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
//      COMPLETED (3) Within syncWeather, fetch new weather data
//      COMPLETED (4) If we have valid results, delete the old data and insert the new

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class SunshineSyncTask {

    synchronized public static void syncWeather(@NonNull final Context context) {
        try {
            URL weatherRequestURL = NetworkUtils.getUrl(context);

            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestURL);

            // Parse the values
            ContentValues[] weatherValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonWeatherResponse);

            if (weatherValues != null && weatherValues.length > 0) {
                // get handle on the content resolver to delete and insert data
                ContentResolver resolver = context.getContentResolver();
                resolver.delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);
                resolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, weatherValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}