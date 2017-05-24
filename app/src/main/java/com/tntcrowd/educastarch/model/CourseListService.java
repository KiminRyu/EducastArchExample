package com.tntcrowd.educastarch.model;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kimin on 17. 5. 23.
 */

public class CourseListService extends IntentService {

  private AppDatabase db;

  public CourseListService() {
    super("CourseListService");
  }

  @Override
  public void onCreate() {
    super.onCreate();
    db = AppDatabase.getInMemoryDatabase(getApplication());
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    if (intent != null) {
      db.courseModel().deleteAll();
      AndroidNetworking.get("https://educast.pro/api/latest/search/query")
          .addQueryParameter("fetch_num", "24")
          .addQueryParameter("target","course")
          .setPriority(Priority.MEDIUM)
          .build()
          .getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
              Log.d("educast", response.toString());
              try {
                // Also, we can use Gson to map json to entity
                JSONArray courseJsons = response.getJSONArray("results");
                for (int i = 0; i < courseJsons.length(); i++) {
                  Course course = getCourseFromJson(courseJsons.getJSONObject(i));
                  if (course != null) {
                    db.courseModel().insertCourse(course);
                  }
                }
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }

            @Override
            public void onError(ANError anError) {
              Log.d("educast", "error occured.");
            }
          });
    }

  }

  private Course getCourseFromJson(JSONObject object) {
    try {
      Course course = new Course(object.getInt("pk"));
      course.setName(object.getString("name"));
      course.setIntroduction(object.getString("introduction"));
      course.setImage(object.getJSONObject("image").getString("medium"));
      course.setPrice(object.getJSONObject("price").getInt("KRW"));
      course.setTakingDays(object.getInt("taking_days"));
      return course;
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }
}
