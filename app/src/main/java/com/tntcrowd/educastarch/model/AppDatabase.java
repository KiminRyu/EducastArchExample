package com.tntcrowd.educastarch.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by kimin on 17. 5. 23.
 */

@Database(entities = {Course.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  private static AppDatabase INSTANCE;

  public abstract CourseDao courseModel();

  public static AppDatabase getInMemoryDatabase(Context context) {
    if (INSTANCE == null) {
      INSTANCE =
          Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
              .allowMainThreadQueries()
              .build();
    }
    return INSTANCE;
  }

  public static void destroyInstance() {
    INSTANCE = null;
  }
}
