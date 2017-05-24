package com.tntcrowd.educastarch.view;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tntcrowd.educastarch.R;
import com.tntcrowd.educastarch.model.Course;
import com.tntcrowd.educastarch.viewmodel.CourseListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kimin on 17. 5. 23.
 */

public class CourseListActivity extends AppCompatActivity implements LifecycleRegistryOwner {

  private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);
  private CourseListViewModel mCourseListViewModel;

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh_layout)
  SwipeRefreshLayout swipeRefreshLayout;

  @Override
  public LifecycleRegistry getLifecycle() {
    return mRegistry;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("educast", "onCreate");
    setContentView(R.layout.activity_course_list);
    mCourseListViewModel = ViewModelProviders.of(this).get(CourseListViewModel.class);

    ButterKnife.bind(this);
    setupUi();
    subscribeUiCourses();
  }

  private void setupUi() {
    setSupportActionBar(toolbar);
    setTitle("Educast Arch Demo");
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        mCourseListViewModel.load();
      }
    });
  }

  private void subscribeUiCourses() {
    mCourseListViewModel.getCoursesLiveData().observe(this, new Observer<List<Course>>() {
      @Override
      public void onChanged(@Nullable List<Course> courses) {
        if (courses != null && courses.size() != 0) {
          recyclerView.setAdapter(new CourseAdapter(courses));
          mCourseListViewModel.setFetching(false);
        }
      }
    });
    mCourseListViewModel.getFetching().observe(this, new Observer<Boolean>() {
      @Override
      public void onChanged(@Nullable Boolean isFetching) {
        Log.d("educast", isFetching.toString());
        swipeRefreshLayout.setRefreshing(isFetching);
        if (isFetching) {
          recyclerView.setVisibility(View.GONE);
        } else {
          recyclerView.setVisibility(View.VISIBLE);
        }
      }
    });
  }

  class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    List<Course> mCourses;

    public CourseAdapter(List<Course> courses) {
      mCourses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new CourseViewHolder(getActivityContext(), parent);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
      final Course course = mCourses.get(position);
      holder.name.setText(course.getName());
      holder.takingDays.setText(CourseUtil.getTakingDaysString(course.getTakingDays()));
      holder.price.setText(CourseUtil.getPriceString(course.getPrice()));

      Glide.with(getActivityContext())
          .load(course.getImage())
          .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
      return mCourses.size();
    }
  }

  static class CourseViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.taking_days)
    TextView takingDays;
    @BindView(R.id.price)
    TextView price;

    public CourseViewHolder(Context context, ViewGroup parent) {
      super(LayoutInflater.from(context).inflate(R.layout.list_course_row, parent, false));
      ButterKnife.bind(this, itemView);
    }
  }

  private Context getActivityContext() {
    return CourseListActivity.this;
  }
}
