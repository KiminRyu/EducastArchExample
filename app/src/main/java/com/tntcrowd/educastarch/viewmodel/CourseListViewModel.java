package com.tntcrowd.educastarch.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;

import com.tntcrowd.educastarch.model.AppDatabase;
import com.tntcrowd.educastarch.model.Course;
import com.tntcrowd.educastarch.model.CourseListService;

import java.util.List;

/**
 * Created by kimin on 17. 5. 23.
 */

public class CourseListViewModel extends AndroidViewModel {
  private LiveData<List<Course>> courses;
  private MutableLiveData<Boolean> fetching = new MutableLiveData<>();

  private AppDatabase mDb;

  public CourseListViewModel(Application application) {
    super(application);
    createDb();
    load();
    subscribeToDbChanges();
  }

  public void createDb() {
    mDb = AppDatabase.getInMemoryDatabase(getApplication());
  }

  public void subscribeToDbChanges() {
    courses = mDb.courseModel().findAllCourses();
  }

  public void load() {
    fetching.setValue(true);
    // TODO: initialize data or fetch data from remote server
    Intent intent = new Intent(this.getApplication(), CourseListService.class);
    getApplication().startService(intent);
  }

  public LiveData<List<Course>> getCoursesLiveData() {
    return courses;
  }

  public MutableLiveData<Boolean> getFetching() {
    return fetching;
  }

  public void setFetching(boolean isFetching) {
    fetching.setValue(isFetching);
  }
}
