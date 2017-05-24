package com.tntcrowd.educastarch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kimin on 17. 5. 23.
 */

@Entity
public class Course {
  private @PrimaryKey int pk;
  private String name;
  private int takingDays;
  private int price;
  private String introduction;
  private String image;

  public Course(int pk) {
    this.pk = pk;
  }

  public int getPk() {
    return pk;
  }

  public void setPk(int pk) {
    this.pk = pk;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getTakingDays() {
    return takingDays;
  }

  public void setTakingDays(int takingDays) {
    this.takingDays = takingDays;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
