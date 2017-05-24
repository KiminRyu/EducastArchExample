package com.tntcrowd.educastarch.model;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

/**
 * Created by kimin on 17. 5. 23.
 */

@Dao
public interface CourseDao {

  @Query("SELECT * FROM Course")
  public LiveData<List<Course>> findAllCourses();

  @Query("SELECT * FROM Course")
  public List<Course> findAllCoursesSync();

  @Insert(onConflict = IGNORE)
  void insertCourse(Course course);

  @Update(onConflict = REPLACE)
  void updateCourse(Course course);

  @Query("DELETE FROM Course")
  void deleteAll();
}

