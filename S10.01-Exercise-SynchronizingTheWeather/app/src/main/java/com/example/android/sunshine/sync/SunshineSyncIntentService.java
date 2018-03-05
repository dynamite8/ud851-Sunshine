package com.example.android.sunshine.sync;

// COMPLETED (5) Create a new class called SunshineSyncIntentService that extends IntentService
//  COMPLETED (6) Create a constructor that calls super and passes the name of this class
//  COMPLETED (7) Override onHandleIntent, and within it, call SunshineSyncTask.syncWeather

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class SunshineSyncIntentService extends IntentService {

    public SunshineSyncIntentService() {
        super(SunshineSyncIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SunshineSyncTask.syncWeather(this);
    }
}