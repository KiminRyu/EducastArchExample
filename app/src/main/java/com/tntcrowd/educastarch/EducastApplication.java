package com.tntcrowd.educastarch;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

/**
 * Created by kimin on 17. 5. 23.
 */

public class EducastApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    AndroidNetworking.initialize(getApplicationContext());
  }
}
