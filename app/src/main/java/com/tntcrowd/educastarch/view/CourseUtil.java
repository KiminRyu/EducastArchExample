package com.tntcrowd.educastarch.view;

/**
 * Created by kimin on 17. 5. 23.
 */

public class CourseUtil {
  public static String getTakingDaysString(int day) {
    if (day == 0) {
      return "Lifetime Access";
    } else {
      return String.format("%d Days", day);
    }
  }

  public static String getPriceString(int amount) {
    if (amount == 0) {
      return "FREE";
    } else {
      return "₩" + amount;
    }
  }
}
